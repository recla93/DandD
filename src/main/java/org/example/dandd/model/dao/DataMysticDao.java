package org.example.dandd.model.dao;

import org.example.dandd.model.entities.pg.DataMystic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataMysticDao extends JpaRepository<DataMystic, Long>
{
}
