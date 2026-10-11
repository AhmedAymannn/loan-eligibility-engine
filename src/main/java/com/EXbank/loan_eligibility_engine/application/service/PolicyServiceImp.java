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
                policy.getMinCreditScore(),
                policy.getMinAge(),
                policy.getMinMonthlyIncome()

        );
    }
}
