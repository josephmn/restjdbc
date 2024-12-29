package com.aws.restjdbc.service.impl;

import com.aws.restjdbc.dto.PersonDto;
import com.aws.restjdbc.repository.PersonRepository;
import com.aws.restjdbc.service.PersonServiceJdbc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceJdbcImpl implements PersonServiceJdbc {

    private final PersonRepository personRepository;

    @Override
    public List<PersonDto> findAllPerson() {
        return personRepository.findAll();
    }

    @Override
    public PersonDto findById(Integer id) {
        return personRepository.findById(id);
    }

    @Override
    public PersonDto save(PersonDto personDto) {
        return personRepository.save(personDto);
    }

    @Override
    public PersonDto update(Integer id, PersonDto personDto) {
        return personRepository.update(id, personDto);
    }

    @Override
    public Integer deleteById(Integer id) {
        return personRepository.deleteById(id);
    }
}
