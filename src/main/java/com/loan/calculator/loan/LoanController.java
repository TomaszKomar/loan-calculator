package com.loan.calculator.loan;

import com.loan.calculator.loan.dto.in.LoanCalculationRequestDTO;
import com.loan.calculator.loan.dto.out.LoanCalculationResultDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/loan")
public class LoanController {
    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanCalculationResultDTO> calculateLoanCosts(@Valid @RequestBody LoanCalculationRequestDTO request) {
        return ResponseEntity.ok(loanService.calculateLoan(request));
    }
}
