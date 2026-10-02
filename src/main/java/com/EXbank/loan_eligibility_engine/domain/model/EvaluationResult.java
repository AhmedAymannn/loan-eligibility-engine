package com.EXbank.loan_eligibility_engine.domain.model;

import com.EXbank.loan_eligibility_engine.domain.enums.DecisionVerdict;

import java.util.List;

public class EvaluationResult {
    private DecisionVerdict verdict ;
    private String applicationId;
    private List <RuleResult> auditLog ;

    public EvaluationResult(DecisionVerdict verdict, String applicationId, List<RuleResult> auditLog) {
        this.verdict = verdict;
        this.applicationId = applicationId;
        this.auditLog = auditLog;
    }

    public DecisionVerdict getVerdict() {
        return verdict;
    }

    public void setVerdict(DecisionVerdict verdict) {
        this.verdict = verdict;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public List<RuleResult> getAuditLog() {
        return auditLog;
    }

    public void setAuditLog(List<RuleResult> auditLog) {
        this.auditLog = auditLog;
    }
}
