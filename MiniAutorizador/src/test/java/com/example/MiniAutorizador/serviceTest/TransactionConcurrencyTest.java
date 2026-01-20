package com.example.MiniAutorizador.serviceTest;

import com.example.MiniAutorizador.dto.TransactionRequest;
import com.example.MiniAutorizador.entity.Card;
import com.example.MiniAutorizador.repository.CardRepository;
import com.example.MiniAutorizador.service.CardService;
import com.example.MiniAutorizador.service.TransacoesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TransactionConcurrencyTest {

    @Autowired
    private TransacoesService transacoesService;

    @Autowired
    private CardRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        repository.deleteAll();

        Card card = new Card(
                "123",
                passwordEncoder.encode("1234")
        );

        card.debitar(new BigDecimal("490.00"));
        repository.save(card);
    }

    @Test
    void apenasUmaTransacaoDeveSerAprovada() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);

        Runnable tarefa = () -> {
            try {
                transacoesService.debitar(new TransactionRequest(
                        "123",
                        "1234",
                        new BigDecimal("10.00")
                ));
            } catch (Exception ignored) {
            } finally {
                latch.countDown();
            }
        };

        executor.submit(tarefa);
        executor.submit(tarefa);

        latch.await();

        Card cardFinal = repository.findById("123").orElseThrow();

        assertEquals(
                0,
                cardFinal.getSaldo().compareTo(BigDecimal.ZERO)
        );
    }
}
