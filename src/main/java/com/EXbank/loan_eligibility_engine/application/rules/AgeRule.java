package com.EXbank.loan_eligibility_engine.application.rules;

import com.EXbank.loan_eligibility_engine.domain.model.evaluationContext.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleResult;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleValues;
import org.springframework.stereotype.Component;


@Component

public class AgeRule implements EligibilityRule {

    private static final String NAME = "AgeRule";

    @Override
    public RuleResult evaluate(EvaluationContext context, RuleValues values) {

        int age = context.applicantAge();

        if (age <= 0) {
            return new RuleResult(NAME, false, "ERR_INVALID_AGE",
                    "Applicant age must be greater than zero.");
        }

        // Maximum maturity age check (tenure rounded up to whole years; long math avoids int overflow)
        long tenureYears = Math.max(0L, ((long) context.requestedTenureMonths() + 11) / 12);
        long ageAtMaturity = age + tenureYears;

        if (ageAtMaturity > values.maxMaturityAge()) {
            return new RuleResult(NAME, false, "ERR_EXCEEDS_MATURITY_AGE",
                    String.format("Applicant age at loan maturity (%d) exceeds the maximum allowed age of %d.",
                            ageAtMaturity, values.maxMaturityAge()));
        }

        if (age < values.minAge()) {
            return new RuleResult(NAME, false, "ERR_UNDERAGE",
                    "Applicant age (" + age + ") is below minimum required age of " + values.minAge());
        }

        return new RuleResult(NAME, true, "AGE_OK",
                "Applicant meets the age requirements.");
    }
}


