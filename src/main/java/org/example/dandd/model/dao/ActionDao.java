package org.example.dandd.model.dao;


import org.example.dandd.model.entities.Action;
import org.example.dandd.model.entities.Monster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionDao extends JpaRepository<Action, Long>
{
	Action findByNameAction(String name);
}
