package com.example.MiniAutorizador.controller;

import com.example.MiniAutorizador.dto.CardRequest;
import com.example.MiniAutorizador.dto.CardResponse;
import com.example.MiniAutorizador.service.CardService;
import com.example.MiniAutorizador.service.CardServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    @Autowired
    private CardService service;

    @PostMapping
    public ResponseEntity<CardResponse> create(@RequestBody @Valid CardRequest request) {
        CardResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<String> getBalance(@PathVariable String numeroCartao) {
        return ResponseEntity.ok(service.getBalance(numeroCartao).toString());
    }

    @GetMapping
    public ResponseEntity<List<CardResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/{numeroCartao}")
    public ResponseEntity<Void> delete(@PathVariable String numeroCartao) {
        service.delete(numeroCartao);
        return ResponseEntity.noContent().build();
    }
}