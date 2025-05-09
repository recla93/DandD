package org.example.dandd.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
	 * */
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
	 *Il metodo nextTurn prende in entrata il GameState precedente, il targer e il tipo di Azione.
	 * Gestisce tutta la logica dell'singolo turno.
	 */
	public GameStateDto nextTurn(GameStateDto previousDto, int targetInList, ActionType action)
	{

		Long attackersId = previousDto.getCurrentEntity();

		GameEntity attacker = gDao.findById(attackersId).orElse(null);
		GameEntity target = gDao.findById(previousDto.getOrder().get(targetInList)).orElse(null);
		Optional<Action> chosenAction = attacker.getActions()
				.stream()
				.filter(a -> a.getActionType().equals(action))
				.findFirst();

		Action chosen = chosenAction.get();

		if( attacker instanceof PgPlayable player && target instanceof Monster monster )
		{
			int hpMonster = previousDto.getEvilTargetHp(target.getId())-chosen.dmgCalculator(player, monster);
			previousDto.substituteEvilTargetHp(target.getId(), hpMonster);
			if(hpMonster<=0)
			{
				System.out.println("È morto il mostro "+ monster.getId());
				previousDto.getOrder().remove(monster.getId());
			}
		}
		if(attacker instanceof Monster monster && target instanceof PgPlayable player )
		{
			int hpPlayer =previousDto.getGoodTargetHp(target.getId()) - chosen.dmgCalculator(monster,player );
			previousDto.substituteGoodTargetHp(target.getId(), hpPlayer);
			if(hpPlayer<=0)
			{
				System.out.println("È morto il personaggio "+ player.getId());
				previousDto.getOrder().remove(player.getId());
			}
		}
		System.out.println(previousDto.getOrder());
		return previousDto;

	}

	/**
	 *Il metodo nextRound prende in entrata il GameState precedente e manda avanti la Lista di Entity di 1
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

//	public GameStateDto nextRound2(GameStateDto currentState)
//	{
//		if (battleOver(currentState).equals("Hai vinto") || battleOver(currentState).equals("Hai perso"))
//		{
//
//
//		}
//
//		List<Long> order = currentState.getOrder();
//		Long currentEntityId = currentState.getCurrentEntity();
//
//		int currentEntityIndex = order.indexOf(currentEntityId);
//
//		int nextEntityIndex = (currentEntityIndex + 1) % order.size();
//		Long nextEntityId = order.get(nextEntityIndex);
//
//		currentState.setCurrentEntity(nextEntityId);
//
//		return currentState;
//	}

}

