package com.hubrick.model;

import com.hubrick.csv.CsvReader;
import com.hubrick.util.ConfigHolder;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class Department {
    private static List<Department> departments;
    private String name;

    private Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns all departments sorted alphabetically. The first time the method is called, it fetches the data from "departments.csv".
     * @return List of departments
     */
    public static List<Department> findAll() {
        if (departments == null) {
            departments = CsvReader.read(ConfigHolder.baseDir.resolve("departments.csv"))
                                   .sorted((d1, d2) -> d1.get(0).compareToIgnoreCase(d2.get(0)))
                                   .map(row -> new Department(row.get(0)))
                                   .collect(toList());
        }
        return departments;
    }
}
