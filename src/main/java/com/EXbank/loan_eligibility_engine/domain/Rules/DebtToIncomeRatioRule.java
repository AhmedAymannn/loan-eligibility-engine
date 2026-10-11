package com.EXbank.loan_eligibility_engine.domain.rules;
import com.EXbank.loan_eligibility_engine.domain.model.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.rules.Helper.InstallmentCalculator;
import com.EXbank.loan_eligibility_engine.domain.model.RuleResult;
import com.EXbank.loan_eligibility_engine.domain.model.RuleValues;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class DebtToIncomeRatioRule implements EligibilityRule {

    private static final String NAME = "DebtToIncomeRatioRule";

    private final InstallmentCalculator installmentCalculator;

    public DebtToIncomeRatioRule(InstallmentCalculator installmentCalculator) {
        this.installmentCalculator = installmentCalculator;
    }

    @Override
    public RuleResult evaluate(EvaluationContext context, RuleValues values) {

        BigDecimal salary = context.monthlyNetSalary();

        // 1. Guard against null/invalid salary
        if (salary == null || salary.compareTo(BigDecimal.ZERO) <= 0) {
            return new RuleResult(NAME, false, "DTI_INVALID_SALARY",
                    "Monthly net salary must be greater than zero.");
        }

        // 2. Guard against invalid tenure
        if (context.requestedTenureMonths() <= 0) {
            return new RuleResult(NAME, false, "DTI_INVALID_TENURE",
                    "Requested loan tenure must be greater than zero.");
        }

        // 3. NEW: guard against invalid amount (the calculator now throws instead of returning 0)
        BigDecimal amount = context.requestedAmount();
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return new RuleResult(NAME, false, "DTI_INVALID_AMOUNT",
                    "Requested loan amount must be greater than zero.");
        }

        // 4. Safe null-handling for existing debt
        BigDecimal debt = context.existingMonthlyDebt() != null
                ? context.existingMonthlyDebt()
                : BigDecimal.ZERO;

        // 5. CHANGED: the interest rate now comes from the policy snapshot
        final BigDecimal newInstallment = installmentCalculator.calculateMonthlyInstallment(
                amount,
                context.requestedTenureMonths(),
                values.annualInterestRate()
        );

        // 6. Sum total obligations (existing debt + new installment) FIRST
        final BigDecimal totalMonthlyDebt = debt.add(newInstallment);

        // 7. CHANGED: one calculation, rounded UP, so a borderline value never rounds in the applicant's favor
        BigDecimal dtiPercentage = totalMonthlyDebt
                .multiply(BigDecimal.valueOf(100))
                .divide(salary, 2, RoundingMode.UP);

        // 8. Evaluate against the threshold from the policy
        if (dtiPercentage.compareTo(values.maxDtiPercent()) > 0) {
            return new RuleResult(NAME, false, "DTI_EXCEEDED",
                    String.format("Calculated DTI of %s%% (incl. new installment %s) exceeds maximum threshold of %s%%",
                            dtiPercentage, newInstallment, values.maxDtiPercent()));
        }

        return new RuleResult(NAME, true, "DTI_OK",
                String.format("Calculated DTI of %s%% is within allowed limit of %s%%",
                        dtiPercentage, values.maxDtiPercent()));
    }
}