package com.example.MiniAutorizador.exception;

public class CardAlreadyExistsException extends BusinessException {
    public CardAlreadyExistsException() {
        super(ErrorCode.CARTAO_JA_EXISTENTE);
    }
}
