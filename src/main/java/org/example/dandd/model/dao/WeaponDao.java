package org.example.dandd.model.dao;


import org.example.dandd.model.entities.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponDao extends JpaRepository<Weapon,Long>
{
}
