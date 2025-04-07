package com.aws.restjdbc.repository.impl;

import com.aws.restjdbc.dto.PersonDto;
import com.aws.restjdbc.exception.types.DataNotFoundException;
import com.aws.restjdbc.exception.types.DataPersistenceException;
import com.aws.restjdbc.repository.PersonRepository;
import com.aws.restjdbc.util.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
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
        try {
            return jdbcTemplate.query(sql, PersonMapper::mapRowToPerson);
        } catch (DataAccessException e) {
            throw new DataNotFoundException("No data available for the requested query");
        }
    }

    @Override
    public PersonDto findById(Integer id) {
        String sql = "SELECT * FROM person WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, PersonMapper::mapRowToPerson, id);
        } catch (Exception e) {
            throw new DataNotFoundException("Person with id: " + id + " not found");
        }
    }

    @Override
    public PersonDto save(PersonDto person) {
        String sql = "INSERT INTO person (name, lastname, age) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, person.getNombre());
                ps.setString(2, person.getApellido());
                ps.setInt(3, person.getEdad());
                return ps;
            }, keyHolder);

            Number key = keyHolder.getKey();
            Integer generatedId = (key != null) ? key.intValue() : null;

            return PersonDto.builder()
                    .id(generatedId)
                    .nombre(person.getNombre())
                    .apellido(person.getApellido())
                    .edad(person.getEdad())
                    .build();

        } catch (DataAccessException e) {
            throw new DataPersistenceException("Failed to save person to the database");
        }

    }

    @Override
    public PersonDto update(Integer id, PersonDto person) {
        String sql = "UPDATE person SET name = ?, lastname = ?, age = ? WHERE id = ?";

        try {
            jdbcTemplate.update(sql, person.getNombre(), person.getApellido(), person.getEdad(), id);
            return findById(id);
        } catch (DataAccessException e) {
            throw new DataPersistenceException("Failed to update person in the database");
        }
    }

    @Override
    public Integer deleteById(Integer id) {
        String sql = "DELETE FROM person WHERE id = ?";

        try {
            return jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            throw new DataPersistenceException("Failed to delete person from the database");
        }
    }
}
