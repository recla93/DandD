package org.example.dandd.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dandd.model.entities.enums.ActionType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionRequest
{
	private GameStateDto previousDto;
	private int target;
	private ActionType actionType;
}
