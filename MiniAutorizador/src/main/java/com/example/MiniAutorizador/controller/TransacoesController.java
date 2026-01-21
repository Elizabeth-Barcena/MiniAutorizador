package com.example.MiniAutorizador.controller;

import com.example.MiniAutorizador.dto.TransacoesResponse;
import com.example.MiniAutorizador.dto.TransactionRequest;
import com.example.MiniAutorizador.service.TransacoesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacoesController {
    @Autowired
    TransacoesService transacoesService;
    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid TransactionRequest request) {
         transacoesService.debitar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }
    @GetMapping("/{numeroCartao}")
    public ResponseEntity<List<TransacoesResponse>> getTransaction(@PathVariable String numeroCartao) {
        return ResponseEntity.ok(transacoesService.getByNumeroCartao(numeroCartao));
    }
}
