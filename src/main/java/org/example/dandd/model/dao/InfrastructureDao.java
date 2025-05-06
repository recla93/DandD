package org.example.dandd.model.dao;

import org.example.dandd.model.entities.pg.Infrastructure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfrastructureDao extends JpaRepository<Infrastructure, Long>
{
}
