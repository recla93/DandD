package org.example.dandd.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.dandd.model.entities.enums.ActionType;
import org.example.dandd.model.entities.enums.CharacterType;
import org.example.dandd.model.entities.pg.PgPlayable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action extends BaseEntity {
	private String nameAction;

	@Column(columnDefinition = "TEXT")
	private String descriptionAction;

	@Column(name = "accuracy")
	private int precision;

	private double molt;
	private int maxNumTarget;
	private int cooldown;

	@Enumerated(EnumType.STRING)
	private ActionType actionType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_entity")
	private GameEntity entity;

	/**
	 * Determina se un attacco ha successo in base a un tiro casuale.
	 * <p>
	 * Viene generato un numero casuale tra 1 e 100. Se questo numero è inferiore
	 * al valore della precisione dell'entità, l'attacco va a segno.
	 * </p>
	 *
	 * @return {@code true} se l'attacco ha colpito (precisione maggiore del tiro), {@code false} altrimenti.
	 */
	public boolean hit() {
		int dice = (int) (Math.random() * 100) + 1;
		return precision > dice;
	}

	/**
	 * Calcola i danni inflitti da un'entità attaccante a un'entità difensore,
	 * tenendo conto del tipo di azione e delle caratteristiche di entrambe.
	 * <p>
	 * Il metodo implementa anche effetti speciali basati sul tipo di personaggio.
	 * In particolare:
	 * <ul>
	 *     <li>Un {@code PgPlayable} può infliggere danni bonus tramite equipaggiamenti.</li>
	 *     <li>Il {@code GitBard} usa l'azione SPECIALE per curare invece di danneggiare
	 *         (restituisce un valore negativo come cura).</li>
	 *     <li>Il {@code CODECLEANER} infligge un danno speciale fisso di 1000.</li>
	 *     <li>Un {@code Monster} infligge danni con moltiplicatori crescenti in base all'azione.</li>
	 * </ul>
	 * Se il colpo non va a segno (basato sul metodo {@code hit()}), restituisce 0.
	 *
	 * @param attaccante L'entità che esegue l'attacco (può essere un {@code PgPlayable} o {@code Monster}).
	 * @param difensore  L'entità bersaglio dell'attacco.
	 * @return Il valore dei danni calcolati (può essere negativo se si tratta di cura, es. GitBard).
	 */
	public int dmgCalculator(GameEntity attaccante, GameEntity difensore) {
		if (!hit()) return 0;

		double dmg = 0;

		if (attaccante instanceof PgPlayable p) {
			if (difensore instanceof Monster m) {
				switch (actionType) {
					case BASE -> dmg = p.getAtk();
					case HEAVY -> dmg = p.getAtk() + p.getEquipments().stream()
							.mapToInt(arma -> arma.getPlusDmg())
							.sum();
					case SPECIALE -> {
						switch (p.getCharacterType()) {
							case CODECLEANER -> dmg = 1000;
							case CODETHIEF, DATAMYSTIC, TROUBLESHOOTER -> {
								int equipBonus = p.getEquipments().stream()
										.mapToInt(arma -> arma.getPlusDmg())
										.sum();
								dmg = p.getAtk() + (equipBonus * molt);
							}
							case GITBARD -> dmg = -20; // fallback se cura un mostro
							case INFRASTRUCTURE -> {
								int defBonus = p.getEquipments().stream()
										.mapToInt(arma -> arma.getPlusDef())
										.sum();
								dmg = p.getDef() + (defBonus * molt);
							}
						}
					}
				}
				return (int) Math.round(dmg - m.getDef());
			} else if (difensore instanceof PgPlayable targetP) {
				if (actionType == ActionType.SPECIALE && p.getCharacterType() == CharacterType.GITBARD) {
					// Cura fissa (es. 20 HP), restituita come valore negativo
					return -20;
				}
			}
		}

		if (attaccante instanceof Monster m && difensore instanceof PgPlayable p) {
			switch (actionType) {
				case BASE -> dmg = m.getAtk();
				case SPECIALE -> dmg = (m.getAtk() + m.getAtk()) * molt;
			}
			return (int) Math.round(dmg - p.getDef());
		}

		return 0;
	}
}