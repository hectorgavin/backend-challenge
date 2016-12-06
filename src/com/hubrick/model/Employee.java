package com.hubrick.model;

import com.hubrick.csv.CsvReader;
import com.hubrick.exception.SexNotFoundException;
import com.hubrick.util.ConfigHolder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

public class Employee {
    private static List<Employee> employees;
    private static final Employee INVALID_EMPLOYEE = new Employee();
    private String name;
    private Integer age;
    private Sex sex;
    private BigDecimal income;
    private Department department;

    private Employee() {}

    private Employee(
            String name,
            int age,
            Sex sex,
            BigDecimal income,
            Department department) throws Exception {

        this.name = name;
        this.age = age;
        this.sex = sex;
        this.income = income;
        this.department = department;
    }

    public Integer getAge() {
        return age;
    }

    public BigDecimal getIncome() {
        return income;
    }

    private Department getDepartment() {
        return department;
    }

    /**
     * Returns all employees. The first time the method is called, it fetches the data from "employees.csv" and "ages.csv".
     * @return List of employees
     */
    public static List<Employee> getEmployees() {
        if (employees == null) {
            Map<String, Integer> employeeAges =
                CsvReader.stream(ConfigHolder.baseDir.resolve("ages.csv"))
                         .collect(Collectors.toMap(
                            row -> row.get(0),
                            row -> Integer.valueOf(row.get(1))
                         ));

            setEmployees(
                CsvReader.stream(ConfigHolder.baseDir.resolve("employees.csv"))
                         .map(row -> {
                             try {
                                 return new Employee(
                                     row.get(1),
                                     employeeAges.get(row.get(1)),
                                     Sex.findByShortName(row.get(2)),
                                     new BigDecimal(row.get(3)),
                                     Department.findAll().get(Integer.valueOf(row.get(0)) - 1)
                                 );
                             }
                             catch (SexNotFoundException e) {
                                 System.err.println("Row "+ row +" has an invalid sex");
                             }
                             catch (NumberFormatException e) {
                                 System.err.println("Row "+ row +" has an invalid income");
                             }
                             catch (IndexOutOfBoundsException e) {
                                 System.err.println("Row "+ row +" has an invalid department");
                             }
                             catch (Exception e) {
                                 System.err.println("Row "+ row +" is invalid");
                             }
                             return INVALID_EMPLOYEE;
                         })
                         .filter(Employee::isValid)
                         .collect(toList()));
        }
        return employees;
    }

    private static void setEmployees(List<Employee> theEmployees) {
        employees = theEmployees;
    }

    private boolean isValid() {
        return name != null &&
               age > 0 &&
               sex != null &&
               income != null &&
               department != null;
    }

    public static List<Employee> findAllWithAgeBetween(int minAge, int maxAge) {
        return getEmployees().stream()
                             .filter(e -> e.getAge() >= minAge && e.getAge() <= maxAge)
                             .collect(toList());
    }

    public static Map<Department, List<Employee>> groupAllByDepartment() {
        return getEmployees().stream()
                             .collect(groupingBy(
                                Employee::getDepartment,
                                mapping(identity(), toList())
                             ));
    }
}
