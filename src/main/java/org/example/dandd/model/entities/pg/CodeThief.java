package org.example.dandd.model.entities.pg;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.example.dandd.model.entities.Action;
import org.example.dandd.model.entities.Equipment;
import org.example.dandd.model.entities.GameEntity;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeThief extends GameEntity
{


	@OneToMany(fetch = FetchType.EAGER, mappedBy = "CodeThief", cascade = CascadeType.ALL)
	private List<Equipment> equipments = new ArrayList<>();
}
