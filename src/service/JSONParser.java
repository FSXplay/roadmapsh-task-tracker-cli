package service;

import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for basic JSON operations including file I/O, 
 * entry splitting, and manual "pretty-print" formatting.
 * This parser is designed for simple JSON manipulation without 
 * external dependencies.
 */
public class JSONParser {

    /**
     * Writes a JSON string to a specified file.
     * * @param filePath The destination path where the file will be saved.
     * @param json     The raw JSON string to write.
     * @param isPretty If true, the JSON will be formatted with indents and newlines 
     * before writing.
     */
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

    /**
     * Reads the entire content of a JSON file and returns it as a single String.
     * * @param filePath The path of the file to read.
     * @return A String containing the file's content.
     * @throws IOException If the file cannot be found or read.
     */
    public static String readJSONAsString(String filePath) throws IOException {
        StringBuilder json = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                json.append(line).append("\n");
            }
        }
        return json.toString();
    }

    /**
     * Splits a JSON array or a list of objects into individual entries based on top-level commas.
     * This method tracks brackets and braces to ensure it only splits at the root level 
     * and ignores commas inside nested objects or strings.
     * * @param json The JSON string to be split.
     * @return An array of Strings, where each element is a separate JSON entry.
     */
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

    /**
     * Converts a condensed or messy JSON string into a structured, 
     * human-readable format with proper indentation.
     * * @param json The raw JSON string to format.
     * @return A formatted "pretty" JSON string.
     */
    private static String formatToPrettyJson(String json) {
        StringBuilder pretty = new StringBuilder();
        int indentLevel = 0;
        boolean inQuotes = false;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
                pretty.append(c);
                continue;
            }

            if (inQuotes) {
                pretty.append(c);
                continue;
            }

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
                    // Skip existing whitespace
                }
                default -> pretty.append(c);
            }
        }
        return pretty.toString();
    }

    /**
     * Appends the appropriate number of indentation spaces to the StringBuilder.
     * * @param sb    The StringBuilder to append to.
     * @param level The current indentation level (depth).
     */
    private static void addIndent(StringBuilder sb, int level) {
        for (int i = 0; i < level; i++) {
            sb.append("  "); // Two spaces per indent level
        }
    }
}