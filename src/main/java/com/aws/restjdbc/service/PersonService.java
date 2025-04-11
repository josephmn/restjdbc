package com.aws.restjdbc.service;

import com.aws.restjdbc.dto.PersonResponseDto;
import com.aws.restjdbc.dto.RequestDto;
import com.aws.restjdbc.dto.ResponseDto;

import java.util.List;

public interface PersonService {
    List<PersonResponseDto> findAllPerson();

    PersonResponseDto findById(Integer id);

    List<PersonResponseDto> findByName(String name);

    PersonResponseDto save(RequestDto requestDto);

    PersonResponseDto update(Integer id, RequestDto requestDto);

    Integer deleteById(Integer id);
}
