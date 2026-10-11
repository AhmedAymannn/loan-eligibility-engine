package com.EXbank.loan_eligibility_engine.domain.rules;

import com.EXbank.loan_eligibility_engine.domain.model.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.RuleResult;
import com.EXbank.loan_eligibility_engine.domain.model.RuleValues;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AmountRule implements EligibilityRule {

    private static final String NAME = "AmountRule";

    @Override
    public RuleResult evaluate(EvaluationContext context, RuleValues values) {

        BigDecimal amount = context.requestedAmount();

        if (amount == null || amount.signum() <= 0) {
            return new RuleResult(NAME, false, "ERR_INVALID_AMOUNT",
                    "Requested loan amount must be greater than zero.");
        }
        if (amount.compareTo(values.minAmount()) < 0) {
            return new RuleResult(NAME, false, "ERR_AMOUNT_BELOW_MINIMUM",
                    "Requested amount (" + amount + ") is below the minimum of " + values.minAmount() + ".");
        }
        if (amount.compareTo(values.maxAmount()) > 0) {
            return new RuleResult(NAME, false, "ERR_AMOUNT_ABOVE_MAXIMUM",
                    "Requested amount (" + amount + ") exceeds the maximum of " + values.maxAmount() + ".");
        }

        return new RuleResult(NAME, true, "AMOUNT_OK",
                "Requested amount is within the allowed range.");
    }
}