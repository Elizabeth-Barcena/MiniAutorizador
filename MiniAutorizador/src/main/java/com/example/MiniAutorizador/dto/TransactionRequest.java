package com.example.MiniAutorizador.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record TransactionRequest(
        @NotBlank(message = "NUMERO_CARTAO_OBRIGATORIO")
        String numeroCartao,
        @NotBlank(message = "SENHA_OBRIGATORIA")
        String senha,
        BigDecimal valor
) {
}