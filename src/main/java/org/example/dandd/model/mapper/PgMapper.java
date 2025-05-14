package org.example.dandd.model.mapper;

import org.example.dandd.model.dto.PgDto;
import org.example.dandd.model.entities.pg.PgPlayable; // Assicurati che PgPlayable abbia getImageUrl()
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PgMapper
{


	public PgDto toDto(PgPlayable pg)
	{
		return new PgDto(
				pg.getId(),
				pg.getName(),
				pg.getDescription(),
				pg.getHp(),
				pg.getAtk(),
				pg.getDef(),
				pg.getSpeed(),
				pg.getActions().stream().map(a -> a.getNameAction()).toList(),
				pg.getActions().stream().map(a -> a.getDescriptionAction()).toList(),
				pg.getActions().stream().map(a->a.getActionType()).toList(),
				pg.getEquipments().stream().map(e -> e.getName()).toList(),
				pg.getEquipments().stream().map(e -> e.getDescription()).toList(),
				pg.getCharacterType().name(),
				pg.getImageUrl()
		);
	}

	public List<PgDto> toDtos(List<PgPlayable> pgs)
	{
		return pgs.stream().map(this::toDto).collect(Collectors.toList()); // Usa riferimento al metodo
	}

}

