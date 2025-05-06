package org.example.dandd.model.dao;

import org.example.dandd.model.entities.pg.GitBard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GitBardDao extends JpaRepository<GitBard, Long>
{
}
