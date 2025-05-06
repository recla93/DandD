package org.example.dandd.model.entities.pg;

import jakarta.persistence.*;
import lombok.*;
import org.example.dandd.model.entities.Action;
import org.example.dandd.model.entities.Equipment;
import org.example.dandd.model.entities.GameEntity;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("playable")
public class CodeThief extends GameEntity
{


	@OneToMany(fetch = FetchType.EAGER, mappedBy = "codethief", cascade = CascadeType.ALL)
	private List<Equipment> equipments;
}
