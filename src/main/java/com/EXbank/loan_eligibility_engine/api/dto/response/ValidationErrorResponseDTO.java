package com.EXbank.loan_eligibility_engine.api.dto.response;

import java.time.Instant;
import java.util.List;

public record ValidationErrorResponseDTO(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path
//        List<FieldErrorDetail> fieldErrors
) {
}
