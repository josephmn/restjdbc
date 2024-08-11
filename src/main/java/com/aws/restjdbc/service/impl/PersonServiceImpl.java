package com.aws.restjdbc.service.impl;

import com.aws.restjdbc.domain.Person;
import com.aws.restjdbc.dto.PersonDto;
import com.aws.restjdbc.service.PersonService;
import com.aws.restjdbc.util.MysqlConnection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final MysqlConnection mysqlConnection;

    @Override
    public List<PersonDto> listPerson() {
        List<PersonDto> person = new ArrayList<>();
        String sql = "select * from person";
        try (Connection conn = this.mysqlConnection.getConnection(); Statement stmt = conn.createStatement()) {
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
        return person;
    }

    @Override
    public PersonDto listPersonById(Long id) {
        return null;
    }

    @Override
    public PersonDto createPerson(PersonDto personDto) {
        return null;
    }

    @Override
    public PersonDto updatePerson(Long id, PersonDto personDto) {
        return null;
    }

    @Override
    public String deletePersonById(Long id) {
        return "";
    }

    @Override
    public String deletePersonByName(String name) {
        return "";
    }

    @Override
    public String deletePersonByLastName(String lastName) {
        return "";
    }
}
