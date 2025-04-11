package com.aws.restjdbc.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PersonResponseDto {
    private int id;
    private String nombre;
    private String apellido;
    private int edad;
}
