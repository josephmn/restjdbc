package com.aws.restjdbc.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Person {
    private int id;
    private String name;
    private String lastname;
    private int age;
}
