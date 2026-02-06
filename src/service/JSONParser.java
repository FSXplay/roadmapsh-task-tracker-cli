package service;

import model.Task.Task;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class JSONParser {
    public static String readJSONAsString(String filePath) throws IOException {
        StringBuilder json = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                json.append(line);
            }
        }
        return json.toString();
    }
}
