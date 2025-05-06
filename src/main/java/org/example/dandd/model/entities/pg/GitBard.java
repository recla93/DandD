package org.example.dandd.model.entities.pg;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.example.dandd.model.entities.Action;
import org.example.dandd.model.entities.Equipment;
import org.example.dandd.model.entities.GameEntity;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class GitBard extends GameEntity
{

}
