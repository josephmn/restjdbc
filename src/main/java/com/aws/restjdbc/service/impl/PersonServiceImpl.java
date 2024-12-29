package com.aws.restjdbc.service.impl;

import com.aws.restjdbc.dto.PersonDto;
import com.aws.restjdbc.exception.types.DataNotContentException;
import com.aws.restjdbc.service.PersonService;
import com.aws.restjdbc.util.MysqlConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final MysqlConnection mysqlConnection;

    @Override
    public ResponseEntity<List<PersonDto>> listPerson() throws SQLException {
        List<PersonDto> person = new ArrayList<>();
        String sql = "select id, name, lastname, age from person";
        try (Connection conn = this.mysqlConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                person.add(new PersonDto(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getInt("age")
                ));
            }
            if (person.isEmpty()) {
                throw new DataNotContentException("No data found for the query.");
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(person);
    }

    @Override
    public ResponseEntity<PersonDto> personById(int id) throws SQLException {
        // Basic Validation Id
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        PersonDto person = new PersonDto();
        String sql = "select * from person where id = ?";

        try (Connection conn = this.mysqlConnection.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(sql)) {

            pStmt.setInt(1, id);

            try (ResultSet rs = pStmt.executeQuery()) {
                if (rs.next()) {
                    person.setId(rs.getInt("id"));
                    person.setNombre(rs.getString("name"));
                    person.setApellido(rs.getString("lastname"));
                    person.setEdad(rs.getInt("age"));
                    return ResponseEntity.ok(person);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
        } catch (SQLException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @Override
    public ResponseEntity<PersonDto> createPerson(PersonDto personDto) throws SQLException {
        String sql = "INSERT INTO person (name, lastname, age) VALUES (?, ?, ?)";
        try (Connection conn = this.mysqlConnection.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set the values for the insert statement
            pStmt.setString(1, personDto.getNombre());
            pStmt.setString(2, personDto.getApellido());
            pStmt.setInt(3, personDto.getEdad());

            // Execute the update
            pStmt.executeUpdate();

            // Get the generated keys (e.g., the ID)
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                // Set the generated ID in the return PersonDto Builder
                PersonDto person = PersonDto.builder()
                        .id(generatedId)
                        .nombre(personDto.getNombre())
                        .apellido(personDto.getApellido())
                        .edad(personDto.getEdad())
                        .build();
                return ResponseEntity.status(HttpStatus.CREATED).body(person);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return null;
    }

    @Override
    public ResponseEntity<PersonDto> updatePerson(int id, PersonDto personDto) throws SQLException {
        if (!existPersonById(id)) {
            throw new IllegalArgumentException("Person with ID " + id + " does not exist.");
        } else {
            StringBuilder sql = new StringBuilder("update person set ");
            List<Object> parameters = new ArrayList<>();

            if (personDto.getNombre() != null) {
                sql.append("name = ?, ");
                parameters.add(personDto.getNombre());
            }

            if (personDto.getApellido() != null) {
                sql.append("lastname = ?, ");
                parameters.add(personDto.getApellido());
            }

            if (personDto.getEdad() != 0) {
                sql.append("age = ?, ");
                parameters.add(personDto.getEdad());
            }

            // Verify that there is at least one field to update
            if (parameters.isEmpty()) {
                throw new IllegalArgumentException("No fields to update.");
            }

            // Remove the last comma and space
            sql.setLength(sql.length() - 2);
            sql.append(" WHERE id = ?");
            parameters.add(id);

            try (Connection conn = this.mysqlConnection.getConnection();
                 PreparedStatement pStmt = conn.prepareStatement(sql.toString())) {
                for (int i = 0; i < parameters.size(); i++) {
                    pStmt.setObject(i + 1, parameters.get(i));
                }
                pStmt.executeUpdate();

                return this.personById(id);
            } catch (SQLException e) {
                throw new RuntimeException("Error updating person with ID " + id, e);
            }
        }
    }

    @Override
    public ResponseEntity<String> deletePersonById(int id) throws SQLException {
        if (!existPersonById(id)) {
            throw new IllegalArgumentException("Person with ID " + id + " does not exist.");
        } else {
            String sql = "delete from person where id = ?";
            try (Connection conn = this.mysqlConnection.getConnection();
                 PreparedStatement pStmt = conn.prepareStatement(sql)) {
                pStmt.setInt(1, id);
                pStmt.executeUpdate();
                return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
            } catch (SQLException e) {
                throw new SQLException(e);
            }
        }
    }

    @Override
    public String deletePersonByName(String name) {
        return "";
    }

    @Override
    public String deletePersonByLastName(String lastName) {
        return "";
    }

    public boolean existPersonById(int id) throws SQLException {
        String sql = "select * from person where id = ?";
        try (Connection conn = this.mysqlConnection.getConnection();
             PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setInt(1, id);
            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return false;
    }
}
