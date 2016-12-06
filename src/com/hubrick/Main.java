package com.hubrick;

import com.hubrick.report.ReportGenerator;
import com.hubrick.util.ConfigHolder;

import java.nio.file.Paths;

public class Main {

    private static void usage() {
        System.out.println("Usage: java report <SOURCE>");
        System.out.println("\t SOURCE: directory where the SOURCE CSV files are stored. Defaults to \"./data\".");
        System.out.println("Example: java report ./data");
    }

    public static void main(String args[]) {
        // Args validation
        if (args.length < 1) {
            usage();
            return;
        }

        ConfigHolder.baseDir = Paths.get(args[0]);
        ReportGenerator.nPercentileIncomeByDepartment("income-by-department.csv", 50);
        ReportGenerator.nPercentileIncomeByDepartment("income-95-by-department.csv", 95);
        ReportGenerator.medianAgeByDepartment("employee-age-by-department.csv");
        ReportGenerator.averageIncomeByAgeRangesWithFactorOfTen("income-average-by-age-range.csv");
    }
}
