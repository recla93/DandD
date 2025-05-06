package org.example.dandd.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	private int precision, molt, maxNumTarget, cooldown;
	private ActionType actionType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_action")
	private GameEntity game;

	private int dmgCalculation(int def,int atk)
	{
		int dmg = atk - def;
		if(dmg<0)
			return 0;

		return dmg;
	}
}
