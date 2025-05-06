package org.example.dandd.model.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Battle extends BaseEntity
{
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "battle")
	private List<GameEntity> gameEntities;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "battle")
	private List<Action> actions;


}
