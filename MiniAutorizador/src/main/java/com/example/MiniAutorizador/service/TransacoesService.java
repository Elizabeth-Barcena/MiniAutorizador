package com.example.MiniAutorizador.service;

import com.example.MiniAutorizador.dto.TransacoesResponse;
import com.example.MiniAutorizador.dto.TransactionRequest;
import com.example.MiniAutorizador.entity.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransacoesService {
    void debitar(TransactionRequest transacoesRequest);

    List<TransacoesResponse> getByNumeroCartao(String numeroCartao);
}
