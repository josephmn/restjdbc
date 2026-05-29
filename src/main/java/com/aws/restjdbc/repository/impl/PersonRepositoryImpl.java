package com.aws.restjdbc.repository.impl;

import com.aws.restjdbc.dto.PersonResponseDto;
import com.aws.restjdbc.dto.RequestDto;
import com.aws.restjdbc.exception.types.DataNotFoundException;
import com.aws.restjdbc.exception.types.DataPersistenceException;
import com.aws.restjdbc.repository.PersonRepository;
import com.aws.restjdbc.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
        log.info("Repository findAll - ini");
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
                            log.info("requestDto findAll count: {}", requestDto.size());

                            if (requestDto.size() == 0) {
                                log.info("No data available for the requested query");
                                throw new DataNotFoundException("No data available for the requested query");
                            }
                            log.info("Repository findAll - end");
                            return requestDto;
                        } catch (SQLException ex) {
                            log.error("SQLException Repository findAll: {}", ex.getMessage());
                            throw new SQLException(Constants.MSG_SQL_EXCEPTION, ex);
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
        log.info("Repository findById - ini");
        return jdbcTemplate.execute(
                (Connection con) -> {
                    try (CallableStatement cs = con.prepareCall("{call person_sel(?)}")) {
                        cs.setInt(1, id);
                        try (ResultSet rs = cs.executeQuery()) {
                            if (rs.next()) {
                                log.info("Person found with id: {}", id);
                                log.info("Repository findById - end");
                                return PersonResponseDto.builder()
                                        .id(rs.getInt("id"))
                                        .nombre(rs.getString("name"))
                                        .apellido(rs.getString("lastname"))
                                        .edad(rs.getInt("age"))
                                        .build();
                            } else {
                                log.info("Person not found with id: {}", id);
                                throw new DataNotFoundException("Person with id: " + id + " not found");
                            }
                        } catch (SQLException ex) {
                            log.error("SQLException Repository findById: {}", ex.getMessage());
                            throw new SQLException(Constants.MSG_SQL_EXCEPTION, ex);
                        }
                    }
                }
        );
    }

    @Override
    public List<PersonResponseDto> findByName(String name) {
        log.info("Repository findByName - ini");
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

                            if (requestDto.size() == 0) {
                                log.info("Person not found with name: {}", name);
                                throw new DataNotFoundException("Person with name: " + name + " not found");
                            }
                            log.info("requestDto findByName count: {}", requestDto.size());
                            log.info("Repository findByName - end");
                            return requestDto;
                        }
                    } catch (SQLException ex) {
                        log.error("SQLException Repository findByName: {}", ex.getMessage());
                        throw new SQLException(Constants.MSG_SQL_EXCEPTION, ex);
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
                    } catch (SQLException ex) {
                        throw new SQLException(Constants.MSG_SQL_EXCEPTION, ex);
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
                    } catch (SQLException ex) {
                        throw new SQLException(Constants.MSG_SQL_EXCEPTION, ex);
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
                    } catch (SQLException ex) {
                        throw new SQLException("Error in DataBase SQL Query", ex);
                    }
                }
        );
    }
}
