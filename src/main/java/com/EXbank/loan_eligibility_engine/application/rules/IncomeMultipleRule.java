package com.EXbank.loan_eligibility_engine.application.rules;

import com.EXbank.loan_eligibility_engine.domain.model.evaluationContext.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleResult;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleValues;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component

public class IncomeMultipleRule implements EligibilityRule {

    private static final String NAME = "IncomeMultipleRule";

    @Override
    public RuleResult evaluate(EvaluationContext context, RuleValues values) {

        BigDecimal salary = context.monthlyNetSalary();
        BigDecimal amount = context.requestedAmount();

        if (salary == null || salary.signum() <= 0) {
            return new RuleResult(NAME, false, "MULTIPLE_INVALID_SALARY",
                    "Monthly net salary must be greater than zero.");
        }
        if (amount == null || amount.signum() <= 0) {
            return new RuleResult(NAME, false, "MULTIPLE_INVALID_AMOUNT",
                    "Requested loan amount must be greater than zero.");
        }

        BigDecimal maxAllowed = salary.multiply(values.maxIncomeMultiple());

        if (amount.compareTo(maxAllowed) > 0) {
            return new RuleResult(NAME, false, "ERR_AMOUNT_EXCEEDS_INCOME_MULTIPLE",
                    String.format("Requested amount (%s) exceeds %s times monthly salary (%s).",
                            amount, values.maxIncomeMultiple(), maxAllowed));
        }

        return new RuleResult(NAME, true, "INCOME_MULTIPLE_OK",
                "Requested amount is within the allowed income multiple.");
    }
}