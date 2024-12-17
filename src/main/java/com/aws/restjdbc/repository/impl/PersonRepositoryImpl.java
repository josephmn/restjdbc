package com.aws.restjdbc.repository.impl;

import com.aws.restjdbc.dto.PersonDto;
import com.aws.restjdbc.repository.PersonRepository;
import com.aws.restjdbc.util.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<PersonDto> findAll() {
        String sql = "SELECT * FROM person";
        return jdbcTemplate.query(sql, PersonMapper::mapRowToPerson);
    }

    @Override
    public PersonDto findById(Integer id) {
        String sql = "SELECT * FROM person WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, PersonMapper::mapRowToPerson, id);
    }

    @Override
    public Integer save(PersonDto person) {
        String sql = "INSERT INTO person (name, lastname, age) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, person.getNombre(), person.getApellido(), person.getEdad());
    }

    @Override
    public Integer update(Integer id, PersonDto person) {
        String sql = "UPDATE person SET name = ?, lastname = ?, age = ? WHERE id = ?";
        return jdbcTemplate.update(sql, person.getNombre(), person.getApellido(), person.getEdad(), id);
    }

    @Override
    public Integer deleteById(Integer id) {
        String sql = "DELETE FROM person WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
