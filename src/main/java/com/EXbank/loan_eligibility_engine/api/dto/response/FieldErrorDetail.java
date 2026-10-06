package com.EXbank.loan_eligibility_engine.api.dto.response;

public record FieldErrorDetail(
        String field,
        Object rejectedValue,
        String message
) {
}
