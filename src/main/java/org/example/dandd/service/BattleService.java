package org.example.dandd.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.example.dandd.model.dao.ActionDao;
import org.example.dandd.model.dao.GameEntityDao;
import org.example.dandd.model.dao.MonsterDao;
import org.example.dandd.model.dao.PgPlayableDao;
import org.example.dandd.model.dto.GameStateDto;
import org.example.dandd.model.dto.MonsterDto;
import org.example.dandd.model.dto.PgDto;
import org.example.dandd.model.entities.Action;
import org.example.dandd.model.entities.GameEntity;
import org.example.dandd.model.entities.Monster;
import org.example.dandd.model.entities.enums.ActionType;
import org.example.dandd.model.entities.enums.CharacterType;
import org.example.dandd.model.entities.pg.PgPlayable;
import org.example.dandd.model.mapper.MonsterMapper;
import org.example.dandd.model.mapper.PgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class BattleService
{
	@Autowired
	PgPlayableDao pdao;
	@Autowired
	PgMapper pgMapper;

	@Autowired
	MonsterDao mdao;
	@Autowired
	MonsterMapper monsterMapper;

	@Autowired
	ActionDao adao;

	@Autowired
	GameEntityDao gDao;

	/**
	 * Il metodo iniziaFight prende in entrata le 2 liste di player e di mostri scelti
	 * e li unisce in una singola lista per poi restituirla ordinata in base alla statistica spd
	 */
	public GameStateDto iniziaFight(List<PgPlayable> player, List<Monster> mostri)
	{
		GameStateDto dto = new GameStateDto();

		List<PgDto> pgDtos = pgMapper.toDtos(player);
		List<MonsterDto> monsterDtos = monsterMapper.toDtos(mostri);

		List<Long> order = Stream.concat(player.stream(), mostri.stream())
				.sorted(Comparator.comparingInt(GameEntity::getSpeed).reversed()) // dal più veloce
				.map(GameEntity::getId)
				.collect(Collectors.toList());


		dto.setGood(pgDtos);
		dto.setEvil(monsterDtos);
		dto.setOrder(order);
		if (!order.isEmpty())
			dto.setCurrentEntity(order.get(0));

		return dto;
	}

	/**
	 * Gestisce la logica di un singolo turno di combattimento.
	 * <p>
	 * Il metodo prende in ingresso lo stato di gioco precedente, la posizione del bersaglio
	 * nella lista d'ordine e il tipo di azione da compiere.
	 * In base al tipo di entità (giocatore o mostro) e all'azione selezionata, calcola l'effetto dell'azione
	 * (danno o cura) e aggiorna i punti vita del bersaglio.
	 * <p>
	 * Se l'azione è effettuata da un {@code PgPlayable} di tipo {@code GITBARD} contro un altro
	 * {@code PgPlayable}, e l'azione è di tipo {@code SPECIALE}, l'effetto dell'azione sarà la cura.
	 * In questo caso, il danno calcolato dall'azione deve essere un valore negativo, che sarà invertito per aumentare gli HP.
	 * <p>
	 * Se i punti vita del bersaglio scendono a zero, viene rimosso dall'ordine del turno.
	 *
	 * @param previousDto  Lo stato del gioco prima dell'esecuzione del turno, contenente le entità,
	 *                     i loro HP e l'ordine di attivazione.
	 * @param targetInList L'indice del bersaglio nella lista d'ordine.
	 * @param actionType   Il tipo di azione da eseguire: BASE, HEAVY o SPECIALE.
	 * @return Un oggetto {@link GameStateDto} aggiornato con gli effetti dell'azione applicata.
	 * @throws IllegalStateException se non vengono trovati l'attaccante, il bersaglio o l'azione.
	 */
	public GameStateDto nextTurn(GameStateDto previousDto, List<Long> targetInList, ActionType actionType)
	{
		/// Ottieni l'id dell'attaccante
		Long attackerId = previousDto.getCurrentEntity();


		///  Restituisce le entità effettive e controlla se esistono
		GameEntity attacker = gDao.findById(attackerId).orElseThrow(() -> new IllegalStateException("Attaccante non trovato"));
		List<GameEntity> targetList = gDao.findAllById(targetInList);

//		for (Long target : targetInList)
//			targetList.add(gDao.findById(targetInList).orElseThrow(() -> new IllegalStateException("Target non trovato")));

		/// Cerca l'azione in base al tipo
		Action action = attacker.getActions().stream()
				.filter(a -> a.getActionType().equals(actionType))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Azione non trovata"));

		/// CAPISCE SE IL PG PUÒ PRENDERE O NO PIÙ TARGET
		if (actionType.equals(ActionType.SPECIALE))
		{
			for (GameEntity g : targetList)
			{
				if (targetList.size()>3)
				{
					System.out.println("Troppi pg selezionati");
				}

				if (attacker.getId().equals(1L))
				{
					if (targetList.size()>2)
					{
						System.out.println("Troppi pg selezionati per Claudio");
					}

					else if (attacker instanceof PgPlayable player && g instanceof Monster monster)
					{
						int damage = action.dmgCalculator(player, monster);
						int hpMonster = previousDto.getEvilTargetHp(g.getId()) - damage;

						if (hpMonster < 0)
							hpMonster = 0;

						previousDto.substituteEvilTargetHp(g.getId(), hpMonster);

						if (hpMonster == 0)
							previousDto.getOrder().remove(monster.getId());

						System.out.println("Attaccante: " + attacker.getName() +
								" | Danni: " + damage +
								" | Nemico: " + g.getName() +
								" | HP rimasti: " + hpMonster);
					}
					else if (attacker instanceof Monster monster && g instanceof PgPlayable player)
					{
						int damage = action.dmgCalculator(monster, player);
						int hpPlayer = previousDto.getGoodTargetHp(g.getId()) - damage;

						if (hpPlayer < 0)
							hpPlayer = 0;

						previousDto.substituteGoodTargetHp(g.getId(), hpPlayer);

						if (hpPlayer == 0)
							previousDto.getOrder().remove(player.getId());

						System.out.println("Attaccante: " + attacker.getName() +
								" | Danni: " + damage +
								" | Nemico: " + g.getName() +
								" | HP rimasti: " + hpPlayer);
					}
					else if (attacker instanceof PgPlayable player && g instanceof PgPlayable targetPlayer)
					{
						if (action.getActionType() == ActionType.SPECIALE && player.getCharacterType().equals(CharacterType.GITBARD))
						{
							int healing = -action.dmgCalculator(attacker, g); // negativo → cura
							int oldHp = previousDto.getGoodTargetHp(g.getId());
							int healedHp = oldHp + healing;


							previousDto.substituteGoodTargetHp(g.getId(), healedHp);

							System.out.println("Curatore: " + player.getName() +
									" | Cura: " + healing +
									" | Alleato: " + targetPlayer.getName() +
									" | HP dopo la cura: " + healedHp);
						}
					}
					else
					{
						throw new IllegalStateException("Non si possono attaccare entity dello stesso tipo");
					}
				}

				else if (attacker.getId().equals(4L))
				{
					if (attacker instanceof PgPlayable player && g instanceof Monster monster)
					{
						int damage = action.dmgCalculator(player, monster);
						int hpMonster = previousDto.getEvilTargetHp(g.getId()) - damage;

						if (hpMonster < 0)
							hpMonster = 0;

						previousDto.substituteEvilTargetHp(g.getId(), hpMonster);

						if (hpMonster == 0)
							previousDto.getOrder().remove(monster.getId());

						System.out.println("Attaccante: " + attacker.getName() +
								" | Danni: " + damage +
								" | Nemico: " + g.getName() +
								" | HP rimasti: " + hpMonster);
					}
					else if (attacker instanceof Monster monster && g instanceof PgPlayable player)
					{
						int damage = action.dmgCalculator(monster, player);
						int hpPlayer = previousDto.getGoodTargetHp(g.getId()) - damage;

						if (hpPlayer < 0)
							hpPlayer = 0;

						previousDto.substituteGoodTargetHp(g.getId(), hpPlayer);

						if (hpPlayer == 0)
							previousDto.getOrder().remove(player.getId());

						System.out.println("Attaccante: " + attacker.getName() +
								" | Danni: " + damage +
								" | Nemico: " + g.getName() +
								" | HP rimasti: " + hpPlayer);
					}
					else if (attacker instanceof PgPlayable player && g instanceof PgPlayable targetPlayer)
					{
						if (action.getActionType() == ActionType.SPECIALE && player.getCharacterType().equals(CharacterType.GITBARD))
						{
							int healing = -action.dmgCalculator(attacker, g); // negativo → cura
							int oldHp = previousDto.getGoodTargetHp(g.getId());
							int healedHp = oldHp + healing;


							previousDto.substituteGoodTargetHp(g.getId(), healedHp);

							System.out.println("Curatore: " + player.getName() +
									" | Cura: " + healing +
									" | Alleato: " + targetPlayer.getName() +
									" | HP dopo la cura: " + healedHp);
						}
					}
					else
					{
						throw new IllegalStateException("Non si possono attaccare entity dello stesso tipo");
					}
				}

				else if (attacker.getId().equals(2L) || attacker.getId().equals(3L) || attacker.getId().equals(5L) || attacker.getId().equals(6L))
				{
					if (targetList.size()>1)
					{
						System.out.println("Troppi pg selezionati per questo personaggio");
					}

					else if (attacker instanceof PgPlayable player && g instanceof Monster monster)
					{
						int damage = action.dmgCalculator(player, monster);
						int hpMonster = previousDto.getEvilTargetHp(g.getId()) - damage;

						if (hpMonster < 0)
							hpMonster = 0;

						previousDto.substituteEvilTargetHp(g.getId(), hpMonster);

						if (hpMonster == 0)
							previousDto.getOrder().remove(monster.getId());

						System.out.println("Attaccante: " + attacker.getName() +
								" | Danni: " + damage +
								" | Nemico: " + g.getName() +
								" | HP rimasti: " + hpMonster);
					}
					else if (attacker instanceof Monster monster && g instanceof PgPlayable player)
					{
						int damage = action.dmgCalculator(monster, player);
						int hpPlayer = previousDto.getGoodTargetHp(g.getId()) - damage;

						if (hpPlayer < 0)
							hpPlayer = 0;

						previousDto.substituteGoodTargetHp(g.getId(), hpPlayer);

						if (hpPlayer == 0)
							previousDto.getOrder().remove(player.getId());

						System.out.println("Attaccante: " + attacker.getName() +
								" | Danni: " + damage +
								" | Nemico: " + g.getName() +
								" | HP rimasti: " + hpPlayer);
					}
					else if (attacker instanceof PgPlayable player && g instanceof PgPlayable targetPlayer)
					{
						if (action.getActionType() == ActionType.SPECIALE && player.getCharacterType().equals(CharacterType.GITBARD))
						{
							int healing = -action.dmgCalculator(attacker, g); // negativo → cura
							int oldHp = previousDto.getGoodTargetHp(g.getId());
							int healedHp = oldHp + healing;


							previousDto.substituteGoodTargetHp(g.getId(), healedHp);

							System.out.println("Curatore: " + player.getName() +
									" | Cura: " + healing +
									" | Alleato: " + targetPlayer.getName() +
									" | HP dopo la cura: " + healedHp);
						}
					}
					else
					{
						throw new IllegalStateException("Non si possono attaccare entity dello stesso tipo");
					}
				}
			}



			return previousDto;
		}



		///  Applica danno in base al tipo
		Long targetId= targetInList.stream().filter(t-> t!=null).findFirst().orElse(null);
		GameEntity target= gDao.findById(targetId).orElseThrow(() -> new IllegalStateException("Target non trovato"));
		if (attacker instanceof PgPlayable player && target instanceof Monster monster)
		{
			int hpMonster = previousDto.getEvilTargetHp(target.getId()) - action.dmgCalculator(player, monster);

			if (hpMonster < 0)
				hpMonster = 0;

			previousDto.substituteEvilTargetHp(target.getId(), hpMonster);

			if (hpMonster == 0)
			{
				previousDto.getOrder().remove(monster.getId());
			}


			System.out.println("Attaccante: " + attacker.getName() +
					"Danni: " + action.dmgCalculator(player, monster) +
					"Nemico: " + target.getName() +
					"HP rimasti:  " + hpMonster);
		}
		else if (attacker instanceof Monster monster && target instanceof PgPlayable player)
		{
			int hpPlayer = previousDto.getGoodTargetHp(target.getId()) - action.dmgCalculator(monster, player);

			if (hpPlayer < 0)
				hpPlayer = 0;

			previousDto.substituteGoodTargetHp(target.getId(), hpPlayer);

			if (hpPlayer == 0)
				previousDto.getOrder().remove(player.getId());

			System.out.println("Attaccante: " + attacker.getName() +
					"Danni: " + action.dmgCalculator(monster, player) +
					"Nemico: " + target.getName() +
					"HP rimasti:  " + hpPlayer);
		}
		else if (attacker instanceof PgPlayable player && target instanceof PgPlayable targetPlayer)
		{
			if (action.getActionType() == ActionType.SPECIALE && player.getCharacterType().equals(CharacterType.GITBARD))
			{
				int oldHp = previousDto.getGoodTargetHp(target.getId());
				int healedHp = oldHp - action.dmgCalculator(attacker, target); // dmg negativo → cura
				if (healedHp > 100) healedHp = 100; // oppure targetPlayer.getMaxHp() se disponibile
				previousDto.substituteGoodTargetHp(target.getId(), healedHp);

				System.out.println("Curatore: " + player.getName() +
						" | Cura: " + (-action.dmgCalculator(attacker, target)) +
						" | Alleato: " + targetPlayer.getName() +
						" | HP dopo la cura: " + healedHp);
			}
		}
		else
		{
			throw new IllegalStateException("Non si possono attaccare entity dello stesso tipo");
		}

		return previousDto;
	}

	/**
	 * Il metodo nextRound prende in entrata il GameState precedente e manda avanti la Lista di Entity di 1
	 */
	public GameStateDto nextRound(GameStateDto currentState)
	{
		battleOver(currentState);

		List<Long> order = currentState.getOrder();
		Long currentEntityId = currentState.getCurrentEntity();

		int currentEntityIndex = order.indexOf(currentEntityId);

		int nextEntityIndex = (currentEntityIndex + 1) % order.size();
		Long nextEntityId = order.get(nextEntityIndex);

		currentState.setCurrentEntity(nextEntityId);

		return currentState;
	}

	public String battleOver(GameStateDto currentState)
	{
		List<Long> order = currentState.getOrder();

		boolean noMonster = order.stream()
				.map(gDao::findById)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.noneMatch(e -> e instanceof Monster);

		boolean playerAlive = order.stream()
				.map(gDao::findById)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.anyMatch(e -> e instanceof PgPlayable);

		if (noMonster && playerAlive)
			return "Hai vinto";
		else if (!noMonster && !playerAlive)
			return "Hai perso";
		else
			return "La battaglia è ancora in corso";
	}




//	public GameStateDto nextTurn(GameStateDto previousDto, List<Long> targetInList, ActionType actionType)
//	{
//		/// Ottieni l'id dell'attaccante
//		Long attackerId = previousDto.getCurrentEntity();
//
//
//		///  Restituisce le entità effettive e controlla se esistono
//		GameEntity attacker = gDao.findById(attackerId).orElseThrow(() -> new IllegalStateException("Attaccante non trovato"));
//		List<GameEntity> targetList = gDao.findAllById(targetInList);
//
////		for (Long target : targetInList)
////			targetList.add(gDao.findById(targetInList).orElseThrow(() -> new IllegalStateException("Target non trovato")));
//
//		/// Cerca l'azione in base al tipo
//		Action action = attacker.getActions().stream()
//				.filter(a -> a.getActionType().equals(actionType))
//				.findFirst()
//				.orElseThrow(() -> new IllegalStateException("Azione non trovata"));
//
//		if (actionType.equals(ActionType.SPECIALE))
//		{
//			for (GameEntity g : targetList)
//			{
//				if (attacker instanceof PgPlayable player && g instanceof Monster monster)
//				{
//					int damage = action.dmgCalculator(player, monster);
//					int hpMonster = previousDto.getEvilTargetHp(g.getId()) - damage;
//
//					if (hpMonster < 0)
//						hpMonster = 0;
//
//					previousDto.substituteEvilTargetHp(g.getId(), hpMonster);
//
//					if (hpMonster == 0)
//						previousDto.getOrder().remove(monster.getId());
//
//					System.out.println("Attaccante: " + attacker.getName() +
//							" | Danni: " + damage +
//							" | Nemico: " + g.getName() +
//							" | HP rimasti: " + hpMonster);
//				}
//				else if (attacker instanceof Monster monster && g instanceof PgPlayable player)
//				{
//					int damage = action.dmgCalculator(monster, player);
//					int hpPlayer = previousDto.getGoodTargetHp(g.getId()) - damage;
//
//					if (hpPlayer < 0)
//						hpPlayer = 0;
//
//					previousDto.substituteGoodTargetHp(g.getId(), hpPlayer);
//
//					if (hpPlayer == 0)
//						previousDto.getOrder().remove(player.getId());
//
//					System.out.println("Attaccante: " + attacker.getName() +
//							" | Danni: " + damage +
//							" | Nemico: " + g.getName() +
//							" | HP rimasti: " + hpPlayer);
//				}
//				else if (attacker instanceof PgPlayable player && g instanceof PgPlayable targetPlayer)
//				{
//					if (action.getActionType() == ActionType.SPECIALE && player.getCharacterType().equals(CharacterType.GITBARD))
//					{
//						int healing = -action.dmgCalculator(attacker, g); // negativo → cura
//						int oldHp = previousDto.getGoodTargetHp(g.getId());
//						int healedHp = oldHp + healing;
//
//
//						previousDto.substituteGoodTargetHp(g.getId(), healedHp);
//
//						System.out.println("Curatore: " + player.getName() +
//								" | Cura: " + healing +
//								" | Alleato: " + targetPlayer.getName() +
//								" | HP dopo la cura: " + healedHp);
//					}
//				}
//				else
//				{
//					throw new IllegalStateException("Non si possono attaccare entity dello stesso tipo");
//				}
//			}
//
//			return previousDto;
//		}
}

