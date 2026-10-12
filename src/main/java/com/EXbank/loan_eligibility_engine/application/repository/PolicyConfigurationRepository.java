package com.EXbank.loan_eligibility_engine.application.repository;


import com.EXbank.loan_eligibility_engine.application.entity.PolicyConfiguration;
import org.springframework.data.repository.Repository;
import java.util.Optional;

public interface PolicyConfigurationRepository extends Repository <PolicyConfiguration, Long> {

    Optional <PolicyConfiguration> findByProductCodeAndActiveIsTrue (String productCode) ;

}
