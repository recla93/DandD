package org.example.dandd.model.dao;

import org.example.dandd.model.entities.pg.PgPlayable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PgPlayableDao extends JpaRepository<PgPlayable, Long>
{
	PgPlayable findByName(String name);
}
