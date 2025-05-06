package org.example.dandd.model.dao;

import org.example.dandd.model.entities.pg.TroubleShooter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TroubleShooterDao extends JpaRepository<TroubleShooter, Long>
{
}
