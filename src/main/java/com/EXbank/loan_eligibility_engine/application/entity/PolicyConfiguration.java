
package com.EXbank.loan_eligibility_engine.application.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Immutable
@Table(name = "policy_configurations")
public class PolicyConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_code")
    private String productCode;

    // ---- thresholds (same order as RuleValues) ----
    @Column(name = "min_age")
    private int minAge;

    @Column(name = "max_maturity_age")
    private int maxMaturityAge;

    @Column(name = "min_credit_score")
    private int minCreditScore;

    @Column(name = "min_monthly_income")
    private BigDecimal minMonthlyIncome;

    @Column(name = "min_residual_income")
    private BigDecimal minResidualIncome;

    @Column(name = "max_dti_percent")
    private BigDecimal maxDtiPercent;

    @Column(name = "max_income_multiple")
    private BigDecimal maxIncomeMultiple;

    @Column(name = "max_active_loans")
    private int maxActiveLoans;

    @Column(name = "min_tenure_months")
    private int minTenureMonths;

    @Column(name = "max_tenure_months")
    private int maxTenureMonths;

    @Column(name = "min_amount")
    private BigDecimal minAmount;

    @Column(name = "max_amount")
    private BigDecimal maxAmount;

    @Column(name = "annual_interest_rate")
    private BigDecimal annualInterestRate;

    @Column(name = "tier_premium_score")
    private int tierPremiumScore;

    @Column(name = "tier_standard_max_amount")
    private BigDecimal tierStandardMaxAmount;

    // ---- lifecycle and audit ----
    @Column(name = "is_active")
    private boolean active;

    @Column(name = "change_reason")
    private String changeReason;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    protected PolicyConfiguration() {}   // required by JPA

    public Long getId()                        { return id; }
    public String getProductCode()             { return productCode; }
    public int getMinAge()                     { return minAge; }
    public int getMaxMaturityAge()             { return maxMaturityAge; }
    public int getMinCreditScore()             { return minCreditScore; }
    public BigDecimal getMinMonthlyIncome()    { return minMonthlyIncome; }
    public BigDecimal getMinResidualIncome()   { return minResidualIncome; }
    public BigDecimal getMaxDtiPercent()       { return maxDtiPercent; }
    public BigDecimal getMaxIncomeMultiple()   { return maxIncomeMultiple; }
    public int getMaxActiveLoans()             { return maxActiveLoans; }
    public int getMinTenureMonths()            { return minTenureMonths; }
    public int getMaxTenureMonths()            { return maxTenureMonths; }
    public BigDecimal getMinAmount()           { return minAmount; }
    public BigDecimal getMaxAmount()           { return maxAmount; }
    public BigDecimal getAnnualInterestRate()  { return annualInterestRate; }
    public int getTierPremiumScore()           { return tierPremiumScore; }
    public BigDecimal getTierStandardMaxAmount() { return tierStandardMaxAmount; }
    public boolean isActive()                  { return active; }
    public String getChangeReason()            { return changeReason; }
    public String getCreatedBy()               { return createdBy; }
    public LocalDateTime getCreatedAt()        { return createdAt; }
}