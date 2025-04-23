package com.aws.restjdbc.dto;

import com.aws.restjdbc.util.Constants;
import com.aws.restjdbc.util.StrictObject;
import com.aws.restjdbc.util.ValidationRegexp;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class RequestDto extends StrictObject {

    @NotBlank(message = "Name cannot be blank")
    @Pattern(regexp = ValidationRegexp.REQ_NAME_REGEXP, message = "Name must contain only letters")
    private String name;

    @NotBlank(message = "Lastname cannot be blank")
    @NotNull(message = "Lastname cannot be null")
    @Pattern(regexp = ValidationRegexp.REQ_LASTNAME_NAME_REGEXP, message = "Lastname must contain only letters")
    private String lastname;

    @NotNull(message = "Age cannot be null")
    @Min(value = Constants.AGE_MIN, message = "Age cannot be less than 0")
    @Max(value = Constants.AGE_MAX, message = "The age cannot be greater than 110")
    private Integer age;
}
