package com.hubrick.report;

import com.hubrick.csv.CsvWriter;
import com.hubrick.model.Department;
import com.hubrick.model.Employee;
import com.hubrick.util.ConfigHolder;
import com.hubrick.util.StatisticFormula;

import java.math.BigDecimal;
import java.util.*;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

public class ReportGenerator {
    private ReportGenerator() { }

    private static Map<Department, List<Employee>> findAllEmployeesByDepartment() {
        return Employee.findAll().stream()
                                 .collect(groupingBy(Employee::getDepartment, mapping(identity(), toList())));
    }

    public static void nPercentileIncomeByDepartment(String filename, int percentile) {
        Map<Department, List<Employee>> employeesByDepartment = findAllEmployeesByDepartment();

        List<List<String>> report = new ArrayList<>();
        report.add(Arrays.asList("department", percentile == 50 ? "median" : percentile+"percentile"));

        for(Map.Entry<Department, List<Employee>> entry : employeesByDepartment.entrySet()) {
            List<BigDecimal> incomes = entry.getValue().stream().map(Employee::getIncome).collect(toList());
            report.add(Arrays.asList(entry.getKey().getName(), StatisticFormula.percentileBigDecimal(percentile, incomes).toString()));
        }

        CsvWriter.write(ConfigHolder.targetDir.resolve(filename), report);
    }

    public static void medianAgeByDepartment(String filename) {
        Map<Department, List<Employee>> employeesByDepartment = findAllEmployeesByDepartment();

        List<List<String>> report = new ArrayList<>();
        report.add(Arrays.asList("department", "median"));

        for(Map.Entry<Department, List<Employee>> entry : employeesByDepartment.entrySet()) {
            List<Integer> ages = entry.getValue().stream().map(Employee::getAge).collect(toList());
            report.add(Arrays.asList(entry.getKey().getName(), StatisticFormula.median(ages).toString()));
        }

        CsvWriter.write(ConfigHolder.targetDir.resolve(filename), report);
    }

    public static void averageIncomeByAgeRangesWithFactorOfTen(String filename) {
        List<Employee> employees = Employee.findAll();
        Optional<Integer> minAge = employees.stream().map(Employee::getAge).min(Integer::compare);
        Optional<Integer> maxAge = employees.stream().map(Employee::getAge).max(Integer::compare);
        if (!minAge.isPresent() || !maxAge.isPresent()) {
            return;
        }

        List<List<String>> report = new ArrayList<>();
        report.add(Arrays.asList("ageRange", "average"));

        BigDecimal average;
        for(Integer age = minAge.get()+10; age <= maxAge.get(); age+=10) {
            average = StatisticFormula.averageBigDecimal(
                Employee.findAllWithAgeUnder(age).stream().map(Employee::getIncome).collect(toList())
            );
            report.add(Arrays.asList(age + "-" + (age+10), average.toString()));
        }

        CsvWriter.write(ConfigHolder.targetDir.resolve(filename), report);
    }
}
