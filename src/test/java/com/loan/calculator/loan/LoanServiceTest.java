package com.loan.calculator.loan;

import com.loan.calculator.loan.dto.in.LoanCalculationRequestDTO;
import com.loan.calculator.loan.dto.out.LoanCalculationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
public class LoanServiceTest {
    @Autowired
    private LoanService loanService;

    @Test
    public void calculateLoan_shouldThrowErrorWhenLoanImpossible() {
        assertThrows(LoanCalculationException.class, () -> {
                    loanService.calculateLoan(
                            LoanCalculationRequestDTO.builder()
                                    .amount(BigDecimal.valueOf(10000))
                                    .expenses(BigDecimal.valueOf(500))
                                    .income(BigDecimal.valueOf(500))
                                    .paymentCount(10)
                                    .build()
                    );
                }
        );
    }

    @Test
    public void calculateLoan_shouldCorrectlyCalculateMonthlyInstallment() {
        var loanRequest =  LoanCalculationRequestDTO.builder()
                .amount(BigDecimal.valueOf(20000))
                .expenses(BigDecimal.valueOf(500))
                .income(BigDecimal.valueOf(10000))
                .paymentCount(24)
                .build();
        var response = loanService.calculateLoan(loanRequest);
        assertEquals("20844.00",response.getTotalCost());
        assertEquals(LocalDate.now().plus(24, ChronoUnit.MONTHS),response.getEndDate());
        assertEquals("868.50",response.getMonthlyInstallment());
    }

    @Test
    public void successfulCalculateLoan_shouldSaveCalculationDetails() {
        var loanRequest =  LoanCalculationRequestDTO.builder()
                .amount(BigDecimal.valueOf(20000))
                .expenses(BigDecimal.valueOf(500))
                .income(BigDecimal.valueOf(10000))
                .paymentCount(24)
                .build();
        var previousCount = loanService.countSavedLoanCalculations();
        var response = loanService.calculateLoan(loanRequest);
        assertEquals(loanService.countSavedLoanCalculations(),previousCount+1);
    }
}