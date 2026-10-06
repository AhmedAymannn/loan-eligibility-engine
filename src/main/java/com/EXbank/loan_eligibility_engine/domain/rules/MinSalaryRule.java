package com.EXbank.loan_eligibility_engine.domain.rules;

import com.EXbank.loan_eligibility_engine.domain.model.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.RuleResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MinSalaryRule implements EligibilityRule {

    private final BigDecimal minSalary;

    public MinSalaryRule(@Value("${eligibility.rules.min-salary}") BigDecimal minSalary) {
        this.minSalary = minSalary;
    }

    @Override
    public RuleResult evaluate(EvaluationContext context) {
        if (context.monthlyNetSalary().compareTo(minSalary) < 0) {
            return new RuleResult(
                    "MinSalaryRule",
                    false,
                    "ERR_SALARY_BELOW_MINIMUM",
                    "Monthly net salary (" + context.monthlyNetSalary() + ") is below minimum required salary of " + minSalary);
        }
        return new RuleResult(
                "MinSalaryRule",
                true,
                "SALARY_OK",
                "Applicant meets the minimum salary requirement.");
    }
}
