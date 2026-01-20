package com.example.MiniAutorizador.repository;

import com.example.MiniAutorizador.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.yaml.snakeyaml.events.Event;

public interface CardRepository extends JpaRepository<Card, String> {
    void deleteById(String id);
    void updateById(String id);
    boolean existsById(String id);

}