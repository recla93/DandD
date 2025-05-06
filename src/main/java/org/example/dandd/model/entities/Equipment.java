package org.example.dandd.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.dandd.model.entities.pg.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Equipment extends BaseEntity
{
	private String name;
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_playable")
	private PgPlayable playable;
}
