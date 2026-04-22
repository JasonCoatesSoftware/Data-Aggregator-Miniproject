package com.sample.dataaggregator.parser.impl;

import com.sample.dataaggregator.model.CarData;
import com.sample.dataaggregator.parser.DataParser;
import com.sample.dataaggregator.parser.DataParserFactory;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    @Test
    void testParseValidJsonFile() throws Exception {
        File testFile = new File("data/dummy.json");
        DataParser<CarData> parser = DataParserFactory.getParser(testFile.getName());
        List<CarData> results = parser.parse(testFile);
        assertNotNull(results, "Returned list should not be null");
        assertEquals(2, results.size(), "There should be exactly 2 records parsed");
        CarData firstRecord = results.get(0);
        assertEquals(5, firstRecord.id());
        assertEquals("Honda", firstRecord.make());
        assertEquals("sedan", firstRecord.bodyStyle());
        assertEquals(18500.00, firstRecord.price());
    }
}