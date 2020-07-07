package dev.rajitha.ipl2020.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class TeamAmountDTO {
	private String label;
	private Long amount;
}
