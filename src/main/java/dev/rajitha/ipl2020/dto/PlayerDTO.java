package dev.rajitha.ipl2020.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlayerDTO {
    private String player;
    private Long price;
    private String role;
}
