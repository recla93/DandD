package org.example.dandd.model.dto;

import lombok.Data;
import org.example.dandd.model.entities.enums.ActionType;

import java.util.List;


public record PgDto
		(
				Long id,
				String name,
				String description,
				int hp,
				int atk,
				int def,
				int spd,
				List<String> actionsName,
				List<String> actionsDescriptions,
				List<String> actionTypes,
				List<String> equipmentsName,
				List<String> equipmentsDescriptions,
				String enumType,
				String imageUrl
		)
{
	public PgDto nuoviHp(int nuoviHp)
	{
		return new PgDto(
				this.id,
				this.name,
				this.description,
				nuoviHp,
				this.atk,
				this.def,
				this.spd,
				this.actionsName,
				this.actionsDescriptions,
				this.actionTypes,
				this.equipmentsName,
				this.equipmentsDescriptions,
				this.enumType,
				this.imageUrl
		);
	}

}

