package com.sample.dataaggregator.parser.impl;

import com.sample.dataaggregator.parser.DataParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CsvParser<T> implements DataParser<T> {

    private final Function<String[], T> rowMapper;

    public CsvParser(Function<String[], T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    @Override
    public List<T> parse(File file) throws Exception {
        List<T> payloads = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] columns = line.split(",");
                
                T mappedObject = rowMapper.apply(columns);
                if (mappedObject != null) {
                    payloads.add(mappedObject);
                }
            }
        }
        return payloads;
    }
}