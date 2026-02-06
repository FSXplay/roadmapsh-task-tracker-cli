package service.tests.testJSONParser;

import java.io.IOException;
import service.JSONParser;

public class TestJSONParser {
    public static void main(String[] args) {
        testJSONReader();
    }

    private static void testJSONReader() {
        // Test the JSON reader
        try {
            String json = JSONParser.readJSONAsString("src/service/tests/testJSONParser/testTasks.json");
            System.out.println("File Content:\n" + json);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
