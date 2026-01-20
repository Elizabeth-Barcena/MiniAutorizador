package com.example.MiniAutorizador.exception;

public class CardNotFoundException extends BusinessException {
    public CardNotFoundException() {
        super(ErrorCode.CARTAO_INEXISTENTE);
    }
}