package com.EXbank.loan_eligibility_engine.service;


import com.EXbank.loan_eligibility_engine.domain.model.EvaluationContext;
import org.springframework.stereotype.Service;

@Service
public class EvaluationContextMapper {
    private final EvaluationContext evaluationContext ;

    public EvaluationContextMapper(EvaluationContext evaluationContext) {
        this.evaluationContext = evaluationContext;
    }
    // Mapping the incoming Dto ==> EvaluationContext

    EvaluationContext map (EvaluationContext context){

    }
}
