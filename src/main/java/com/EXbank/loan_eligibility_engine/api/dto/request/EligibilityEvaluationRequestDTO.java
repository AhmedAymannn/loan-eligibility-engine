package com.EXbank.loan_eligibility_engine.api.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;


public record EligibilityEvaluationRequestDTO(

        @NotBlank(message = "National ID is required.")
        @Pattern(regexp = "^[0-9]{14}$", message = "National ID must be exactly 14 digits.")
        String nationalId,

        @NotBlank(message = "productCode is required")
        @Size(max = 50, message = "productCode must be at most 50 characters")
        @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_]*$",
                message = "productCode may contain only letters, digits and underscores")
        String productCode,


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
        @DecimalMin(value = "0.0", inclusive = true, message = "Existing monthly debt cannot be negative.")
        BigDecimal existingMonthlyDebt,

        @NotNull(message = "Requested loan amount is required.")
        @Positive(message = "Requested loan amount must be strictly greater than zero.")
        BigDecimal requestedAmount,

        @NotNull(message = "Requested tenure in months is required.")
        @Min(value = 6, message = "Minimum loan tenure is 6 months.")
        @Max(value = 84, message = "Maximum loan tenure is 84 months.")
        Integer requestedTenureMonths,

        @NotNull(message = "Active loans count is required.")
        @Min(value = 0, message = "Active loans count cannot be negative.")
        Integer activeLoans
) {
}