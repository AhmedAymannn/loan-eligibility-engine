package com.EXbank.loan_eligibility_engine.application.service;


import com.EXbank.loan_eligibility_engine.application.entity.PolicyConfiguration;
import com.EXbank.loan_eligibility_engine.application.repository.PolicyConfigurationRepository;
import com.EXbank.loan_eligibility_engine.domain.model.RuleValues;
import org.springframework.stereotype.Service;

@Service
public class PolicyServiceImp implements PolicyServiceProvider {

    private final PolicyConfigurationRepository policyConfigurationRepository;

    public PolicyServiceImp(PolicyConfigurationRepository policyConfigurationRepository) {
        this.policyConfigurationRepository = policyConfigurationRepository ;
    }

    @Override
    public RuleValues getRuleValues(String productCode) {
        String productCodeUpperCased = productCode.toUpperCase();

        PolicyConfiguration policy = policyConfigurationRepository.
                findByProductCodeWhereActiveIsTrue(productCodeUpperCased).orElseThrow();

        return new RuleValues(
                policy.getMinAge(),
                policy.getMaxMaturityAge(),
                policy.getMinCreditScore(),
                policy.getMinMonthlyIncome(),
                policy.getMinResidualIncome(),
                policy.getMaxDtiPercent(),
                policy.getMaxIncomeMultiple(),
                policy.getMaxActiveLoans(),
                policy.getMinTenureMonths(),
                policy.getMaxTenureMonths(),
                policy.getMinAmount(),
                policy.getMaxAmount(),
                policy.getAnnualInterestRate(),
                policy.getTierPremiumScore(),
                policy.getTierStandardMaxAmount()
        );
    }
}
