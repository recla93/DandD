package org.example.dandd;

import org.example.dandd.model.dao.ActionDao;
import org.example.dandd.model.dao.EquipmentDao;
import org.example.dandd.model.dao.MonsterDao;
import org.example.dandd.model.dao.PgPlayableDao;
import org.example.dandd.model.entities.Action;
import org.example.dandd.model.entities.Equipment;
import org.example.dandd.model.entities.Monster;
import org.example.dandd.model.entities.enums.ActionType;
import org.example.dandd.model.entities.enums.CharacterType;
import org.example.dandd.model.entities.enums.EquipmentType;
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
		@Autowired
		MonsterDao mDao;
		@Autowired
		ActionDao aDao;

		@Test
		public void testDb()
		{
			PgPlayable cla = new PgPlayable();
			cla.setCharacterType(CharacterType.TROUBLESHOOTER);
			cla.setName("Cloud Constantine");
			cla.setDescription("Un cecchino infallibile.");
			cla.setHp(80);
			cla.setAtk(30);
			cla.setDef(15);
			cla.setSpeed(10);
			pDao.save(cla);

			Equipment fucile  = new Equipment();
			fucile.setEquipmentType(EquipmentType.WEAPON);
			fucile.setName("4NGUL4R");
			fucile.setDescription("Il grilletto a sterminare le orde di bug.");
			fucile.setPlayable(cla);
			fucile.setPlusDmg(15);
			eDao.save(fucile);

			Action sparoBase = new Action();
			sparoBase.setNameAction("Sparo");
			sparoBase.setDescriptionAction("Attacco base del TroubleShooter, infligge danni pari alla sua statistica Atk.");
			sparoBase.setMaxNumTarget(1);
			sparoBase.setActionType(ActionType.BASE);
			sparoBase.setPrecision(100);
			sparoBase.setEntity(cla);
			aDao.save(sparoBase);

			Monster javlin  = new Monster();
			javlin.setName("Javlin");
			javlin.setDescription("Un essere infimo e schifoso fatto di codici mai finiti ed errori mai letti carattere per carattere.");
			javlin.setHp(80);
			javlin.setAtk(20);
			javlin.setDef(5);
			javlin.setSpeed(5);
			mDao.save(javlin);

			Action graffioBase = new Action();
			graffioBase.setNameAction("Graffio");
			graffioBase.setDescriptionAction("pollo");
			graffioBase.setMaxNumTarget(1);
			graffioBase.setActionType(ActionType.BASE);
			graffioBase.setPrecision(100);
			graffioBase.setEntity(mDao.findByName("Javlin"));
			aDao.save(graffioBase);

			PgPlayable V = new PgPlayable();
			V.setCharacterType(CharacterType.CODETHIEF);
			V.setName("V Alphamale");
			V.setDescription("Un'ombra nel codice.");
			V.setHp(120);
			V.setAtk(25);
			V.setDef(15);
			V.setSpeed(40);
			pDao.save(V);

			Equipment pugnale1  = new Equipment();
			pugnale1.setEquipmentType(EquipmentType.WEAPON);
			pugnale1.setName("CTRL-C");
			pugnale1.setDescription("Copia l'anima del bug");
			pugnale1.setPlayable(V);
			pugnale1.setPlusDmg(25);
			eDao.save(pugnale1);
			
			Equipment pugnale2  = new Equipment();
			pugnale2.setEquipmentType(EquipmentType.WEAPON);
			pugnale2.setName("CTRL-V");
			pugnale2.setDescription("LA INCOLLA ALL'INFERNO");
			pugnale2.setPlayable(V);
			pugnale2.setPlusDmg(25);
			eDao.save(pugnale2);

			Action stab = new Action();
			stab.setNameAction("Stab");
			stab.setDescriptionAction("Garibaldi");
			stab.setMaxNumTarget(1);
			stab.setActionType(ActionType.BASE);
			stab.setPrecision(100);
			stab.setEntity(V);
			aDao.save(stab);

			/// Prossimo Fra

			PgPlayable fra = new PgPlayable();
			fra.setCharacterType(CharacterType.INFRASTRUCTURE);
			fra.setName("Heimdall");
			fra.setDescription("Il guardiano dei Vendicatori.");
			fra.setHp(160);
			fra.setAtk(15);
			fra.setDef(20);
			fra.setSpeed(10);
			pDao.save(fra);

			Equipment shield  = new Equipment();
			shield.setEquipmentType(EquipmentType.SHIELD);
			shield.setName("EGIDA DI STEFANO");
			shield.setDescription("Lo scudo contro cui si infrange l'Exception");
			shield.setPlayable(fra);
			shield.setPlusDef(5);
			eDao.save(shield);

			Action punch = new Action();
			punch.setNameAction("Pugno");
			punch.setDescriptionAction("Ti do un pugno sulle sopracciglia");
			punch.setMaxNumTarget(1);
			punch.setActionType(ActionType.BASE);
			punch.setPrecision(100);
			punch.setEntity(fra);
			aDao.save(punch);

			//Prossimo Edo

			PgPlayable edo = new PgPlayable();
			edo.setCharacterType(CharacterType.DATAMYSTIC);
			edo.setName("Skibidi Sanni (ajo)");
			edo.setDescription("Una misteriosa figura incappucciata, il volto mai visibile.");
			edo.setHp(80);
			edo.setAtk(30);
			edo.setDef(12);
			edo.setSpeed(25);
			pDao.save(edo);

			Equipment sfera  = new Equipment();
			sfera.setEquipmentType(EquipmentType.WEAPON);
			sfera.setName("SFERA DI DATI");
			sfera.setDescription("Amplifica il potere dei dati");
			sfera.setPlayable(edo);
			sfera.setPlusDmg(10);
			eDao.save(sfera);

			Action dataBolt = new Action();
			dataBolt.setNameAction("Data Bolt");
			dataBolt.setDescriptionAction("Un proiettile di dati energetici");
			dataBolt.setMaxNumTarget(1);
			dataBolt.setActionType(ActionType.BASE);
			dataBolt.setPrecision(100);
			dataBolt.setEntity(edo);
			aDao.save(dataBolt);

			//Prossimo Veronico

			PgPlayable vero = new PgPlayable();
			vero.setCharacterType(CharacterType.GITBARD);
			vero.setName("Debug Her");
			vero.setDescription("Mai visto dei bug corrotti ballare distrattamente mentre vengono decimati?.");
			vero.setHp(130);
			vero.setAtk(15);
			vero.setDef(15);
			vero.setSpeed(20);
			pDao.save(vero);

			Equipment flauto  = new Equipment();
			flauto.setEquipmentType(EquipmentType.WEAPON);
			flauto.setName("ANNOTATION");
			flauto.setDescription("Ammalia e ordina i bug con le sue note");
			flauto.setPlayable(vero);
			flauto.setPlusDmg(10);
			eDao.save(flauto);

			Action nota = new Action();
			nota.setNameAction("Do minore");
			nota.setDescriptionAction("Un do che è com a nu paccr");
			nota.setMaxNumTarget(1);
			nota.setActionType(ActionType.BASE);
			nota.setPrecision(100);
			nota.setEntity(vero);
			aDao.save(nota);

			//Prossimo Sandro

			PgPlayable sandro = new PgPlayable();
			sandro.setCharacterType(CharacterType.CODECLEANER);
			sandro.setName("Sandor Monster");
			sandro.setDescription("Il leggendario Decimatore di Bug.");
			sandro.setHp(120);
			sandro.setAtk(25);
			sandro.setDef(17);
			sandro.setSpeed(20);
			pDao.save(sandro);

			Equipment spadone  = new Equipment();
			spadone.setEquipmentType(EquipmentType.WEAPON);
			spadone.setName("REFACTOR");
			spadone.setDescription("Distrugge tutto pure il codice del suo utilizzatore perchè non sa farlo bene.");
			spadone.setPlayable(sandro);
			spadone.setPlusDmg(10);
			eDao.save(spadone);

			Action fendente = new Action();
			fendente.setNameAction("White Slash");
			fendente.setDescriptionAction("Mena na schecchera assurda e intanto apre pure una monster bianca");
			fendente.setMaxNumTarget(1);
			fendente.setActionType(ActionType.BASE);
			fendente.setPrecision(100);
			fendente.setEntity(sandro);
			aDao.save(fendente);

			
			
		}



}
