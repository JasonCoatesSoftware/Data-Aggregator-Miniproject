package com.sample.dataaggregator.parser.impl; // <-- Updated to match the impl folder

import com.sample.dataaggregator.model.CarData;
import com.sample.dataaggregator.parser.DataParser;
import com.sample.dataaggregator.parser.DataParserFactory;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CsvParserTest {

    @Test
    void testParseValidCsvFile() throws Exception {
        File testFile = new File("data/dummy.csv");
        DataParser<CarData> parser = DataParserFactory.getParser(testFile.getName());
        List<CarData> results = parser.parse(testFile);
        assertNotNull(results, "Rreturned list should not be null");
        assertEquals(4, results.size(), "There should be exactly 4 records parsed");
        CarData firstRecord = results.get(0);
        assertEquals(1, firstRecord.id());
        assertEquals("Ford", firstRecord.make());
        assertEquals(60000, firstRecord.mileage());
        assertEquals(12000.00, firstRecord.price());
    }
}