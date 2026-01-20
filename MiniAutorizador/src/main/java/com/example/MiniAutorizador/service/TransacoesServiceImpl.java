package com.example.MiniAutorizador.service;

import com.example.MiniAutorizador.dto.TransactionRequest;
import com.example.MiniAutorizador.entity.Card;
import com.example.MiniAutorizador.exception.BusinessException;
import com.example.MiniAutorizador.exception.CardNotFoundException;
import com.example.MiniAutorizador.exception.ErrorCode;
import com.example.MiniAutorizador.repository.CardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TransacoesServiceImpl implements TransacoesService {
    @Autowired
    private CardRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void debitar(TransactionRequest request) {

        Card card = repository.findByIdForUpdate(request.numeroCartao())
                .orElseThrow(() ->
                        new CardNotFoundException()
                );

        card.validarSenha(request.senha(), passwordEncoder);
        card.validarSaldo(request.valor());
        card.debitar(request.valor());
    }

}
