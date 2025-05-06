package org.example.dandd.model.dao;

import org.example.dandd.model.entities.pg.CodeCleaner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeCleanerDao extends JpaRepository<CodeCleaner, Long>
{
}
