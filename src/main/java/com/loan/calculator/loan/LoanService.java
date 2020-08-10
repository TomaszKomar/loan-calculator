package com.loan.calculator.loan;

import com.loan.calculator.loan.dto.in.LoanCalculationRequestDTO;
import com.loan.calculator.loan.dto.out.LoanCalculationException;
import com.loan.calculator.loan.dto.out.LoanCalculationResultDTO;
import com.loan.calculator.loan.persistence.CalculationRepository;
import com.loan.calculator.loan.persistence.LoanCalculation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanService {
    private static final BigDecimal YEARLY_INTERESTS = new BigDecimal("0.04");
    private static final MathContext CALCULATION_CONTEXT = MathContext.DECIMAL128;

    private final CalculationRepository calculationRepository;

    public LoanCalculationResultDTO calculateLoan(LoanCalculationRequestDTO request) {;
        var monthlyInstallment = calculateMonthlyInstallment(request);
        if (request.calculateFreeFunds().compareTo(monthlyInstallment) <= 0) {
            throw new LoanCalculationException("FUNDS_LOWER_THAN_INSTALLMENT");
        }
        var result = new LoanCalculationResultDTO(request, monthlyInstallment);
        calculationRepository.save(new LoanCalculation(request, result));
        return result;
    }

    /**
     * Calculate monthly installment according to
     * amount * (q ^ paymentCount) * (q-1) / ((q^paymentCount) - 1)
     * Where q = 1 + (yearly interests / 12)
     *
     * @param request containing calculation params
     * @return monthly installment
     */
    private BigDecimal calculateMonthlyInstallment(LoanCalculationRequestDTO request) {
        var q = BigDecimal.ONE.add(YEARLY_INTERESTS.divide(new BigDecimal(12), CALCULATION_CONTEXT));
        var qPowPaymentCount = q.pow(request.getPaymentCount());
        return request.getAmount()
                .multiply(qPowPaymentCount)
                .multiply(q.subtract(BigDecimal.ONE))
                .divide(qPowPaymentCount.subtract(BigDecimal.ONE), CALCULATION_CONTEXT).setScale(2, RoundingMode.HALF_UP);
    }

    public Long countSavedLoanCalculations() {
        return calculationRepository.count();
    }
}

