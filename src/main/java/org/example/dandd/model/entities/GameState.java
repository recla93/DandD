package org.example.dandd.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameState
{
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "gameState")
	private List<GameEntity> gameCharacters = new ArrayList<>();

	@OneToMany(fetch = FetchType.EAGER,mappedBy = "gameState")
	private List<Action> actions = new ArrayList<>();

}
