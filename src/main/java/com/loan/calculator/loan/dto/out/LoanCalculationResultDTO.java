package com.loan.calculator.loan.dto.out;

import com.loan.calculator.loan.dto.in.LoanCalculationRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanCalculationResultDTO {
    private String totalCost;
    private LocalDate endDate;
    private String monthlyInstallment;

    public LoanCalculationResultDTO(LoanCalculationRequestDTO request, BigDecimal installment) {
        this.totalCost = installment.multiply(BigDecimal.valueOf(request.getPaymentCount())).toPlainString();
        this.monthlyInstallment = installment.toPlainString();
        this.endDate = LocalDate.now().plus(request.getPaymentCount(), ChronoUnit.MONTHS);
    }
}
