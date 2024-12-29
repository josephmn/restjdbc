package com.aws.restjdbc.repository.impl;

import com.aws.restjdbc.dto.PersonDto;
import com.aws.restjdbc.repository.PersonRepository;
import com.aws.restjdbc.util.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
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
    public PersonDto save(PersonDto person) {
        String sql = "INSERT INTO person (name, lastname, age) VALUES (?, ?, ?)";
        // return jdbcTemplate.update(sql, person.getNombre(), person.getApellido(), person.getEdad());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        // Execute insert
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, person.getNombre());
            ps.setString(2, person.getApellido());
            ps.setInt(3, person.getEdad());
            return ps;
        }, keyHolder);

        int generatedId = keyHolder.getKey().intValue();

        // Option 1: Execute method findById
        // return findById(generatedId);

        // Option 2: Use Build PersonDto
        return PersonDto.builder()
                .id(generatedId)
                .nombre(person.getNombre())
                .apellido(person.getApellido())
                .edad(person.getEdad())
                .build();
    }

    @Override
    public PersonDto update(Integer id, PersonDto person) {
        String sql = "UPDATE person SET name = ?, lastname = ?, age = ? WHERE id = ?";
        jdbcTemplate.update(sql, person.getNombre(), person.getApellido(), person.getEdad(), id);

        return findById(id);
    }

    @Override
    public Integer deleteById(Integer id) {
        String sql = "DELETE FROM person WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
