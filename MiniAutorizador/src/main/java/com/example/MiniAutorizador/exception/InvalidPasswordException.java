package com.example.MiniAutorizador.exception;
public class InvalidPasswordException extends BusinessException {
    public InvalidPasswordException() {
        super(ErrorCode.SENHA_INVALIDA);
    }
}
