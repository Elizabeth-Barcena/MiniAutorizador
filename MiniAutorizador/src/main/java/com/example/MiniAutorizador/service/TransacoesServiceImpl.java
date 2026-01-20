package com.example.MiniAutorizador.service;

import com.example.MiniAutorizador.dto.TransactionRequest;
import com.example.MiniAutorizador.entity.Card;
import com.example.MiniAutorizador.exception.CardNotFoundException;
import com.example.MiniAutorizador.repository.CardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransacoesServiceImpl implements TransacoesService {
    @Autowired
    private CardRepository repository;

    @Transactional
    public void debitar(TransactionRequest request) {

        Card card = repository.findByIdForUpdate(request.numeroCartao())
                .orElseThrow(CardNotFoundException::new);

        card.validarSenha(request.senha());
        card.validarSaldo(request.valor());
        card.debitar(request.valor());
    }

}
