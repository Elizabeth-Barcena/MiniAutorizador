package com.example.MiniAutorizador.entity;

import com.example.MiniAutorizador.exception.BusinessException;
import com.example.MiniAutorizador.exception.ErrorCode;
import jakarta.persistence.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;


@Entity
@Table(name = "cards")
public class Card {

    @Id
    @Column(name = "numero_cartao", nullable = false, unique = true, length = 16)
    private String numeroCartao;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal saldo;

    protected Card() {
    }

    public Card(String numeroCartao, String senha) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        this.saldo = new BigDecimal("500.00");
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public String getSenha() {
        return senha;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void validarSenha(String senhaInformada, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(senhaInformada, this.senha)) {
            throw new BusinessException(ErrorCode.SENHA_INVALIDA);
        }
    }
    public void validarValor(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ErrorCode.VALOR_INVALIDO);
        }
    }


    public void validarSaldo(BigDecimal valor) {
        if (saldo.compareTo(valor) < 0) {
            throw new BusinessException(ErrorCode.SALDO_INSUFICIENTE);
        }
    }


    public void debitar(BigDecimal valor) {
        this.saldo = this.saldo.subtract(valor);
    }
}
