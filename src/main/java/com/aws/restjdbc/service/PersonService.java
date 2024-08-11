package com.aws.restjdbc.service;

import com.aws.restjdbc.dto.PersonDto;

import java.util.List;

public interface PersonService {
    // List Person
    List<PersonDto> listPerson();

    // Get Person by id
    PersonDto listPersonById(Long id);

    // create Person
    PersonDto createPerson(PersonDto personDto);

    // update Person
    PersonDto updatePerson(Long id, PersonDto personDto);

    // delete Person by id
    String deletePersonById(Long id);

    // delete Person by name
    String deletePersonByName(String name);

    // delete Person by lastName
    String deletePersonByLastName(String lastName);
}
