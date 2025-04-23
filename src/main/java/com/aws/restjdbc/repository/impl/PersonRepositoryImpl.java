package com.aws.restjdbc.repository.impl;

import com.aws.restjdbc.dto.PersonResponseDto;
import com.aws.restjdbc.dto.RequestDto;
import com.aws.restjdbc.exception.types.DataNotFoundException;
import com.aws.restjdbc.exception.types.DataPersistenceException;
import com.aws.restjdbc.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<PersonResponseDto> findAll() {
        /*String sql = "SELECT * FROM person";
        try {
            return jdbcTemplate.query(sql, PersonMapper::mapRowToPerson);
        } catch (DataAccessException e) {
            throw new DataNotFoundException("No data available for the requested query");
        }*/
        return jdbcTemplate.execute(
                (Connection con) -> {
                    try (CallableStatement cs = con.prepareCall("{call person_all()}")) {
                        try (ResultSet rs = cs.executeQuery()) {
                            List<PersonResponseDto> requestDto = new ArrayList<>();
                            while (rs.next()) {
                                PersonResponseDto person = PersonResponseDto.builder()
                                        .id(rs.getInt("id"))
                                        .nombre(rs.getString("name"))
                                        .apellido(rs.getString("lastname"))
                                        .edad(rs.getInt("age"))
                                        .build();
                                requestDto.add(person);
                            }
                            return requestDto;
                        }
                    }
                }
        );
    }

    @Override
    public PersonResponseDto findById(Integer id) {
        /*String sql = "SELECT * FROM person WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, PersonMapper::mapRowToPerson, id);
        } catch (Exception e) {
            throw new DataNotFoundException("Person with id: " + id + " not found");
        }*/
        return jdbcTemplate.execute(
                (Connection con) -> {
                    try (CallableStatement cs = con.prepareCall("{call person_sel(?)}")) {
                        cs.setInt(1, id);
                        try (ResultSet rs = cs.executeQuery()) {
                            if (rs.next()) {
                                return PersonResponseDto.builder()
                                        .id(rs.getInt("id"))
                                        .nombre(rs.getString("name"))
                                        .apellido(rs.getString("lastname"))
                                        .edad(rs.getInt("age"))
                                        .build();
                            } else {
                                throw new DataNotFoundException("Person with id: " + id + " not found");
                            }
                        }
                    }
                }
        );
    }

    @Override
    public List<PersonResponseDto> findByName(String name) {
        return jdbcTemplate.execute(
                (Connection con) -> {
                    try (CallableStatement cs = con.prepareCall("{call person_sel_name(?)}")) {
                        cs.setString(1, name);
                        try (ResultSet rs = cs.executeQuery()) {
                            List<PersonResponseDto> requestDto = new ArrayList<>();
                            while (rs.next()) {
                                PersonResponseDto person = PersonResponseDto.builder()
                                        .id(rs.getInt("id"))
                                        .nombre(rs.getString("name"))
                                        .apellido(rs.getString("lastname"))
                                        .edad(rs.getInt("age"))
                                        .build();
                                requestDto.add(person);
                            }
                            return requestDto;
                        } catch (SQLException e) {
                            throw new DataNotFoundException("Person with name: " + name + " not found");
                        }
                    }
                }
        );
    }

    @Override
    public PersonResponseDto save(RequestDto person) {
        /*String sql = "INSERT INTO person (name, lastname, age) VALUES (?, ?, ?)";

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
        }*/
        return jdbcTemplate.execute(
                (Connection con) -> {
                    try (CallableStatement cs = con.prepareCall("{call person_ins(?, ?, ?)}")) {
                        cs.setString(1, person.getName());
                        cs.setString(2, person.getLastname());
                        cs.setInt(3, person.getAge());
                        try (ResultSet rs = cs.executeQuery()) {
                            if (rs.next()) {
                                return PersonResponseDto.builder()
                                        .id(rs.getInt("id"))
                                        .nombre(rs.getString("name"))
                                        .apellido(rs.getString("lastname"))
                                        .edad(rs.getInt("age"))
                                        .build();
                            } else {
                                throw new DataPersistenceException("Failed to save person to the database");
                            }
                        }
                    }
                }
        );
    }

    @Override
    public PersonResponseDto update(Integer id, RequestDto person) {
        /*String sql = "UPDATE person SET name = ?, lastname = ?, age = ? WHERE id = ?";

        try {
            jdbcTemplate.update(sql, person.getNombre(), person.getApellido(), person.getEdad(), id);
            return findById(id);
        } catch (DataAccessException e) {
            throw new DataPersistenceException("Failed to update person in the database");
        }*/
        return jdbcTemplate.execute(
                (Connection con) -> {
                    try (CallableStatement cs = con.prepareCall("{call person_upd(?, ?, ?, ?)}")) {
                        cs.setInt(1, id);
                        cs.setString(2, person.getName());
                        cs.setString(3, person.getLastname());
                        cs.setInt(4, person.getAge());
                        try (ResultSet rs = cs.executeQuery()) {
                            if (rs.next()) {
                                return PersonResponseDto.builder()
                                        .id(rs.getInt("id"))
                                        .nombre(rs.getString("name"))
                                        .apellido(rs.getString("lastname"))
                                        .edad(rs.getInt("age"))
                                        .build();
                            } else {
                                throw new DataPersistenceException("Failed to update person in the database");
                            }
                        }
                    }
                }
        );
    }

    @Override
    public Integer deleteById(Integer id) {
        /*String sql = "DELETE FROM person WHERE id = ?";

        try {
            return jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            throw new DataPersistenceException("Failed to delete person from the database");
        }*/
        return jdbcTemplate.execute(
                (Connection con) -> {
                    try (CallableStatement cs = con.prepareCall("{call person_del(?)}")) {
                        cs.setInt(1, id);
                        return cs.executeUpdate();
                    }
                }
        );
    }
}
