package com.aws.restjdbc.web;

import com.aws.restjdbc.dto.PersonDto;
import com.aws.restjdbc.service.PersonServiceJdbc;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/jdbcTemplate")
@RequiredArgsConstructor
public class PersonJdbcTemplateController {

    private final PersonServiceJdbc personService;

    @GetMapping()
    public ResponseEntity<List<PersonDto>> findAll() {
        return ResponseEntity.ok(personService.findAllPerson());
    };

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(personService.findById(id));
    };

    @PostMapping()
    public ResponseEntity<PersonDto> save(@RequestBody PersonDto personDto) {
        return ResponseEntity.status(201).body(personService.save(personDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> save(@PathVariable Integer id, @RequestBody PersonDto personDto) {
        return ResponseEntity.ok(personService.update(id, personDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(personService.deleteById(id));
    }
}
