package com.example.MiniAutorizador.serviceTest;

import com.example.MiniAutorizador.repository.CardRepository;
import com.example.MiniAutorizador.service.CardService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransactionE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CardService cardService;

    @Test
    @Order(1)
    void deveCriarCartao() throws Exception {
        mockMvc.perform(post("/cards")
                        .with(httpBasic("username", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "numeroCartao": "1234567890000",
                                  "senha": "1234"
                                }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    void deveRetornarSaldoInicial() throws Exception {
        mockMvc.perform(get("/cards/1234567890000")
                        .with(httpBasic("username", "password")))
                .andExpect(status().isOk())
                .andExpect(content().string("500.00"));
    }

    @Test
    @Order(3)
    void deveRealizarTransacao() throws Exception {
        mockMvc.perform(post("/transacoes")
                        .with(httpBasic("username", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "numeroCartao": "1234567890000",
                                  "senha": "1234",
                                  "valor": 100.00
                                }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(4)
    void deveRetornarSaldoAtualizado() throws Exception {
        mockMvc.perform(get("/cards/1234567890000")
                        .with(httpBasic("username", "password")))
                .andExpect(status().isOk())
                .andExpect(content().string("400.00"));
    }

    @Test
    @Order(5)
    void deveBloquearTransacaoPorSaldoInsuficiente() throws Exception {
        mockMvc.perform(post("/transacoes")
                        .with(httpBasic("username", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "numeroCartao": "1234567890000",
                                  "senha": "1234",
                                  "valor": 1000.00
                                }
                                """))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message")
                        .value("SALDO_INSUFICIENTE"));
    }
    @Test
    @Order(6)
    void deveDeletarCartao() throws Exception {
        mockMvc.perform(
                        delete("/cards/1234567890000")
                                .with(httpBasic("username", "password"))
                )
                .andExpect(status().isNoContent());
    }


}
