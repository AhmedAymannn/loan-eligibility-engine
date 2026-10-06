package com.EXbank.loan_eligibility_engine.domain.rules;
import com.EXbank.loan_eligibility_engine.domain.model.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.Helper.InstallmentCalculator;
import com.EXbank.loan_eligibility_engine.domain.model.RuleResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
@Component
public class DebtToIncomeRatioRule implements EligibilityRule {

    private final BigDecimal maxDtiRatio;
    private final InstallmentCalculator installmentCalculator;

    public DebtToIncomeRatioRule(
            @Value("${loan.eligibility.rules.max-ratio}") BigDecimal maxDtiRatio,
            InstallmentCalculator installmentCalculator) {
        this.maxDtiRatio = maxDtiRatio;
        this.installmentCalculator = installmentCalculator;
    }

    @Override
    public RuleResult evaluate(EvaluationContext context) {

        BigDecimal salary = context.monthlyNetSalary();

        // 1. Guard against null/invalid salary
        if (salary == null || salary.compareTo(BigDecimal.ZERO) <= 0) {
            return new RuleResult(
                    "DebtToIncomeRatioRule",
                    false,
                    "DTI_INVALID_SALARY",
                    "Monthly net salary must be greater than zero."
            );
        }

        // 2. Guard against invalid tenure
        if (context.requestedTenureMonths() <= 0) {
            return new RuleResult(
                    "DebtToIncomeRatioRule",
                    false,
                    "DTI_INVALID_TENURE",
                    "Requested loan tenure must be greater than zero."
            );
        }

        // 3. Safe null-handling for existing debt
        BigDecimal debt = context.existingMonthlyDebt() != null
                ? context.existingMonthlyDebt()
                : BigDecimal.ZERO;

        // 4. Calculate proposed new loan installment
        final BigDecimal newInstallment = installmentCalculator.calculateMonthlyInstallment(
                context.requestedAmount(),
                context.requestedTenureMonths()
        );

        // 5. Sum total obligations (Existing Debt + New Loan Installment) FIRST
        final BigDecimal totalMonthlyDebt = debt.add(newInstallment);

        // 6. Calculate total DTI percentage
        BigDecimal dtiPercentage = totalMonthlyDebt.divide(salary, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);

        // 7. Evaluate against threshold
        if (dtiPercentage.compareTo(maxDtiRatio) > 0) {
            return new RuleResult(
                    "DebtToIncomeRatioRule",
                    false,
                    "DTI_EXCEEDED",
                    String.format("Calculated DTI of %s%% (incl. new installment %s) exceeds maximum threshold of %s%%",
                            dtiPercentage, newInstallment, maxDtiRatio)
            );
        }

        return new RuleResult(
                "DebtToIncomeRatioRule",
                true,
                "DTI_OK",
                String.format("Calculated DTI of %s%% is within allowed limit of %s%%", dtiPercentage, maxDtiRatio)
        );
    }
}