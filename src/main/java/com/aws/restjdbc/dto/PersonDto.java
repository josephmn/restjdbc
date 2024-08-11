package com.aws.restjdbc.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PersonDto {
    private int id;
    private String nombre;
    private String apellido;
    private int edad;
}
