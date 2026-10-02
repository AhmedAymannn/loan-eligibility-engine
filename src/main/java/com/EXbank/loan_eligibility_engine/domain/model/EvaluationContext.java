package com.EXbank.loan_eligibility_engine.domain.model;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class EvaluationContext {
   private final String applicationId ;
   private final String nationalId ;
   private final int applicantAge;
   private final int creditScore ;
   private final BigDecimal calculateDti;

    public EvaluationContext(String applicationId, String nationalId, int applicantAge, int IScore, int creditScore, BigDecimal calculateDti) {
        this.applicationId = applicationId;
        this.nationalId = nationalId;
        this.applicantAge = applicantAge;
        this.creditScore = creditScore;
        this.calculateDti = calculateDti;
    }

    public int getApplicantAge() {
        return applicantAge;
    }



    public int getCreditScore() {
        return creditScore;
    }


    public BigDecimal getCalculateDti() {
        return calculateDti;
    }


    public String getApplicationId() {
        return applicationId;
    }

    public String getNationalId() {
        return nationalId;
    }
}
