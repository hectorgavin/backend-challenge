package com.hubrick.csv;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class CsvWriter {
    private static final String DEFAULT_SEPARATOR = ",";
    private CsvWriter() {}

    /**
     * Formats and writes data to a CSV file.
     * @param target Path to the target file
     * @param separator Column Separator character
     */
    public static void write(Path target, List<List<String>> data, String separator) {
        try (BufferedWriter writer = Files.newBufferedWriter(target)) {
            String csv = data.stream()
                             .map(row -> String.join(separator, row))
                             .collect(Collectors.joining("\n"));
            writer.write(csv);
        }
        catch(IOException e) {
            System.err.println("There was an error writing to "+ target);
        }
    }

    public static void write(Path target, List<List<String>> data) {
        write(target, data, DEFAULT_SEPARATOR);
    }
}
