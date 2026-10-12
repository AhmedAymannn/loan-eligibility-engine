package com.EXbank.loan_eligibility_engine.api.dto.response;

import java.time.Instant;

public record ErrorResponseDTO(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path
) {}