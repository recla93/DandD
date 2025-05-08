package org.example.dandd.service;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.dandd.model.dao.ActionDao;
import org.example.dandd.model.dao.GameEntityDao;
import org.example.dandd.model.dao.MonsterDao;
import org.example.dandd.model.dao.PgPlayableDao;
import org.example.dandd.model.dto.GameStateDto;
import org.example.dandd.model.dto.MonsterDto;
import org.example.dandd.model.dto.PgDto;
import org.example.dandd.model.entities.Action;
import org.example.dandd.model.entities.BaseEntity;
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

	public GameStateDto iniziaFight(List<PgPlayable> player, List<Monster> mostri)
	{
		GameStateDto dto = new GameStateDto();

		List<PgDto> pgDtos = pgMapper.toDtos(player);
		List<MonsterDto> monsterDtos = monsterMapper.toDtos(mostri);

		List<Long> order = Stream.concat(player.stream(), mostri.stream())
				.sorted(Comparator.comparingInt(GameEntity::getSpeed).reversed()) // dal più veloce
				.map(GameEntity::getId)
				.toList();


		dto.setGood(pgDtos);
		dto.setEvil(monsterDtos);
		dto.setOrder(order);
		if (!order.isEmpty())
			dto.setCurrentEntity(order.get(0));

		return dto;
	}

	/**
	 * Il metodo NEXTPG è la singola azione del Playeable verso il Mostro
	 */
//	public GameStateDto nextPg(PgPlayable attaccante, Monster target, Action action)
//	{
//		PgPlayable player = pdao.findById(attaccante.getId()).orElse(null);
//		Monster enemies = mdao.findById(target.getId()).orElse(null);
//		Action move = adao.findByNameAction(action.getNameAction());
//
//		int HpRimanenti = enemies.getHp() - move.dmgCalculator(player, enemies);
//
//	}

	public GameStateDto nextTurn(GameStateDto previousDto, int targetInList, ActionType action)
	{
		Long attackersId = previousDto.getCurrentEntity();

		if (attackersId == null)
		{
			System.out.println("ERRORE");
			return previousDto;
		}

		GameEntity attacker = gDao.findById(attackersId).orElse(null);
		if (attacker == null)
		{
			System.out.println("ATTACKER NULL");
			return previousDto;
		}

		GameEntity target = gDao.findById(previousDto.getOrder().get(targetInList)).orElse(null);
		if (target == null)
		{
			System.out.println("TARGET NULL");
			return previousDto;
		}

		Optional<Action> chosenAction = attacker.getActions()
				.stream()
				.filter(a -> a.getActionType().equals(action))
				.findFirst();

		if (chosenAction.isEmpty())
		{
			System.out.println("CHOSEN ACTION NULL");
			return previousDto;
		}

		Action chosen = chosenAction.get();


		if( (attacker instanceof PgPlayable && target instanceof PgPlayable) || (attacker instanceof Monster  && target instanceof Monster ) )
			System.out.println("Non puoi attaccare la stessa GameEntity");

		if( attacker instanceof PgPlayable player && target instanceof Monster monster )
		{
			int hpMonster = previousDto.getEvilTargetHp(target.getId())-chosen.dmgCalculator(player, monster);
			previousDto.substituteEvilTargetHp(target.getId(), hpMonster);
			System.out.println(attacker.getName() + "ha attaccato " + target.getName()+ "con " + chosen.getNameAction() + "\nVita del mostro rimasta " + hpMonster);
		}
		if(attacker instanceof Monster monster && target instanceof PgPlayable player )
		{
			int hpPlayer =previousDto.getGoodTargetHp(target.getId()) - chosen.dmgCalculator(monster,player );
			previousDto.substituteGoodTargetHp(target.getId(), hpPlayer);
			System.out.println("hpPlayer: " + hpPlayer);
		}

		if(target.getHp()<=0)
			previousDto.getOrder().remove(target.getId());



		return previousDto;

	}

	public GameStateDto nextRound(GameStateDto currentState)
	{
		if (currentState == null || currentState.getCurrentEntity() == null)
			return currentState;

		List<Long> order = currentState.getOrder();
		Long currentEntityId = currentState.getCurrentEntity();

		int currentEntityIndex = order.indexOf(currentEntityId);

		if (currentEntityIndex == -1)
		{
			System.out.println("Entity non trovata");
			currentState.setCurrentEntity(order.getFirst());
			return currentState;
		}

		int nextEntityIndex = (currentEntityIndex + 1) % order.size();
		Long nextEntityId = order.get(nextEntityIndex);

		currentState.setCurrentEntity(nextEntityId);

		System.out.println("Prossimo turno: " + nextEntityId);

		return currentState;
	}

	public GameStateDto applyAction(GameStateDto currentState, String actionName, Long targetId)
	{
		Long attackersId = currentState.getCurrentEntity();
		if (attackersId == null)
		{
			System.out.println("ERRORE");
			return currentState;
		}

		GameEntity attacker = gDao.findById(attackersId).orElseThrow(); //boh funge
		if (attacker == null)
		{
			System.out.println("ATTACKER NULL");
			return currentState;
		}

		GameEntity target = gDao.findById(targetId).orElseThrow();
		if (target == null)
		{
			System.out.println("TARGET NULL");
			return currentState;
		}

		Optional<Action> chosenAction = attacker.getActions()
				.stream()
				.filter(a -> a.getNameAction().equalsIgnoreCase(actionName))
				.findFirst();

		if (chosenAction.isEmpty())
		{
			System.out.println("CHOSEN ACTION NULL");
			return currentState;
		}

		Action chosen = chosenAction.get();

		System.out.println(attacker.getName() + " usa " + chosen.getNameAction() + " su " + target.getName());

		if (chosen.hit())
		{
			System.out.println("A SEGNO");
			int damageDealt = 0;

			if (attacker instanceof PgPlayable && target instanceof Monster)
			{
				damageDealt = chosen.dmgCalculator((PgPlayable) attacker, (Monster) target);
			}
			else if (attacker instanceof Monster && target instanceof PgPlayable)
			{
				damageDealt = chosen.dmgCalculator((Monster) attacker, (PgPlayable) target);
			}

			if (damageDealt > 0)
			{
				System.out.println("DANNO " + damageDealt);
				target.setHp(target.getHp() - damageDealt);
				System.out.println("Vita target " + target.getHp());
			}
			else
				System.out.println("DANNO 0");

			if (target.getHp() <= 0)
			{
				target.setHp(0);
				System.out.println(target.getName() + "È STATO SCONFITTO");
			}

			System.out.println(target.getName() + " ha " + target.getHp() + " vita");
		}
		else
			System.out.println("MANCATO");

		return currentState;
	}


}

