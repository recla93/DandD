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
public /* abstract */ class GameEntity extends BaseEntity
{
	protected int hp, atk, def, speed;
	protected String name, description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_battle")
	private Battle battle;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "game")
	private List<Action> actions;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "owner", cascade = CascadeType.ALL)
	private List<Equipment> equipments;
}
