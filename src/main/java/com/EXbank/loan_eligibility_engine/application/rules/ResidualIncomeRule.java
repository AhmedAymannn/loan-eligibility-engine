package com.EXbank.loan_eligibility_engine.application.rules;

import com.EXbank.loan_eligibility_engine.domain.model.evaluationContext.EvaluationContext;
import com.EXbank.loan_eligibility_engine.application.rules.Helper.InstallmentCalculator;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleResult;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleValues;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ResidualIncomeRule implements EligibilityRule {

    private static final String NAME = "ResidualIncomeRule";

    private final InstallmentCalculator installmentCalculator;

    public ResidualIncomeRule(InstallmentCalculator installmentCalculator) {
        this.installmentCalculator = installmentCalculator;
    }

    @Override
    public RuleResult evaluate(EvaluationContext context, RuleValues values) {

        BigDecimal salary = context.monthlyNetSalary();
        if (salary == null || salary.signum() <= 0) {
            return new RuleResult(NAME, false, "RESIDUAL_INVALID_SALARY",
                    "Monthly net salary must be greater than zero.");
        }

        // tenure must be positive and not beyond the policy maximum (TenureRule reports the policy limit)
        int tenure = context.requestedTenureMonths();
        if (tenure <= 0 || tenure > values.maxTenureMonths()) {
            return new RuleResult(NAME, false, "RESIDUAL_INVALID_TENURE",
                    "Requested loan tenure must be between 1 and " + values.maxTenureMonths()
                            + " months to calculate the residual income.");
        }

        BigDecimal amount = context.requestedAmount();
        if (amount == null || amount.signum() <= 0) {
            return new RuleResult(NAME, false, "RESIDUAL_INVALID_AMOUNT",
                    "Requested loan amount must be greater than zero.");
        }

        BigDecimal debt = context.existingMonthlyDebt() != null
                ? context.existingMonthlyDebt()
                : BigDecimal.ZERO;
        if (debt.signum() < 0) {
            return new RuleResult(NAME, false, "RESIDUAL_INVALID_DEBT",
                    "Existing monthly debt cannot be negative.");
        }

        BigDecimal newInstallment = installmentCalculator.calculateMonthlyInstallment(
                amount, tenure, values.annualInterestRate());

        BigDecimal residual = salary.subtract(debt).subtract(newInstallment);

        if (residual.compareTo(values.minResidualIncome()) < 0) {
            return new RuleResult(NAME, false, "ERR_RESIDUAL_INCOME_TOO_LOW",
                    String.format("Residual income after all obligations (%s) is below the minimum of %s.",
                            residual, values.minResidualIncome()));
        }

        return new RuleResult(NAME, true, "RESIDUAL_INCOME_OK",
                String.format("Residual income of %s meets the minimum of %s.",
                        residual, values.minResidualIncome()));
    }
}