package service;

import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

public class JSONParser {
    public static void writeJSONFile(String filePath, String json, boolean isPretty) {
        json = json.trim();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            if (isPretty)
                json = formatToPrettyJson(json);
            bufferedWriter.write(json);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static String readJSONAsString(String filePath) {
        StringBuilder json = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                json.append(line + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return json.toString();
    }

    public static String[] splitJsonEntries(String json) {
        List<String> entries = new ArrayList<>();
        int bracketCount = 0, braceCount = 0;
        boolean inQuotes = false;
        StringBuilder entry = new StringBuilder();

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            }

            if (!inQuotes) {
                if (c == '{') {
                    braceCount++;
                } else if (c == '}') {
                    braceCount--;
                } else if (c == '[') {
                    bracketCount++;
                } else if (c == ']') {
                    bracketCount--;
                }
            }

            if (c == ',' && !inQuotes && braceCount == 0 && bracketCount == 0) {
                entries.add(entry.toString().trim());
                entry.setLength(0);
            } else {
                entry.append(c);
            }
        }

        if (entry.length() > 0) {
            entries.add(entry.toString().trim());
        }

        return entries.toArray(new String[0]);
    }

    private static String formatToPrettyJson(String json) {
        StringBuilder pretty = new StringBuilder();
        int indentLevel = 0;
        boolean inQuotes = false;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);

            // Handle quotes to avoid formatting inside strings
            if (c == '"') {
                inQuotes = !inQuotes;
                pretty.append(c);
                continue;
            }

            if (inQuotes) {
                pretty.append(c);
                continue;
            }

            // Logic based on characters
            switch (c) {
                case '{', '[' -> {
                    pretty.append(c).append("\n");
                    indentLevel++;
                    addIndent(pretty, indentLevel);
                }
                case '}', ']' -> {
                    pretty.append("\n");
                    indentLevel--;
                    addIndent(pretty, indentLevel);
                    pretty.append(c);
                }
                case ',' -> {
                    pretty.append(c).append("\n");
                    addIndent(pretty, indentLevel);
                }
                case ':' -> pretty.append(": ");
                case ' ', '\n', '\r', '\t' -> {
                    // Skip existing whitespace in the raw string
                }
                default -> pretty.append(c);
            }
        }
        return pretty.toString();
    }

    private static void addIndent(StringBuilder sb, int level) {
        for (int i = 0; i < level; i++) {
            sb.append("  "); // Two spaces per indent level
        }
    }
}
