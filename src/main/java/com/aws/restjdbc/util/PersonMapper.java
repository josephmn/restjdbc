package com.aws.restjdbc.util;

import com.aws.restjdbc.dto.PersonResponseDto;
import com.aws.restjdbc.dto.RequestDto;
import com.aws.restjdbc.dto.ResponseDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonMapper {

    public static PersonResponseDto mapRowToPerson(ResultSet rs, int rowNum) throws SQLException {
        return PersonResponseDto.builder()
                .id(rs.getInt("id"))
                .nombre(rs.getString("name"))
                .apellido(rs.getString("lastname"))
                .edad(rs.getInt("age"))
                .build();
    }

    public static ResponseDto buildResponse(String code, String message, Object data) {
        ResponseDto response = new ResponseDto();
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.PATTERN_YYYYMMDDHHMMSS);
        response.setDateTime(sdf.format(new Date()));

        return response;
    }
}
