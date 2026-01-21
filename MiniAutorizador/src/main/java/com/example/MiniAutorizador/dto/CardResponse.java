package com.example.MiniAutorizador.dto;


import java.math.BigDecimal;

public record CardResponse(
        String numeroCartao,
        BigDecimal saldo
) {
}