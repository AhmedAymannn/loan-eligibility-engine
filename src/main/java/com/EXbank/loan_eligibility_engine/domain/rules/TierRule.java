package com.EXbank.loan_eligibility_engine.domain.rules;

import com.EXbank.loan_eligibility_engine.domain.model.evaluationContext.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleResult;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleValues;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TierRule implements EligibilityRule {

    private static final String NAME = "TierRule";

    @Override
    public RuleResult evaluate(EvaluationContext context, RuleValues values) {

        BigDecimal amount = context.requestedAmount();

        if (amount == null || amount.signum() <= 0) {
            return new RuleResult(NAME, false, "TIER_INVALID_AMOUNT",
                    "Requested loan amount must be greater than zero.");
        }

        boolean premium = context.creditScore() >= values.tierPremiumScore();

        if (!premium && amount.compareTo(values.tierStandardMaxAmount()) > 0) {
            return new RuleResult(NAME, false, "ERR_STANDARD_TIER_AMOUNT_CAP",
                    String.format("Standard tier (credit score below %d) is limited to %s, but %s was requested.",
                            values.tierPremiumScore(), values.tierStandardMaxAmount(), amount));
        }

        return new RuleResult(NAME, true, "TIER_OK",
                premium ? "Applicant qualifies for the premium tier."
                        : "Requested amount is within the standard tier limit.");
    }
}