package com.EXbank.loan_eligibility_engine.application.repository;


import com.EXbank.loan_eligibility_engine.application.entity.PolicyConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyConfigurationRepository extends JpaRepository < PolicyConfiguration, Long > {



}
