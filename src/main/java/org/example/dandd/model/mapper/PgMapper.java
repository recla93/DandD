package org.example.dandd.model.mapper;

import org.example.dandd.model.dto.PgDto;
import org.example.dandd.model.entities.pg.PgPlayable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PgMapper
{

	public PgDto toDto(PgPlayable pg)
	{
		return new PgDto(pg.getId(),pg.getName(), pg.getDescription(), pg.getHp(), pg.getAtk(), pg.getDef(),pg.getSpeed(),pg.getActions().stream().map(a->a.getNameAction()).toList(),pg.getEquipments().stream().map(e->e.getName()).toList(),pg.getCharacterType().name());
	}

	public List<PgDto> toDtos(List<PgPlayable> pgs)
	{
		return pgs.stream().map(p->toDto(p)).collect(Collectors.toList());
	}

}
