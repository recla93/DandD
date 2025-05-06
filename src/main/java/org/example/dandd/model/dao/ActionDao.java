package org.example.dandd.model.dao;

import org.example.dandd.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionDao extends JpaRepository<Action, Long>
{
}
