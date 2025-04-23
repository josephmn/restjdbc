package com.aws.restjdbc.util;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public abstract class StrictObject {
    @JsonIgnore
    private final Map<String, Object> unknownFields = new HashMap<>();

    @JsonAnySetter
    public void setUnknownField(String key, Object value) {
        unknownFields.put(key, value);
    }

    public Stream<String> getUnknownFields() {
        return unknownFields.keySet().stream();
    }
}
