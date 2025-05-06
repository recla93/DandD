package org.example.dandd.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public class GameEntity extends BaseEntity
{
	protected int hp, atk, def, speed;
	protected String name, description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_battle")
	private Battle battle;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "game")
	private List<Action> actions;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_gameState")
	private GameState gameState;
}
