package com.aws.restjdbc.repository;

import com.aws.restjdbc.dto.PersonDto;

import java.util.List;

public interface PersonRepository {
    List<PersonDto> findAll();
    PersonDto findById(Integer id);
    PersonDto save(PersonDto person);
    PersonDto update(Integer id, PersonDto person);
    Integer deleteById(Integer id);
}
