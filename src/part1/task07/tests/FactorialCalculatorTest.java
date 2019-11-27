package part1.task07.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import part1.task07.FactorialCalculator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FactorialCalculatorTest {
    private List<Integer> numbers = new ArrayList<>();
    private List<Integer> numbers2 = new ArrayList<>();

    @Before
    public void numInit() {
        numbers.add(1226);
        numbers.add(4311);
        numbers.add(81443);

        //числа для проверки кэшированных вычислений
        numbers2.add(1256);
        numbers2.add(4311);
        numbers2.add(81900);

    }

    @Test
    public void getFactorials() {
        try {
            long start = new Date().getTime();
            List<BigInteger> factorials = FactorialCalculator.getFactorials(numbers);
            long end = new Date().getTime();
            System.out.println(factorials.toString());
            System.out.println("Время, потраченное на вычисление факториалов: " + (end-start) + " ms.");


            start = new Date().getTime();
            List<BigInteger> factorials2 = FactorialCalculator.getFactorials(numbers2);
            end = new Date().getTime();
            System.out.println(factorials2.toString());
            System.out.println("Время, потраченное на вычисление факториалов с использованием кэшированных вычислений: " + (end-start) + " ms.");

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Assert.fail("Что-то пошло не так");
        }

    }
}