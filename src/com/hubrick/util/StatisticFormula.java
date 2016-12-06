package com.hubrick.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class StatisticFormula {

    private StatisticFormula() {}

    /**
     * Percentile calculation based on https://en.wikipedia.org/wiki/Percentile#The_Nearest_Rank_method
     */
    public static BigDecimal percentileBigDecimal(int percentile, List<BigDecimal> numbers) {
        List<BigDecimal> sortedNumbers = numbers.stream().sorted().collect(toList());
        int n = (int) Math.round(numbers.size() * percentile / 100.0);
        return sortedNumbers.get(n - 1);
    }

    public static Integer percentile(int percentile, List<Integer> numbers) {
        List<Integer> sortedNumbers = numbers.stream().sorted().collect(toList());
        int n = (int) Math.round(numbers.size() * percentile / 100.0);
        return sortedNumbers.get(n - 1);
    }

    public static Integer median(List<Integer> numbers) {
        return percentile(50, numbers);
    }

    public static BigDecimal averageBigDecimal(List<BigDecimal> numbers) {
        return numbers.stream()
                      .reduce(BigDecimal::add)
                      .get()
                      .divide(new BigDecimal(numbers.size()), RoundingMode.HALF_EVEN);
    }
}
