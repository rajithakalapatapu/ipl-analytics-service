package dev.rajitha.ipl2020.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class RoleCountDTO {
	private String role;
	private Long count;
}
