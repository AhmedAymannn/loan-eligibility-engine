package com.EXbank.loan_eligibility_engine.domain.rules;

import com.EXbank.loan_eligibility_engine.domain.model.EvaluationContext;
import com.EXbank.loan_eligibility_engine.domain.model.RuleResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class DebtToIncomeRatioRule implements EligibilityRule {

    private final BigDecimal maxDtiRatio;

    public DebtToIncomeRatioRule(
            @Value("${loan.eligibility.rules.dti.max-ratio:45.0}") BigDecimal maxDtiRatio) {
        this.maxDtiRatio = maxDtiRatio;
    }

    @Override
    public RuleResult evaluate(EvaluationContext context) {
        BigDecimal salary = context.getMonthlyNetSalary();
        BigDecimal debt = context.getExistingMonthlyDebt();

        if (salary == null || salary.compareTo(BigDecimal.ZERO) <= 0) {
            return new RuleResult(
                    "DebtToIncomeRatioRule",
                    false,
                    "DTI_INVALID_SALARY",
                    "Monthly net salary must be greater than zero"
            );
        }

        BigDecimal dtiPercentage = debt.divide(salary, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);

        if (dtiPercentage.compareTo(maxDtiRatio) > 0) {
            return new RuleResult(
                    "DebtToIncomeRatioRule",
                    false,
                    "DTI_EXCEEDED",
                    String.format("Calculated DTI of %s%% exceeds maximum threshold of %s%%", dtiPercentage, maxDtiRatio)
            );
        }

        return new RuleResult(
                "DebtToIncomeRatioRule",
                true,
                "DTI_OK",
                String.format("Calculated DTI of %s%% is within allowed limit of %s%%", dtiPercentage, maxDtiRatio)
        );
    }
}