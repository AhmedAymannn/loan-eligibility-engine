package com.EXbank.loan_eligibility_engine.api.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record EligibilityEvaluationRequestDTO(

        @NotBlank(message = "Application ID is required.")
        String applicationId,

        @NotBlank(message = "National ID is required.")
        @Pattern(regexp = "^[0-9]{14}$", message = "National ID must be exactly 14 digits.")
        String nationalId,

        @NotNull(message = "Applicant age is required.")
        @Min(value = 18, message = "Applicant must be at least 18 years old.")
        @Max(value = 70, message = "Applicant age cannot exceed 70 years.")
        Integer applicantAge,

        @NotNull(message = "Credit score is required.")
        @Min(value = 300, message = "Credit score must be at least 300.")
        @Max(value = 850, message = "Credit score cannot exceed 850.")
        Integer creditScore,

        @NotNull(message = "Monthly net salary is required.")
        @Positive(message = "Monthly net salary must be strictly greater than zero.")
        BigDecimal monthlyNetSalary,

        @NotNull(message = "Existing monthly debt is required.")
        @Min(value = 0, message = "Existing monthly debt cannot be negative.")
        BigDecimal existingMonthlyDebt,

        @NotNull(message = "Requested loan amount is required.")
        @Positive(message = "Requested loan amount must be strictly greater than zero.")
        BigDecimal requestedAmount,

        @NotNull(message = "Requested tenure in months is required.")
        @Min(value = 6, message = "Minimum loan tenure is 6 months.")
        @Max(value = 84, message = "Maximum loan tenure is 84 months.")
        Integer requestedTenureMonths
) {}