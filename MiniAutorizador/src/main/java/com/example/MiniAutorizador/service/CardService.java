package com.example.MiniAutorizador.service;

import com.example.MiniAutorizador.dto.CardRequest;
import com.example.MiniAutorizador.dto.CardResponse;

import java.math.BigDecimal;
import java.util.List;

public interface CardService {

    CardResponse create(CardRequest request);

    BigDecimal getBalance(String cardNumber);

    List<CardResponse> getAll();

    void delete(String numeroCartao);
}