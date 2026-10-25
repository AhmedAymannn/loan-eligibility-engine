package com.EXbank.loan_eligibility_engine.application.rules;

import com.EXbank.loan_eligibility_engine.domain.model.evaluationContext.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleResult;
import com.EXbank.loan_eligibility_engine.domain.model.ruleContext.RuleValues;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component

public class MinSalaryRule implements EligibilityRule {

    private static final String NAME = "MinSalaryRule";

    @Override
    public RuleResult evaluate(EvaluationContext context, RuleValues values) {

        BigDecimal salary = context.monthlyNetSalary();

        if (salary == null) {
            return new RuleResult(NAME, false, "ERR_INVALID_SALARY",
                    "Monthly net salary is required.");
        }

        if (salary.compareTo(values.minMonthlyIncome()) < 0) {
            return new RuleResult(NAME, false, "ERR_SALARY_BELOW_MINIMUM",
                    "Monthly net salary (" + salary + ") is below minimum required salary of "
                            + values.minMonthlyIncome());
        }

        return new RuleResult(NAME, true, "SALARY_OK",
                "Applicant meets the minimum salary requirement.");
    }
}
