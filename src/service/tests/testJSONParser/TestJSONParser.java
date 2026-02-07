package service.tests.testJSONParser;

import service.JSONParser;

public class TestJSONParser {
    private static final String FILE_PATH = "src/service/tests/testJSONParser/testTasks.json";

    public static void main(String[] args) {
        // test_writeJSONFile();
        test_splitJSONEntries();
    }

    private static void test_readJSONAsString() {
        String json = JSONParser.readJSONAsString(FILE_PATH);
        System.out.println("File Content:\n" + json);
    }

    private static void test_writeJSONFile() {
        String json = "{\"10\":{\"description\":\"Buy groceries: milk, eggs, and \\\"bread\\\"\",\"status\":\"todo\",\"createdAt\":\"2026-02-07T12:00:00\",\"updatedAt\":\"2026-02-07T12:00:00\"},\"25\":{\"description\":\"Fix bug #42 (Priority: High); check logs.\",\"status\":\"in-progress\",\"createdAt\":\"2026-02-05T08:00:00\",\"updatedAt\":\"2026-02-07T14:00:00\"},\"99\":{\"description\":\"\",\"status\":\"done\",\"createdAt\":\"2026-01-01T00:00:00\",\"updatedAt\":\"2026-01-01T01:00:00\"}}";
        JSONParser.writeJSONFile(FILE_PATH, json, true);
    }

    private static void test_splitJSONEntries() {
        String rawJSON = JSONParser.readJSONAsString(FILE_PATH);
        String[] jsonEntries = JSONParser.splitJsonEntries(rawJSON);
        System.out.println("The first JSON entry:");
        System.out.println(jsonEntries[1]);
    }
}
