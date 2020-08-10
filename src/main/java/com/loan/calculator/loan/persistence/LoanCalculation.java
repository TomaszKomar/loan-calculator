package com.loan.calculator.loan.persistence;

import com.loan.calculator.loan.dto.in.LoanCalculationRequestDTO;
import com.loan.calculator.loan.dto.out.LoanCalculationResultDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class LoanCalculation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_calculations_gen")
    @SequenceGenerator(name = "loan_calculations_gen", sequenceName = "loan_calculations_seq")
    Long id;

    Integer paymentCount;
    BigDecimal amount;
    BigDecimal income;
    BigDecimal expenses;
    BigDecimal installment;
    BigDecimal totalCost;


    public LoanCalculation(LoanCalculationRequestDTO request, LoanCalculationResultDTO result) {
        this.paymentCount = request.getPaymentCount();
        this.amount = request.getAmount();
        this.income = request.getIncome();
        this.expenses = request.getExpenses();
        this.installment = new BigDecimal(result.getMonthlyInstallment());
        this.totalCost = new BigDecimal(result.getTotalCost());
    }
}
