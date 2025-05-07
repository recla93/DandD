package org.example.dandd;


import org.example.dandd.model.dao.ActionDao;
import org.example.dandd.model.dao.MonsterDao;
import org.example.dandd.model.dao.PgPlayableDao;
import org.example.dandd.model.entities.Action;
import org.example.dandd.model.entities.GameEntity;
import org.example.dandd.model.entities.Monster;
import org.example.dandd.model.entities.pg.PgPlayable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestCombat
{
	@Autowired
	PgPlayableDao pDao;
	@Autowired
	MonsterDao mDao;
	@Autowired
	ActionDao aDao;

	@Test
	void testCombat()
	{
		PgPlayable cloud = pDao.findByName("Cloud Constantine");
		Monster javlin= mDao.findByName("Javlin");
		Action sparo= aDao.findByNameAction("Sparo");
		Action graffio= aDao.findByNameAction("Graffio");

		int vitaCloud= cloud.getHp();
		int vitaJavlin= javlin.getHp();

		while(vitaCloud>0 && vitaJavlin>0)
		{
			if(sparo.hit(javlin.getDef(), cloud.getAtk()))
			{
				int danno=sparo.baseDmgCalculation(javlin.getDef(), cloud.getAtk());
				vitaJavlin-=danno;
				System.out.println("Vita rimasta del javlin "+(vitaJavlin>0?vitaJavlin:0));

				if(vitaJavlin<=0)
				{
					System.out.println("E' morto Javlin");
					break;
				}

			}

			if (graffio.hit(cloud.getDef(), javlin.getAtk()))
			{
				int danno=graffio.baseDmgCalculation(cloud.getDef(), javlin.getAtk());
				vitaCloud-=danno;
				System.out.println("Vita rimasta di Cloud "+(vitaCloud>0?vitaCloud:0));

					if (vitaCloud<=0)
				{
					System.out.println("E' morto Cloud");
					break;
				}
			}
		}
//		System.out.println("\nStato finale");
//		System.out.println("Attaccante: " + attaccante.getName() + " (HP: " + attaccante.getHp() + ", ATK: " + attaccante.getAtk() + ")");
//		System.out.println("Difensore: " + difensore.getName() + " (HP: " + difensore.getHp() + ", ATK: " + difensore.getAtk() + ")");
//		System.out.println("Azione usata: '" + attaccoBase.getNameAction() + "' (Tipo: " + attaccoBase.getActionType() + ")");
	}
}


