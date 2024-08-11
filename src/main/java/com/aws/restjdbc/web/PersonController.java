package com.aws.restjdbc.web;

import com.aws.restjdbc.dto.PersonDto;
import com.aws.restjdbc.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public List<PersonDto> listPerson() {
        return this.personService.listPerson();
    };
}
