package com.aws.restjdbc.dto;

import com.aws.restjdbc.util.Constants;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class ResponseDto {
    private String code;
    private String message;
    private Object data;
    private String dateTime;
}
