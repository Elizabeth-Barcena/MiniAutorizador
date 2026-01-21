package com.example.MiniAutorizador.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor", nullable = false)
    private BigDecimal valorDebitado;

    @Column(name = "saldo_resultante", nullable = false)
    private BigDecimal saldo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numero_cartao", nullable = false)
    private Card card;

    protected Transaction() {
    }

    public Transaction(Card card, BigDecimal valorDebitado, BigDecimal saldo) {
        this.card = card;
        this.valorDebitado = valorDebitado;
        this.saldo = saldo;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValorDebitado() {
        return valorDebitado;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Card getCard() {
        return card;
    }
}
