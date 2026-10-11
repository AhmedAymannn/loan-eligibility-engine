package com.EXbank.loan_eligibility_engine.application.service;

import com.EXbank.loan_eligibility_engine.application.entity.PolicyConfiguration;

public interface PolicyService {

    public PolicyConfiguration getAll();
    public PolicyConfiguration getPolicyByName (String name) ;
    public PolicyConfiguration getAllActivePolicy () ;

}
