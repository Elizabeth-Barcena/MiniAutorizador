package com.example.MiniAutorizador.service;

import com.example.MiniAutorizador.exception.*;
import com.example.MiniAutorizador.dto.CardRequest;
import com.example.MiniAutorizador.dto.CardResponse;
import com.example.MiniAutorizador.entity.Card;
import com.example.MiniAutorizador.repository.CardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private  CardRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public CardResponse create(CardRequest request) {
        validarRequest(request);

        Card card = new Card(
                request.numeroCartao(),
                passwordEncoder.encode(request.senha())
        );

        repository.save(card);

        return new CardResponse(card.getNumeroCartao(), card.getSaldo());
    }

    private void validarRequest(CardRequest request) {
        Optional.ofNullable(request.numeroCartao())
                .filter(n -> !n.isBlank())
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.NUMERO_CARTAO_OBRIGATORIO)
                );

        Optional.ofNullable(request.senha())
                .filter(s -> !s.isBlank())
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.SENHA_OBRIGATORIA)
                );


        repository.findById(request.numeroCartao())
                .ifPresent(card -> {
                    throw new BusinessException(ErrorCode.CARTAO_JA_EXISTENTE);
                });
    }

    @Transactional()
    public BigDecimal getBalance(String numeroCartao) {
        return repository.findById(numeroCartao)
                .map(Card::getSaldo)
                .orElseThrow(() ->
                        new CardNotFoundException()
                );
    }

    @Transactional
    public List<CardResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(card -> new CardResponse(card.getNumeroCartao(), card.getSaldo()))
                .toList();
    }

    @Transactional
    @Override
    public void delete(String numeroCartao) {
        if (!repository.existsById(numeroCartao)) {
            throw new CardNotFoundException();
        }

        repository.deleteById(numeroCartao);
    }
}