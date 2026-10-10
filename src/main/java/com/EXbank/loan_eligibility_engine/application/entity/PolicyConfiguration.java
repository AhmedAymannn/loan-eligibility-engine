package com.EXbank.loan_eligibility_engine.application.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "policy_configurations")
public class PolicyConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Represents the unique primary key of a specific policy row in MySQL

    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(name = "rule_name", nullable = false)
    private String ruleName;

    @Column(name = "parameter_key", nullable = false)
    private String parameterKey;

    @Column(name = "parameter_value", nullable = false)
    private String parameterValue;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public PolicyConfiguration() {
    }

    public PolicyConfiguration(Long id, String productCode, String ruleName, String parameterKey,
                               String parameterValue, Boolean isActive, LocalDateTime updatedAt) {
        this.id = id;
        this.productCode = productCode;
        this.ruleName = ruleName;
        this.parameterKey = parameterKey;
        this.parameterValue = parameterValue;
        this.isActive = isActive;
        this.updatedAt = updatedAt;
    }

    // Getters
    public Long getId() { return id; }
    public String getParameterValue() { return parameterValue; }
    public String getProductCode() { return productCode; }
    public String getRuleName() { return ruleName; }
    public String getParameterKey() { return parameterKey; }
    public Boolean getActive() {return isActive;}
    public LocalDateTime getUpdatedAt() {return updatedAt;}



}
