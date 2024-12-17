package com.aws.restjdbc.util;

import com.aws.restjdbc.dto.PersonDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper {

    public static PersonDto mapRowToPerson(ResultSet rs, int rowNum) throws SQLException {
        return PersonDto.builder()
                .id(rs.getInt("id"))
                .nombre(rs.getString("name"))
                .apellido(rs.getString("lastname"))
                .edad(rs.getInt("age"))
                .build();
    }
}
