package com.aws.restjdbc.service;

import com.aws.restjdbc.dto.PersonDto;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;

public interface PersonService {
    // List Person
    ResponseEntity<List<PersonDto>> listPerson() throws SQLException;

    // Get Person by id
    ResponseEntity<PersonDto> personById(int id) throws SQLException;

    // create Person
    ResponseEntity<PersonDto> createPerson(PersonDto personDto) throws SQLException;

    // update Person
    ResponseEntity<PersonDto> updatePerson(int id, PersonDto personDto) throws IllegalArgumentException, SQLException;

    // delete Person by id
    ResponseEntity<String> deletePersonById(int id) throws IllegalArgumentException, SQLException;

    // delete Person by name
    String deletePersonByName(String name);

    // delete Person by lastName
    String deletePersonByLastName(String lastName);
}
