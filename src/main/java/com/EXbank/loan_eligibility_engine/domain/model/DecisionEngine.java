package com.EXbank.loan_eligibility_engine.domain.model;

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
    public EvaluationResult evaluateAll (EvaluationContext context){
        // 1. Iterate over each rule strategy and invoke evaluate(context)

        List<RuleResult> auditLog = eligibilityRuleList.stream()
                .map(rule -> rule.evaluate(context))
                .toList();
        // 2. Aggregate verdict: If any rule failed, verdict is DECLINED
        boolean hasFailures = auditLog.stream().anyMatch(result -> !result.passed());
        DecisionVerdict verdict = hasFailures ? DecisionVerdict.DECLINED : DecisionVerdict.APPROVED;

        // 3. Construct and return the aggregated domain evaluation result
        return new EvaluationResult(
                verdict,
                context.getApplicationId(),
                auditLog
        );
    }
}
