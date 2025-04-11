package com.aws.restjdbc.web;

import com.aws.restjdbc.dto.PersonResponseDto;
import com.aws.restjdbc.dto.RequestDto;
import com.aws.restjdbc.dto.ResponseDto;
import com.aws.restjdbc.service.PersonService;
import com.aws.restjdbc.util.PersonMapper;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/jdbcTemplate")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping()
    public ResponseEntity<ResponseDto> findAll() {
        List<PersonResponseDto> personDto = personService.findAllPerson();
        return ResponseEntity.ok().body(PersonMapper.buildResponse(String.valueOf(HttpStatus.OK.value()),
                "Consulta exitosa", personDto));
    };

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> findById(@PathVariable Integer id) {
        PersonResponseDto personDto = personService.findById(id);
        return ResponseEntity.ok().body(PersonMapper.buildResponse(String.valueOf(HttpStatus.OK.value()),
                "Consulta exitosa", personDto));
    };

    @GetMapping("/name")
    public ResponseEntity<ResponseDto> findByName(@PathParam("name") String name) {
        List<PersonResponseDto> personDto = personService.findByName(name);
        return ResponseEntity.ok().body(PersonMapper.buildResponse(String.valueOf(HttpStatus.OK.value()),
                "Consulta exitosa", personDto));
    };

    @PostMapping()
    public ResponseEntity<ResponseDto> save(@RequestBody RequestDto requestDto) {
        PersonResponseDto personDto = personService.save(requestDto);
        return ResponseEntity.ok().body(PersonMapper.buildResponse(String.valueOf(HttpStatus.CREATED.value()),
                "Creación exitosa", personDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> save(@PathVariable Integer id, @RequestBody RequestDto requestDto) {
        PersonResponseDto personDto = personService.update(id, requestDto);
        return ResponseEntity.ok().body(PersonMapper.buildResponse(String.valueOf(HttpStatus.OK.value()),
                "Creación exitosa", personDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> delete(@PathVariable Integer id) {
        return ResponseEntity.ok().body(PersonMapper.buildResponse(String.valueOf(HttpStatus.OK.value()),
                "Eliminación exitosa", personService.deleteById(id)));
    }
}
