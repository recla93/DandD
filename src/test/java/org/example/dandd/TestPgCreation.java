package org.example.dandd;

import org.example.dandd.model.dao.EquipmentDao;
import org.example.dandd.model.dao.PgPlayableDao;
import org.example.dandd.model.entities.Equipment;
import org.example.dandd.model.entities.enums.CharacterType;
import org.example.dandd.model.entities.pg.PgPlayable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestPgCreation
{
		@Autowired
		PgPlayableDao pDao;
		@Autowired
		EquipmentDao eDao;

		@Test
		public void testDb()
		{
			PgPlayable cloud = new PgPlayable();
			cloud.setCharacterType(CharacterType.TROUBLESHOOTER);
			cloud.setName("Cloud Constantine");
			cloud.setDescription("Un cecchino infallibile," +
					" armato del suo fido fucile 4NGUL4R," +
					" è pronto a sterminare le orde di bug." +
					" Vive per il brivido di premere il grilletto," +
					" e ha già un proiettile con un nome inciso: Bug Abissale. Un colpo, un morto.");
			cloud.setHp(80);
			cloud.setAtk(30);
			cloud.setDef(15);
			cloud.setSpeed(10);
			pDao.save(cloud);

			Equipment fucile  = new Equipment();
			fucile.setName("4NGUL4R");
			fucile.setDescription("Il grilletto a sterminare le orde di bug.");
			fucile.setPlayable(cloud);
			eDao.save(fucile);

			Equipment pistole  = new Equipment();
			pistole.setName("SICULA");
			pistole.setDescription("Il grilletto a sterminare calabresi.");
			pistole.setPlayable(cloud);
			eDao.save(pistole);

			List<Equipment> armiCloud= new ArrayList<>();
			armiCloud.add(fucile);
			armiCloud.add(pistole);
			cloud.setEquipments(armiCloud);
			pDao.save(cloud);
		}



}
