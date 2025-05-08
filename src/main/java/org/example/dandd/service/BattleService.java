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
import org.example.dandd.model.dao.MonsterDao;
import org.example.dandd.model.dao.PgPlayableDao;
import org.example.dandd.model.dto.GameStateDto;
import org.example.dandd.model.dto.MonsterDto;
import org.example.dandd.model.dto.PgDto;
import org.example.dandd.model.entities.Action;
import org.example.dandd.model.entities.BaseEntity;
import org.example.dandd.model.entities.GameEntity;
import org.example.dandd.model.entities.Monster;
import org.example.dandd.model.entities.pg.PgPlayable;
import org.example.dandd.model.mapper.MonsterMapper;
import org.example.dandd.model.mapper.PgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
	public GameStateDto nextPg(PgPlayable attaccante, List<Monster> mostri, Action action)
	{
		PgPlayable player = pdao.findById(attaccante.getId()).orElse(null);
		 = pdao.findById(attaccante.getId()).orElse(null);
	}
}

