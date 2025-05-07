package org.example.dandd.model.dto;

import java.util.List;

public record MonsterDto
		(
				Long id,
				String name,
				String description,
				String Danger,
				int hp,
				int atk,
				int def,
				int spd,
				List<String> actionsName
		)
{
}
