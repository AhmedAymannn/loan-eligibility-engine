package com.EXbank.loan_eligibility_engine.application.rules;

import com.EXbank.loan_eligibility_engine.domain.model.evaluationContext.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleResult;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleValues;
import com.EXbank.loan_eligibility_engine.application.rules.Helper.InstallmentCalculator;
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

        // 1. Guard: salary
        BigDecimal salary = context.monthlyNetSalary();
        if (salary == null || salary.signum() <= 0) {
            return new RuleResult(NAME, false, "DTI_INVALID_SALARY",
                    "Monthly net salary must be greater than zero.");
        }

        // 2. Guard: tenure must be positive and not beyond the policy maximum
        //    (the policy limit itself is reported by TenureRule)
        int tenure = context.requestedTenureMonths();
        if (tenure <= 0 || tenure > values.maxTenureMonths()) {
            return new RuleResult(NAME, false, "DTI_INVALID_TENURE",
                    "Requested loan tenure must be between 1 and " + values.maxTenureMonths()
                            + " months to calculate the DTI.");
        }

        // 3. Guard: amount
        BigDecimal amount = context.requestedAmount();
        if (amount == null || amount.signum() <= 0) {
            return new RuleResult(NAME, false, "DTI_INVALID_AMOUNT",
                    "Requested loan amount must be greater than zero.");
        }

        // 4. Existing debt: missing means none, negative is invalid
        BigDecimal debt = context.existingMonthlyDebt() != null
                ? context.existingMonthlyDebt()
                : BigDecimal.ZERO;
        if (debt.signum() < 0) {
            return new RuleResult(NAME, false, "DTI_INVALID_DEBT",
                    "Existing monthly debt cannot be negative.");
        }

        // 5. New installment, priced with the policy's interest rate
        final BigDecimal newInstallment = installmentCalculator.calculateMonthlyInstallment(
                amount, tenure, values.annualInterestRate());

        // 6. Total obligations (existing debt + new installment)
        final BigDecimal totalMonthlyDebt = debt.add(newInstallment);

        // 7. DTI percentage, rounded UP so a borderline value never rounds in the applicant's favor
        BigDecimal dtiPercentage = totalMonthlyDebt
                .multiply(BigDecimal.valueOf(100))
                .divide(salary, 2, RoundingMode.UP);

        // 8. Compare with the policy threshold
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