package com.example.MiniAutorizador.repository;

import com.example.MiniAutorizador.entity.Card;
import com.example.MiniAutorizador.entity.Transaction;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCard_NumeroCartao(String numeroCartao);
}
