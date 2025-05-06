package org.example.dandd.model.dao;

import org.example.dandd.model.entities.GameState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameStateDao extends JpaRepository<GameState, Long>
{
}
