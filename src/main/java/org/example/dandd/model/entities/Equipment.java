package org.example.dandd.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Equipment extends BaseEntity
{
	private String name;
	private String description;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private GameEntity owner;
}
