package com.example.MiniAutorizador.repository;

import com.example.MiniAutorizador.entity.Card;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.yaml.snakeyaml.events.Event;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, String> {
    void deleteById(String id);
    boolean existsById(String id);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Card c where c.numeroCartao = :numero")
    Optional<Card> findByIdForUpdate(@Param("numero") String numeroCartao);
}