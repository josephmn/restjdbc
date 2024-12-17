package com.aws.restjdbc.web;

import com.aws.restjdbc.dto.PersonDto;
import com.aws.restjdbc.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public ResponseEntity<List<PersonDto>> listPerson() throws SQLException {
        return this.personService.listPerson();
    };

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> personById(@PathVariable(value = "id") int id) throws SQLException {
        return this.personService.personById(id);
    };

    @PostMapping
    public ResponseEntity<PersonDto> createPerson(@RequestBody PersonDto personDto) throws SQLException {
        return this.personService.createPerson(personDto);
    };

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable(value = "id") int id, @RequestBody PersonDto personDto) throws SQLException {
        return this.personService.updatePerson(id, personDto);
    };

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePersonById(@PathVariable(value = "id") int id) throws SQLException {
        return this.personService.deletePersonById(id);
    };
}
