package com.sample.dataaggregator.parser;

import com.sample.dataaggregator.parser.impl.CsvParser;
import com.sample.dataaggregator.parser.impl.JsonParser;
import com.sample.dataaggregator.model.CarData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class DataParserFactory {
    private static final Function<String[], CarData> CAR_MAPPER = columns -> {
        if (columns.length != 7) return null; 

        try {
            return new CarData(
                Integer.parseInt(columns[0].trim()),
                LocalDateTime.parse(columns[1].trim(), DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                columns[2].trim(),
                columns[3].trim(),
                Integer.parseInt(columns[4].trim()),
                columns[5].trim(),
                Double.parseDouble(columns[6].trim())
            );
        } catch (Exception e) {
            return null; 
        }
    };

    public static DataParser<CarData> getParser(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            throw new IllegalArgumentException("Invalid file name: no extension found.");
        }

        String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();

        return switch (extension) {
            case ".csv" -> new CsvParser<>(CAR_MAPPER); 
            case ".json" -> new JsonParser();
            default -> throw new IllegalArgumentException("Unsupported file format: " + extension);
        };
    }
}