package com.EXbank.loan_eligibility_engine.domain.Rules;

import com.EXbank.loan_eligibility_engine.domain.model.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.RuleResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class DebtToIncomeRatioRule implements EligibilityRule{

    private final BigDecimal maxDti;

    public DebtToIncomeRatioRule(@Value("$eligibility.rule.max-dti") BigDecimal maxDti) {
        this.maxDti = maxDti;
    }

    @Override
    public RuleResult evaluate(EvaluationContext context) {
        if (context.getCalculateDti().compareTo(maxDti) > 0) {
            return RuleResult.fail(
                    "DebtToIncomeRatioRule",
                    "ERR_DTI_EXCEEDED",
                    "Debt-to-Income ratio (" + context.getCalculateDti() + "%) exceeds the maximum limit of " + maxDti + "%"
            );
        }

        // 3. If within limits, return PASS
        return RuleResult.pass(
                "DebtToIncomeRatioRule",
                "Debt-to-Income ratio (" + context.getCalculateDti() + "%) is within acceptable limits."
        );
    }



}
