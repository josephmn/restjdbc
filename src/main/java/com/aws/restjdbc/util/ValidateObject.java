package com.aws.restjdbc.util;

import com.aws.restjdbc.exception.types.DuplicateFieldException;
import com.aws.restjdbc.exception.types.UnknownFieldException;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateObject {

    private final Validator validator;

    public <T extends StrictObject> T validRequestBody(String jsonString, Class<T> clazz) {
        T obj = validJsonStringAndMapToDto(jsonString, clazz);
        validateUnknownFields(obj);
        validateObjectAnnotations(obj, clazz);
        return obj;
    }

    private <T> void validateObjectAnnotations(T obj, Class<T> clazz) {
        List<String> errorMessages = new ArrayList<>();
        BindingResult bindingResult = new BeanPropertyBindingResult(obj, clazz.getSimpleName());
        validator.validate(obj, bindingResult);

        log.info("Objeto a validar: {}", obj);
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error ->
                    errorMessages.add(error.getField() + " - " + error.getDefaultMessage())
            );
            throw new IllegalArgumentException(errorMessages.toString());
        } else {
            log.info("No validation errors found");
        }
    }

    public <T> T validJsonStringAndMapToDto(String jsonString, Class<T> targetClass) {
        detectAllDuplicateFields(jsonString);
        try {
            JsonFactory factory = new JsonFactory();
            factory.enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
            ObjectMapper objectMapper = new ObjectMapper(factory);
            return objectMapper.readValue(jsonString, targetClass);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid Json: " + e.getMessage());
        }
    }

    public void detectAllDuplicateFields(String jsonString) {
        List<String> errors = new ArrayList<>();
        Map<String, Integer> fieldCount = new LinkedHashMap<>();
        try (Scanner scanner = new Scanner(jsonString)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.contains(":")) {
                    String field = line.split(":")[0]
                            .replaceAll("[\"{,]", "")
                            .trim();
                    fieldCount.put(field, fieldCount.getOrDefault(field, 0) + 1);
                }
            }
            Set<String> duplicateFields = fieldCount.entrySet().stream()
                    .filter(entry -> entry.getValue() > 1)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toCollection(TreeSet::new));

            if (duplicateFields.isEmpty()) {
                log.info("No duplicate fields found");
                return;
            }

            String response = "Duplicate field: " + String.join(", ", duplicateFields);
            log.info("Throw IllegalArgumentException -> {}", response);
            throw new DuplicateFieldException(response);
        }
    }

    private <T extends StrictObject> void validateUnknownFields(T data) {
        Set<String> fieldsNotAllowed = data.getUnknownFields()
                .collect(Collectors.toCollection(TreeSet::new));

        if (fieldsNotAllowed.isEmpty()) {
            log.info("No disallowed fields found");
            return;
        }

        String response = "Disallowed field: " + String.join(", ", fieldsNotAllowed);
        log.info("Throw UnknownFieldException -> {}", response);
        throw new UnknownFieldException(response);
    }
}
