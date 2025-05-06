package org.example.dandd.model.dao;

import org.example.dandd.model.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponDao extends JpaRepository<Weapon,Long>
{
}
