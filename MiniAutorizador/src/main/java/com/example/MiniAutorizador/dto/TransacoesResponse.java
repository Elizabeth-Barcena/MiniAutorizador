package com.example.MiniAutorizador.dto;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransacoesResponse(
        String numeroCartao,
        BigDecimal valorDebitado,
        BigDecimal saldo,
        LocalDateTime createdAt
) {
}