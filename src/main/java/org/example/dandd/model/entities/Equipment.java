package org.example.dandd.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dandd.model.entities.pg.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "equip")
public class Equipment extends BaseEntity
{
	private String name;
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_codecleaner")
	private CodeCleaner codecleaner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_codethief")
	private CodeThief codethief;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_datamystic")
	private DataMystic datamystic;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_gitbard")
	private GitBard gitbard;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_infrastructure")
	private Infrastructure infrastructure;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_troubleshooter")
	private TroubleShooter troubleshooter;

}
