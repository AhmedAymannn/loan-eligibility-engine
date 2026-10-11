package com.EXbank.loan_eligibility_engine.domain.rules;

import com.EXbank.loan_eligibility_engine.domain.model.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.RuleResult;
import com.EXbank.loan_eligibility_engine.domain.model.RuleValues;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AgeRule implements EligibilityRule {
    private final int minAge;
    private final int maxMaturityAge;
    public AgeRule(@Value("${eligibility.rules.min-age}") int minAge,
                   @Value("${eligibility.rules.max-maturity-age}")  int maxMaturityAge) {
        this.minAge = minAge;
        this.maxMaturityAge = maxMaturityAge;
    }
    @Override
    public RuleResult evaluate(EvaluationContext context , RuleValues values) {

        if (context.applicantAge() <= 0) {
            return new RuleResult(
                    "AgeRule",
                    false,
                    "ERR_INVALID_AGE",
                    "Applicant age must be greater than zero."
            );
        }
        // 2. Maximum maturity age check
        int tenureYears = (context.requestedTenureMonths() + 11) / 12;
        int ageAtMaturity = context.applicantAge() + tenureYears;

        if (ageAtMaturity > maxMaturityAge) {
            return new RuleResult(
                    "AgeRule",
                    false,
                    "ERR_EXCEEDS_MATURITY_AGE",
                    String.format("Applicant age at loan maturity (%d) exceeds the maximum allowed age of %d.",
                            ageAtMaturity, maxMaturityAge)
            );
        }
        if (context.applicantAge() < minAge) {
            return new RuleResult(
                    "AgeRule",
                    false,
                    "ERR_UNDERAGE",
                    "Applicant age (" + context.applicantAge() + ") is below minimum required age of " + minAge
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


