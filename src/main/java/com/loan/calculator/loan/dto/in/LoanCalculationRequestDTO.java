package com.loan.calculator.loan.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanCalculationRequestDTO {
    @Positive
    @NotNull
    Integer paymentCount;

    @Positive
    @NotNull
    BigDecimal amount;

    @Positive
    @NotNull
    BigDecimal income;

    @Positive
    @NotNull
    BigDecimal expenses;

    public BigDecimal calculateFreeFunds() {
        return income.subtract(expenses);
    }
}
