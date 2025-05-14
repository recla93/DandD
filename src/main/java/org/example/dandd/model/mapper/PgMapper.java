package org.example.dandd.model.mapper;

import org.example.dandd.model.dto.ActionDetailDto;
import org.example.dandd.model.dto.PgDto;
import org.example.dandd.model.entities.Action;
import org.example.dandd.model.entities.pg.PgPlayable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PgMapper {

	public PgDto toDto(PgPlayable pg) {
		if (pg == null) {
			return null;
		}

		List<ActionDetailDto> detailedActionsList;
		if (pg.getActions() != null) {
			detailedActionsList = pg.getActions().stream()
					.map(actionEntity -> new ActionDetailDto(
							actionEntity.getNameAction(),
							actionEntity.getDescriptionAction(),
							actionEntity.getActionType(),
							// Per currentCooldown: questo valore è dinamico e dovrebbe venire dallo stato di gioco.
							// Se l'entità Action non traccia il cooldown dinamico (cosa probabile),
							// inizializzalo a 0 qui. Il BattleService dovrà gestirlo.
							// Se actionEntity.getCooldown() è il cooldown MASSIMO, usalo per maxCooldown.
							0, // currentCooldown (placeholder, da gestire dinamicamente)
							actionEntity.getCooldown(),       // maxCooldown (il cooldown base dell'azione)
							actionEntity.getMaxNumTarget(),   // maxTargets
							actionEntity.isTargetsAllies(),   // targetsAllies (dal nuovo campo in Action.java)
							actionEntity.isTargetsSelf()      // targetsSelf (dal nuovo campo in Action.java)
					))
					.collect(Collectors.toList());
		} else {
			detailedActionsList = Collections.emptyList();
		}

		return new PgDto(
				pg.getId(),
				pg.getName(),
				pg.getDescription(),
				pg.getHp(),
				pg.getAtk(),
				pg.getDef(),
				pg.getSpeed(),
				detailedActionsList,
				pg.getEquipments() != null ? pg.getEquipments().stream().map(e -> e.getName()).collect(Collectors.toList()) : Collections.emptyList(),
				pg.getEquipments() != null ? pg.getEquipments().stream().map(e -> e.getDescription()).collect(Collectors.toList()) : Collections.emptyList(),
				pg.getCharacterType() != null ? pg.getCharacterType().name() : null,
				pg.getImageUrl()
		);
	}

	public List<PgDto> toDtos(List<PgPlayable> pgs) {
		if (pgs == null) {
			return Collections.emptyList();
		}
		return pgs.stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}
}
