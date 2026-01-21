package com.example.MiniAutorizador.service;

import com.example.MiniAutorizador.dto.TransacoesResponse;
import com.example.MiniAutorizador.dto.TransactionRequest;
import com.example.MiniAutorizador.entity.Card;
import com.example.MiniAutorizador.entity.Transaction;
import com.example.MiniAutorizador.exception.CardNotFoundException;
import com.example.MiniAutorizador.repository.CardRepository;
import com.example.MiniAutorizador.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransacoesServiceImpl implements TransacoesService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void debitar(TransactionRequest request) {

        Card card = cardRepository.findByIdForUpdate(request.numeroCartao())
                .orElseThrow(() ->
                        new CardNotFoundException()
                );
        card.validarValor(request.valor());
        card.validarSenha(request.senha(), passwordEncoder);
        card.validarSaldo(request.valor());
        card.debitar(request.valor());
        card.registrarTransacao(request.valor());

        cardRepository.save(card);
    }

    @Override
    public List<TransacoesResponse> getByNumeroCartao(String numeroCartao) {
        return transactionRepository.findByCard_NumeroCartao(numeroCartao)
                .stream()
                .map(transacao ->
                        new TransacoesResponse(
                                transacao.getCard().getNumeroCartao(),
                                transacao.getValorDebitado(),
                                transacao.getSaldo(),
                                transacao.getCreatedAt()
                        )
                )
                .toList();
    }

}
