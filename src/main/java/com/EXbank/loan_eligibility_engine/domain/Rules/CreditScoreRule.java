package com.EXbank.loan_eligibility_engine.domain.Rules;

import com.EXbank.loan_eligibility_engine.domain.model.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.RuleResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class CreditScoreRule implements EligibilityRule {

    private final int minCreditScore ;

    public CreditScoreRule(@Value("$eligibility.rules.min-credit-score") int minCreditScore) {
        this.minCreditScore = minCreditScore;
    }

    @Override
    public RuleResult evaluate(EvaluationContext context) {
        if(context.getCreditScore() < minCreditScore ){
            return RuleResult.fail(
                    "CreditScoreRule",
                    "ERR_UNDERAGE",
                    "Applicant CreditScoreRule (" + context.getApplicantAge() + ") is below minimum required age of " + minCreditScore
            );
        }

        return RuleResult.pass(
                "CreditScoreRule",
                "Applicant meets the minimum CreditScoreRule requirement."
        );
    }
}

