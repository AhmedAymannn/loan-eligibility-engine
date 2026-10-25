package com.EXbank.loan_eligibility_engine.application.engine;

import com.EXbank.loan_eligibility_engine.domain.model.evaluationContext.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.evaluationContext.EvaluationResult;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleResult;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleValues;
import com.EXbank.loan_eligibility_engine.application.rules.EligibilityRule;
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
