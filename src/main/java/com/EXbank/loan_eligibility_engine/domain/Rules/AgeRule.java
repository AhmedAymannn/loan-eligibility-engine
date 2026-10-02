package com.EXbank.loan_eligibility_engine.domain.rules;

import com.EXbank.loan_eligibility_engine.domain.model.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.RuleResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AgeRule implements EligibilityRule {
    private final int minAge;

    public AgeRule(@Value("${eligibility.rules.min-age}") int minAge, EvaluationContext context, RuleResult ruleResult) {
        this.minAge = minAge;
    }
    @Override
    public RuleResult evaluate(EvaluationContext context) {
        if (context.getApplicantAge() < minAge) {
            return new RuleResult(
                    "AgeRule",
                    false,
                    "ERR_UNDERAGE",
                    "Applicant age (" + context.getApplicantAge() + ") is below minimum required age of " + minAge
            );
        }

        return new RuleResult(
                "AgeRule",
                true,
                "AGE_OK",
                "Applicant meets the minimum age requirement."
        );
    }
}


