package com.example.MiniAutorizador.exception;

public class InsufficientBalanceException extends BusinessException {
    public InsufficientBalanceException() {
        super(ErrorCode.SALDO_INSUFICIENTE);
    }
}

