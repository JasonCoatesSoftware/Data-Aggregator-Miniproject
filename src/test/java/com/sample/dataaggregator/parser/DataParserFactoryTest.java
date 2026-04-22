package com.sample.dataaggregator.parser;

import com.sample.dataaggregator.model.CarData;
import com.sample.dataaggregator.parser.impl.CsvParser;
import com.sample.dataaggregator.parser.impl.JsonParser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DataParserFactoryTest {

    @Test
    void testGetCsvParser() {
        DataParser<CarData> parser = DataParserFactory.getParser("data/my_metrics.csv");
        assertTrue(parser instanceof CsvParser, "Factory should return a CsvParser for .csv files");
    }

    @Test
    void testGetJsonParser() {
        DataParser<CarData> parser = DataParserFactory.getParser("reports/daily_log.json");
        assertTrue(parser instanceof JsonParser, "Factory should return a JsonParser for .json files");
    }

    @Test
    void testUnsupportedExtensionThrowsException() {
        // Assert that asking for an XML file throws our specific exception
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            DataParserFactory.getParser("data/config.xml");
        });
        
        assertTrue(exception.getMessage().contains("Unsupported file format: .xml"));
    }
}