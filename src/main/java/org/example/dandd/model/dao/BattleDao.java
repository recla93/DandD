package org.example.dandd.model.dao;

import org.example.dandd.service.BattleService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BattleDao extends JpaRepository<BattleService, Long>
{
}
