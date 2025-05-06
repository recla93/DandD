package org.example.dandd.dao;

import org.example.dandd.model.entities.GameState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameStateDao extends JpaRepository<GameState, Long> {}
