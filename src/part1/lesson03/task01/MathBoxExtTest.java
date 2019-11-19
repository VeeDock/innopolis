package part1.lesson03.task01;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class MathBoxExtTest {

    private MathBoxExt mathBox;

    @Before
    public void setUp() {
        mathBox = new MathBoxExt<>(new Number[]{112431,1231,123.12123,123123});
        System.out.println("MathboxExt before: " + mathBox);
    }

    /**
     * тест добавления объекта Number в MathBox. При попытке добавить тип, отличный от Number, выбрасывается исключение
     */
    @Test
    public void addObject() {
        mathBox.addObject(1111);
        //тут должна выйти ошибка, потому что добавляем строку.
        try{
            mathBox.addObject("lkdsjfds");
            Assert.fail("Object was added :(");
        }
        catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    /**
     * складываем все значения и сверяем с ожидаемым значением.
     */
    @Test
    public void summator() {

        BigDecimal actualSum = mathBox.summator();
        BigDecimal expectedSum = BigDecimal.valueOf(236908.12123);
        Assert.assertEquals(expectedSum, actualSum.setScale(5, BigDecimal.ROUND_HALF_UP)); //округлим до 5 знаков после точки для точного сравнения
    }

    /**
     * тест деления всех элементов набора на определенное число (в данном случае 5).
     */
    @Test
    public void splitter() {
        mathBox.splitter(5);
        System.out.println("Делим  все элементы на 5");
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
        System.out.println("MathboxExt after: " + mathBox);
    }
}