package org.example.dandd.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dandd.model.entities.Action;
import org.example.dandd.model.entities.GameEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameStateDto
{
	private List<PgDto> good = new ArrayList<>();
	private List<MonsterDto> evil = new ArrayList<>();

	private List<Long> order= new ArrayList<>();

	private Long currentEntity;

}
