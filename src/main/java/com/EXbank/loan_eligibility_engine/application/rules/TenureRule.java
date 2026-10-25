package com.EXbank.loan_eligibility_engine.application.rules;

import com.EXbank.loan_eligibility_engine.domain.model.evaluationContext.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleResult;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleValues;
import org.springframework.stereotype.Component;


@Component
public class TenureRule implements EligibilityRule {

    private static final String NAME = "TenureRule";

    @Override
    public RuleResult evaluate(EvaluationContext context, RuleValues values) {

        int tenure = context.requestedTenureMonths();

        if (tenure < values.minTenureMonths()) {
            return new RuleResult(NAME, false, "ERR_TENURE_BELOW_MINIMUM",
                    "Requested tenure (" + tenure + " months) is below the minimum of "
                            + values.minTenureMonths() + " months.");
        }
        if (tenure > values.maxTenureMonths()) {
            return new RuleResult(NAME, false, "ERR_TENURE_ABOVE_MAXIMUM",
                    "Requested tenure (" + tenure + " months) exceeds the maximum of "
                            + values.maxTenureMonths() + " months.");
        }

        return new RuleResult(NAME, true, "TENURE_OK",
                "Requested tenure is within the allowed range.");
    }
}
