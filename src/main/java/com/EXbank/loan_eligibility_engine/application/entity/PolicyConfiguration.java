package com.EXbank.loan_eligibility_engine.application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Immutable;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "policy_configurations")
@Immutable
public class PolicyConfiguration {

    @Id
    private Long id;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "min_credit_score")
    private int minCreditScore;

    @Column(name = "min_age")
    private int minAge;

    @Column(name = "min_monthly_income")
    private BigDecimal minMonthlyIncome;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "change_reason")
    private String changeReason;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    protected PolicyConfiguration() {}   // required by JPA

    public Long getId()                     { return id; }
    public String getProductCode()          { return productCode; }
    public int getMinCreditScore()          { return minCreditScore; }
    public int getMinAge()                  { return minAge; }
    public BigDecimal getMinMonthlyIncome() { return minMonthlyIncome; }
    public boolean isActive()               { return active; }
    public String getChangeReason()         { return changeReason; }
    public String getCreatedBy()            { return createdBy; }
    public LocalDateTime getCreatedAt()     { return createdAt; }


}