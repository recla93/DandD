package org.example.dandd.model.dao;


import org.example.dandd.model.entities.Monster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonsterDao extends JpaRepository<org.example.dandd.model.entities.Monster, Long>
{
}
