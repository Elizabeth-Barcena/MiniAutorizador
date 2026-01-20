package com.example.MiniAutorizador.dto;

import jakarta.validation.constraints.NotBlank;

public record CardRequest(
        @NotBlank(message = "NUMERO_CARTAO_OBRIGATORIO")
        String numeroCartao,
        @NotBlank(message = "SENHA_OBRIGATORIA")
        String senha
) {
}