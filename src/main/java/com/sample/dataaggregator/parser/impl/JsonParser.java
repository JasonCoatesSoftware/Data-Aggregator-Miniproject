package com.sample.dataaggregator.parser.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sample.dataaggregator.model.CarData;
import com.sample.dataaggregator.parser.DataParser;
import java.io.File;
import java.util.List;

public class JsonParser implements DataParser<CarData> {

    private final ObjectMapper mapper;

    public JsonParser() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public List<CarData> parse(File file) throws Exception {
        return mapper.readValue(file, new TypeReference<List<CarData>>() {});
    }
}