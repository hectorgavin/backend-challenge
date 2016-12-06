package com.hubrick.test;

import com.hubrick.util.StatisticFormula;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class StatisticFormulaTest {

    @Test
    public void averageBDWhole() {
        List<BigDecimal> numbers = Stream.of(1, 2, 3, 4, 5).map(BigDecimal::new).collect(toList());
        BigDecimal result = StatisticFormula.averageBigDecimal(numbers);

        assertEquals(result, new BigDecimal("3.00"));
    }

    @Test
    public void averageBDDecimal() {
        List<BigDecimal> numbers = Stream.of(1, 4).map(BigDecimal::new).collect(toList());
        BigDecimal result = StatisticFormula.averageBigDecimal(numbers);

        assertEquals(result, new BigDecimal("2.50"));
    }

    @Test
    // Based on https://en.wikipedia.org/wiki/Percentile#Worked_examples_of_the_Nearest_Rank_method
    public void percentileBD() {
        List<BigDecimal> numbers = Stream.of("15.00", "20.00", "35.00", "40.00", "50.00").map(BigDecimal::new).collect(toList());

        assertEquals(StatisticFormula.percentileBigDecimal(30, numbers), new BigDecimal("20.00"));
        assertEquals(StatisticFormula.percentileBigDecimal(40, numbers), new BigDecimal("20.00"));
        assertEquals(StatisticFormula.percentileBigDecimal(50, numbers), new BigDecimal("35.00"));
        assertEquals(StatisticFormula.percentileBigDecimal(100, numbers), new BigDecimal("50.00"));
    }

    @Test
    // Based on https://en.wikipedia.org/wiki/Percentile#Worked_examples_of_the_Nearest_Rank_method
    public void percentile() {
        List<Integer> numbers = Arrays.asList(3, 6, 7, 8, 8, 10, 13, 15, 16, 20);

        assertEquals(StatisticFormula.percentile(25, numbers), new Integer(7));
        assertEquals(StatisticFormula.percentile(50, numbers), new Integer(8));
        assertEquals(StatisticFormula.percentile(75, numbers), new Integer(15));
        assertEquals(StatisticFormula.percentile(100, numbers), new Integer(20));
    }
}
