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
	@JoinColumn(name = "id_action")
	private GameEntity game;

	public int dmgCalculation(int def,int atk)
	{
		int dmg = atk - def;
		if(dmg<0)
			return 0;

		return dmg;
	}
}
