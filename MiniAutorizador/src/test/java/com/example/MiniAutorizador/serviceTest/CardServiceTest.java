package com.example.MiniAutorizador.serviceTest;

import com.example.MiniAutorizador.dto.CardRequest;
import com.example.MiniAutorizador.entity.Card;
import com.example.MiniAutorizador.exception.BusinessException;
import com.example.MiniAutorizador.exception.ErrorCode;
import com.example.MiniAutorizador.repository.CardRepository;
import com.example.MiniAutorizador.service.CardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private CardRepository repository;

    @InjectMocks
    private CardServiceImpl service;

    private Card card;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        card = new Card("123", "$hash");
    }


    @Test
    void validarSenhaCorreta() {
        Mockito.when(passwordEncoder.matches("1234", "$hash"))
                .thenReturn(true);

        assertDoesNotThrow(() ->
                card.validarSenha("1234", passwordEncoder)
        );
    }

    @Test
    void lancarExcecaoParaSenhaIncorreta() {
        Mockito.when(passwordEncoder.matches("5855", "$hash"))
                .thenReturn(false);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> card.validarSenha("5855", passwordEncoder)
        );

        assertEquals(ErrorCode.SENHA_INVALIDA, exception.getErrorCode());
    }

    @Test
    void validarSaldoSuficiente() {
        assertDoesNotThrow(() ->
                card.validarSaldo(new BigDecimal("10.00")));
    }

    @Test
    void lancarExcecaoParaSaldoInsuficiente() {

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> card.validarSaldo(new BigDecimal("600.00"))
        );

        assertEquals(ErrorCode.SALDO_INSUFICIENTE, exception.getErrorCode());
    }


    @Test
    void debitarSaldo() {
        card.debitar(new BigDecimal("100.00"));

        assertEquals(new BigDecimal("400.00"), card.getSaldo());
    }
    @Test
    void retornaSaldoCorreto() {
        Mockito.when(repository.findById("123"))
                .thenReturn(Optional.of(card));

        BigDecimal saldo = service.getBalance("123");

        assertEquals(new BigDecimal("500.00"), saldo);
    }
    @Test
    void criarCartaoComNumeroVazio() {

        CardRequest request = new CardRequest("", "1234");

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.create(request)
        );

        assertEquals("NUMERO_CARTAO_OBRIGATORIO", exception.getErrorCode().name());
    }
    @Test
    void criarCartaoComSenhaEmBranco() {

        CardRequest request = new CardRequest("123", "");

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> service.create(request)
        );

        assertEquals("SENHA_OBRIGATORIA", exception.getErrorCode().name());
    }

}