package org.example.dandd.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.dandd.model.entities.enums.ActionType;
import org.example.dandd.model.entities.pg.PgPlayable;
import org.example.dandd.service.BattleService;

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
	@Enumerated(EnumType.STRING)
	private ActionType actionType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_entity")
	private GameEntity entity;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_battle")
	private BattleService battleService;

	public boolean hit()
	{
		int dice = (int) (Math.random() * 100) + 1;

		return precision > dice;
	}

	public int dmgCalculator(PgPlayable p, Monster m)
	{
		int dmg = 0;

		switch(actionType)
		{
			case BASE ->
			{
				dmg= p.getAtk()-m.getDef();
			}
			case HEAVY ->
			{
				dmg= p.getAtk() + p.getEquipments().stream().mapToInt(arma->arma.getPlusDmg()).sum();
			}
			case SPECIALE ->
			{
				//TODO da definire i vari attacchi speciali
				switch (p.getCharacterType())
				{
					case CODECLEANER ->
					{
						dmg= p.getAtk()-m.getDef();
					}
					case CODETHIEF ->
					{
						dmg= p.getAtk()-m.getDef();
					}
					case DATAMYSTIC ->
					{
						dmg= p.getAtk()-m.getDef();
					}
					case GITBARD ->
					{
						dmg=-20;
					}
					case INFRASTRUCTURE ->
					{
						dmg= p.getAtk()-m.getDef();
					}
					case TROUBLESHOOTER ->
					{
						dmg= p.getAtk()-m.getDef();
					}
				}
			}
		}


		return dmg;
	}

	public int dmgCalculator(Monster m, PgPlayable p)
	{
		int dmg = 0;
		return dmg= m.getAtk()-p.getDef();
	}


}
