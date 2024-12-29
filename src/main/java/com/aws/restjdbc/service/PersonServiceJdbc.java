package com.aws.restjdbc.service;

import com.aws.restjdbc.dto.PersonDto;

import java.util.List;

public interface PersonServiceJdbc {
    List<PersonDto> findAllPerson();

    PersonDto findById(Integer id);

    PersonDto save(PersonDto personDto);

    PersonDto update(Integer id, PersonDto personDto);

    Integer deleteById(Integer id);
}
