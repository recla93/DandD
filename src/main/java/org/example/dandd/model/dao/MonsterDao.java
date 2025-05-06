package org.example.dandd.model.dao;

import org.example.dandd.model.Monster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonsterDao extends JpaRepository<Monster, Long>
{
}
