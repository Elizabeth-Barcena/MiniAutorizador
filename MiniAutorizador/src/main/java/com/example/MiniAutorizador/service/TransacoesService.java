package com.example.MiniAutorizador.service;

import com.example.MiniAutorizador.dto.TransactionRequest;

import java.math.BigDecimal;

public interface TransacoesService {
    void debitar(TransactionRequest transacoesRequest);
}
