package part1.lesson03.task01;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class MathBoxTest {
    private MathBox mathBox;

    @Before
    public void setUp() throws UnsupportedNumberTypeException {
        mathBox = new MathBox(new Number[]{112431,1231,123.12123,123123});
        System.out.println("Mathbox before: " + mathBox);
    }

    @Test
    public void summator() {
        BigDecimal expectedSum = mathBox.summator(BigDecimal.class);
        BigDecimal actualSum = BigDecimal.valueOf(236908.12123);
        Assert.assertEquals(expectedSum,actualSum);
    }

    @Test
    public void splitter() {
        mathBox.splitter(5);
        System.out.println(mathBox);
    }

    @Test
    public void remove() {
        int num = 1231;
        System.out.println("removing " + num);
        mathBox.remove(num);
        assertFalse(mathBox.contains(BigDecimal.valueOf(num)));
    }

    @After
    public void after(){
        System.out.println("Mathbox after: " + mathBox);
    }
}