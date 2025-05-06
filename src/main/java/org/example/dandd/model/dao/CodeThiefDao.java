package org.example.dandd.model.dao;

import org.example.dandd.model.entities.pg.CodeThief;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeThiefDao extends JpaRepository<CodeThief, Long>
{
}
