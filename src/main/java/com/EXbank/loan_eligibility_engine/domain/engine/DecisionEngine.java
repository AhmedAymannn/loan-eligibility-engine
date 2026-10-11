package com.EXbank.loan_eligibility_engine.domain.engine;

import com.EXbank.loan_eligibility_engine.domain.model.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.EvaluationResult;
import com.EXbank.loan_eligibility_engine.domain.model.RuleResult;
import com.EXbank.loan_eligibility_engine.domain.model.RuleValues;
import com.EXbank.loan_eligibility_engine.domain.rules.EligibilityRule;
import com.EXbank.loan_eligibility_engine.domain.enums.DecisionVerdict;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DecisionEngine {

    private final List<EligibilityRule> eligibilityRuleList;

    public DecisionEngine(List<EligibilityRule> eligibilityRuleList) {
        this.eligibilityRuleList = eligibilityRuleList;
    }

    public EvaluationResult evaluateAll (EvaluationContext context , RuleValues values){

        List<RuleResult> auditLog =
                eligibilityRuleList
                        .stream()
                        .map(rule -> rule.evaluate(context , values))
                        .toList();
        boolean hasFailures = auditLog.stream().anyMatch(result -> !result.passed());
        DecisionVerdict verdict = hasFailures ? DecisionVerdict.DECLINED : DecisionVerdict.APPROVED;
       return new EvaluationResult(
                verdict,
                context.applicationId(),
                auditLog
        );
    }
}
