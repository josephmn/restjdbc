package com.aws.restjdbc.service.impl;

import com.aws.restjdbc.dto.PersonResponseDto;
import com.aws.restjdbc.dto.RequestDto;
import com.aws.restjdbc.repository.PersonRepository;
import com.aws.restjdbc.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    @Override
    public List<PersonResponseDto> findAllPerson() {
        return personRepository.findAll();
    }

    @Override
    public PersonResponseDto findById(Integer id) {
        return personRepository.findById(id);
    }

    @Override
    public List<PersonResponseDto> findByName(String name) {
        return personRepository.findByName(name);
    }

    @Override
    public PersonResponseDto save(RequestDto requestDto) {
        return personRepository.save(requestDto);
    }

    @Override
    public PersonResponseDto update(Integer id, RequestDto requestDto) {
        return personRepository.update(id, requestDto);
    }

    @Override
    public Integer deleteById(Integer id) {
        return personRepository.deleteById(id);
    }
}
