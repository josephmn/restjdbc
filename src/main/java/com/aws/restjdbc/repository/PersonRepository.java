package com.aws.restjdbc.repository;

import com.aws.restjdbc.dto.PersonResponseDto;
import com.aws.restjdbc.dto.RequestDto;

import java.util.List;

public interface PersonRepository {
    List<PersonResponseDto> findAll();
    PersonResponseDto findById(Integer id);
    List<PersonResponseDto> findByName(String name);
    PersonResponseDto save(RequestDto person);
    PersonResponseDto update(Integer id, RequestDto person);
    Integer deleteById(Integer id);
}
