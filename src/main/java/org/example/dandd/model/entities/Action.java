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
				dmg= p.getAtk();
			}
			case HEAVY ->
			{
				dmg= p.getAtk() + p.getEquipments().stream().mapToInt(arma->arma.getPlusDmg()).sum();
			}
			case SPECIALE ->
			{
				switch (p.getCharacterType())
				{
					case CODECLEANER ->
					{
						/// CON PRECISIONE BASSISSIMA SE HITTA UCCIDE LA QUALSIASI
						dmg=1000;

					}
					case CODETHIEF ->
					{
						/// DOVREBBE FARE TANTI DANNI AD UN SOLO TARGET
						dmg = p.getAtk() + p.getEquipments().stream().mapToInt(arma->arma.getPlusDmg()).sum() * molt;
					}
					case DATAMYSTIC ->
					{
						/// DOVREBBE FARE TANTI DANNI A PIù TARGET CON COOLDOWN SUPERIORE
						dmg = p.getAtk() + p.getEquipments().stream().mapToInt(arma->arma.getPlusDmg()).sum() * molt;

					}
					case GITBARD ->
					{
						/// DOVREBBE CURARE UN TARGET
						dmg=-20;
					}
					case INFRASTRUCTURE ->
					{
						/// LA DIFESA DIVENTA L'ATTACCO
						dmg= p.getDef() + p.getEquipments().stream().mapToInt(arma->arma.getPlusDef()).sum()*molt;
					}
					case TROUBLESHOOTER ->
					{
						/// 2 TARGET CON DANNI
						dmg = p.getAtk() + p.getEquipments().stream().mapToInt(arma->arma.getPlusDmg()).sum() * molt;
					}
				}
			}
		}


		return dmg - m.getDef();
	}


	public int dmgCalculator(Monster m, PgPlayable p)
	{
		int dmg = 0;
		return dmg= m.getAtk()-p.getDef();
	}


}
