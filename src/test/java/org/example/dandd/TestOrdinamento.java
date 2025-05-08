package org.example.dandd;

import org.example.dandd.model.dao.MonsterDao;
import org.example.dandd.model.dao.PgPlayableDao;
import org.example.dandd.model.entities.Monster;
import org.example.dandd.model.entities.pg.PgPlayable;
import org.example.dandd.service.BattleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestOrdinamento
{
	@Autowired
	PgPlayableDao pgPlayableDao;

	@Autowired
	MonsterDao monsterDao;

	@Autowired
	BattleService battleService;


	@Test
	public void TestOrdinamento()
	{
		List<PgPlayable> player = pgPlayableDao.findAll();

		List<Monster> monster = monsterDao.findAll();

		battleService.iniziaFight(player,monster);


	}
}
