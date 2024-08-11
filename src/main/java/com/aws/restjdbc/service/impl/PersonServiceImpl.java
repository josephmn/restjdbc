package com.aws.restjdbc.service.impl;

import com.aws.restjdbc.dto.PersonDto;
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
    public ResponseEntity<List<PersonDto>> listPerson() {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(person);
    }

    @Override
    public ResponseEntity<PersonDto> listPersonById(int id) {
        String sql = "select * from person where id = ?";
        try (Connection conn = this.mysqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PersonDto person = new PersonDto(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getInt("age")
                );
                return ResponseEntity.status(HttpStatus.OK).body(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<PersonDto> createPerson(PersonDto personDto) {
        String sql = "INSERT INTO person (name, lastname, age) VALUES (?, ?, ?)";
        try (Connection conn = this.mysqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set the values for the insert statement
            pstmt.setString(1, personDto.getNombre());
            pstmt.setString(2, personDto.getApellido());
            pstmt.setInt(3, personDto.getEdad());

            // Execute the update
            pstmt.executeUpdate();

            // Get the generated keys (e.g., the ID)
            ResultSet rs = pstmt.getGeneratedKeys();
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
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<PersonDto> updatePerson(int id, PersonDto personDto) {
        if (!existPersonById(id)) {
            throw new IllegalArgumentException("Person with ID " + id + " does not exist.");
        }

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
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }
            pstmt.executeUpdate();

            return this.listPersonById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating person with ID " + id, e);
        }
    }

    @Override
    public ResponseEntity<String> deletePersonById(int id) {
        if (!existPersonById(id)) {
            throw new IllegalArgumentException("Person with ID " + id + " does not exist.");
        }
        String sql = "delete from person where id = ?";
        try (Connection conn = this.mysqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error delete register by id");
    }

    @Override
    public String deletePersonByName(String name) {
        return "";
    }

    @Override
    public String deletePersonByLastName(String lastName) {
        return "";
    }

    public boolean existPersonById(int id) {
        String sql = "select * from person where id = ?";
        try (Connection conn = this.mysqlConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
