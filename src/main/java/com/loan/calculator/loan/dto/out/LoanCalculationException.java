package com.loan.calculator.loan.dto.out;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LoanCalculationException extends RuntimeException {
    public LoanCalculationException(String message) {
        super(message);
    }
}
