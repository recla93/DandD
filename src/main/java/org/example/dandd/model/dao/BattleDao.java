package org.example.dandd.model.dao;

import org.example.dandd.model.entities.Battle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BattleDao extends JpaRepository<Battle, Long>
{
}
