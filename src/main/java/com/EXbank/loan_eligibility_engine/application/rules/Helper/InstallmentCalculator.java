package com.EXbank.loan_eligibility_engine.application.rules.Helper;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Component
public class InstallmentCalculator {

    public BigDecimal calculateMonthlyInstallment(BigDecimal principal,
                                                  int months,
                                                  BigDecimal annualRatePercent) {
        if (principal == null || principal.signum() <= 0) {
            throw new IllegalArgumentException("principal must be positive");
        }
        if (months <= 0) {
            throw new IllegalArgumentException("months must be positive");
        }
        if (annualRatePercent == null || annualRatePercent.signum() < 0) {
            throw new IllegalArgumentException("annualRatePercent must be zero or positive");
        }

        // explicit 0% interest -> simple principal division (P / n)
        if (annualRatePercent.signum() == 0) {
            return principal.divide(BigDecimal.valueOf(months), 2, RoundingMode.HALF_UP);
        }

        // Monthly interest rate r = annualRate / 1200
        BigDecimal r = annualRatePercent.divide(BigDecimal.valueOf(1200), 10, RoundingMode.HALF_UP);

        // (1 + r)^months
        BigDecimal pow = BigDecimal.ONE.add(r).pow(months, MathContext.DECIMAL64);

        // PMT = P * r * pow / (pow - 1)
        return principal.multiply(r)
                .multiply(pow)
                .divide(pow.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
    }
}