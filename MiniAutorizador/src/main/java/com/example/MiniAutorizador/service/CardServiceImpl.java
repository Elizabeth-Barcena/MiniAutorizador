package com.example.MiniAutorizador.service;

import com.example.MiniAutorizador.exception.CardAlreadyExistsException;
import com.example.MiniAutorizador.dto.CardRequest;
import com.example.MiniAutorizador.dto.CardResponse;
import com.example.MiniAutorizador.entity.Card;
import com.example.MiniAutorizador.exception.CardNotFoundException;
import com.example.MiniAutorizador.repository.CardRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository repository;

    public CardServiceImpl(CardRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public CardResponse create(CardRequest request) {
        repository.findById(request.numeroCartao())
                .ifPresent(card -> {
                    throw new CardAlreadyExistsException();
                });

        Card card = new Card(request.numeroCartao(), request.senha());
        repository.save(card);

        return new CardResponse(card.getNumeroCartao(), card.getSenha());
    }
    @Transactional
    public BigDecimal getBalance(String numeroCartao) {
        return repository.findById(numeroCartao)
                .map(Card::getSaldo)
                .orElseThrow(CardNotFoundException::new);
    }
    @Transactional
    public List<CardResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(card -> new CardResponse(card.getNumeroCartao(), card.getSenha()))
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