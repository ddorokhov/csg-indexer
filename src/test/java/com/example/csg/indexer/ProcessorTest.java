package com.example.csg.indexer;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProcessorTest {

    @Test
    void testValidFileProcessing() throws IOException {

        var tempFile = Files.createTempFile("testfile", ".txt");
        Files.writeString(tempFile, "Hello world from Dmitriy");

        Processor processor = new Processor();
        List<Rules> rules = List.of(Rules.RULE_UPPER_CASE_WORD, Rules.WORD_LONGER_FIVE);

        Map<String, FileResult> resultMap = processor.process(
                new String[]{tempFile.toString()}, rules
        );


        assertTrue(resultMap.containsKey(tempFile.toString()));
        FileResult result = resultMap.get(tempFile.toString());

        assertNull(result.errorMessage(), "There should be no error");


        int upperCase = result.result().getOrDefault(Rules.RULE_UPPER_CASE_WORD, 0);
        int longerThanFive = result.result().getOrDefault(Rules.WORD_LONGER_FIVE, 0);

        assertEquals(2, upperCase);
        assertEquals(1, longerThanFive);
    }

    @Test
    void testMissingFile() {
        String missingFile = "non_existing";
        Processor processor = new Processor();


        Map<String, FileResult> resultMap = processor.process(
                new String[]{missingFile},
                List.of(Rules.RULE_UPPER_CASE_WORD)
        );

        
        FileResult result = resultMap.get(missingFile);
        assertNotNull(result);
        assertNotNull(result.errorMessage());
        assertTrue(result.errorMessage().toLowerCase().contains("error"));
    }
}