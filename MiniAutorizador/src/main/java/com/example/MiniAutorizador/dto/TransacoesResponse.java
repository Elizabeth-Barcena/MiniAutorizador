package com.example.MiniAutorizador.dto;


public record TransacoesResponse(
        String numeroCartao,
        String senha,
        String valor
) {
}