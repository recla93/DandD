package org.example.dandd.model.entities.pg;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dandd.model.entities.Equipment;
import org.example.dandd.model.entities.GameEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TroubleShooter extends GameEntity
{

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "TroubleShouter", cascade = CascadeType.ALL)
	private List<Equipment> equipments = new ArrayList<>();
}
