package org.example.dandd.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
