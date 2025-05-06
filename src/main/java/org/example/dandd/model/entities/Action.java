package org.example.dandd.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.dandd.model.entities.enums.ActionType;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action extends BaseEntity
{
	private String nameAction, descriptionAction;
	@Column(name = "accuracy")
	private int precision;
	private int molt, maxNumTarget, cooldown;
	private ActionType actionType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_game")
	private GameEntity game;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_gameState")
	private GameState gameState;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_battle")
	private Battle battle;

	private boolean hit(int def,int atk)
	{
		int dice = (int) (Math.random() * 100) + 1;

		return precision > dice;
	}

	public int baseDmgCalculation(int def, int atk)
	{
		int dmg = atk - def;
		if(dmg<0)
			return 0;

		return dmg;
	}
}
