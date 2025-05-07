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
	/**
	 * Hp min=80, max=200;
	 * atk min=10, max=50;
	 * def min=10, max=20;
	 * speed min=10, max=50
	 */
	protected int hp, atk, def, speed;
	protected String name, description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_battle")
	private Battle battle;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "entity")
	private List<Action> actions;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_gameState")
	private GameState gameState;
}
