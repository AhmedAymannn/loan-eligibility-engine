package com.EXbank.loan_eligibility_engine.domain.rules.Helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
@Component
public class InstallmentCalculator {

    public BigDecimal calculateMonthlyInstallment(BigDecimal principal,
                                                  int months,
                                                  BigDecimal annualRatePercent) {
        // Guard clause for invalid inputs
        if (principal == null || principal.compareTo(BigDecimal.ZERO) <= 0 || months <= 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        // 0% interest fallback -> simple principal division (P / n)
        if (annualRatePercent == null || annualRatePercent.signum() == 0) {
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