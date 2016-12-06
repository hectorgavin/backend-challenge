package com.hubrick.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class CsvReader {
    private static final String DEFAULT_SEPARATOR = ",";
    private CsvReader() {}

    /**
     * Reads and parses a CSV file and returns a Stream of its rows.
     * @param source Path to the source file
     * @param separator Column Separator character
     * @return Stream of CSV rows
     */
    public static Stream<List<String>> stream(Path source, String separator) {
        try {
            Stream<String> lines = Files.lines(source);
            return lines.map(line -> Arrays.asList(line.split(separator)));
        }
        catch (IOException e) {
            System.err.println("There was an error reading from "+ source);
            return Stream.empty();
        }
    }

    public static Stream<List<String>> stream(Path path) {
        return stream(path, DEFAULT_SEPARATOR);
    }
}
