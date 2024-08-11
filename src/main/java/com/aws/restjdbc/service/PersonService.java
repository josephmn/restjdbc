package com.aws.restjdbc.service;

import com.aws.restjdbc.dto.PersonDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PersonService {
    // List Person
    ResponseEntity<List<PersonDto>> listPerson();

    // Get Person by id
    ResponseEntity<PersonDto> listPersonById(int id);

    // create Person
    ResponseEntity<PersonDto> createPerson(PersonDto personDto);

    // update Person
    ResponseEntity<PersonDto> updatePerson(int id, PersonDto personDto) throws IllegalArgumentException;

    // delete Person by id
    ResponseEntity<String> deletePersonById(int id) throws IllegalArgumentException;

    // delete Person by name
    String deletePersonByName(String name);

    // delete Person by lastName
    String deletePersonByLastName(String lastName);
}
