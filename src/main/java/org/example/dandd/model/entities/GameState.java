package org.example.dandd.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameState extends BaseEntity
{

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "gameState")
	private List<GameEntity> gameCharacters = new ArrayList<>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "gameState")
	private List<Action> actions = new ArrayList<>();
}
