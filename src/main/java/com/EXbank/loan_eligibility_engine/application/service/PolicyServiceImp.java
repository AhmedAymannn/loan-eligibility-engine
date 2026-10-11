package com.EXbank.loan_eligibility_engine.application.service;


import com.EXbank.loan_eligibility_engine.application.entity.PolicyConfiguration;
import org.springframework.stereotype.Service;

@Service
public class PolicyServiceImp implements PolicyService{


    @Override
    public PolicyConfiguration getPolicyByName(String name) {
        return null;
    }

    @Override
    public PolicyConfiguration getActivePolicy() {
        return null;
    }
}
