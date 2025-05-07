package org.example.dandd.model.dto;

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
				List<String> equipmentsName,
				String enumType
		)
{
}
