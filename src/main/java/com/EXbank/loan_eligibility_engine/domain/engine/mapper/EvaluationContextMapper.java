package com.EXbank.loan_eligibility_engine.domain.engine.mapper;


import com.EXbank.loan_eligibility_engine.api.dto.request.EligibilityEvaluationRequestDTO;
import com.EXbank.loan_eligibility_engine.domain.model.EvaluationContext;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;


@Component
public class EvaluationContextMapper {

    public EvaluationContext toDomainContext(EligibilityEvaluationRequestDTO requestDTO) {
        String applicationId = UUID.randomUUID().toString();
        BigDecimal salary = normalizeCurrency(requestDTO.monthlyNetSalary());
        BigDecimal debt = normalizeCurrency(requestDTO.existingMonthlyDebt());
        BigDecimal requestedAmount = normalizeCurrency(requestDTO.requestedAmount());

        return new EvaluationContext(
                applicationId,
                requestDTO.nationalId(),
                requestDTO.applicantAge(),
                requestDTO.creditScore(),
                salary,
                debt,
                requestedAmount,
                requestDTO.requestedTenureMonths(),
                requestDTO.activeLoans()
        );
    }
    private BigDecimal normalizeCurrency(BigDecimal value) {
        if (value == null) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
