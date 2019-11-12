//Задание 3. Доработать классы MathBox и ObjectBox таким образом, чтобы MathBox был наследником ObjectBox. Необходимо сделать такую связь, правильно распределить поля и методы. Функциональность в целом должна сохраниться. При попытке положить Object в MathBox должно создаваться исключение.

package part1.lesson03.task01;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Объект, хранящий множество чисел Number. По умолчанию все числа приводятся к формату BigDecimal, как предположительный наивысший возможный вариант числа, содержащегося в массиве Number
 *
 * @author VDV
 */
public class MathBoxExt extends ObjectBoxExt {

    private final Set<Class> classes;

    {
        /**
         * набор возможных классов, с которыми оперирует объект
         * возьмем в обработку только 8 основных типов данных, наследуемых от Number
         */
        classes = new HashSet<>(Arrays.asList(Long.class, Byte.class, Integer.class, Short.class, Double.class, Float.class, BigInteger.class, BigDecimal.class));
    }

    public MathBoxExt(Number[] nums) throws UnsupportedNumberTypeException {
        objects = new HashSet<BigDecimal>();
        for (Number num : nums) {
            if (!classes.contains(num.getClass())) throw new UnsupportedNumberTypeException();
            addObject(num);
        }

    }//...constructor

    /**
     * Вспомогательный метод, преобразующий Object в BigDecimal
     * @param v
     * @return
     */
    BigDecimal val(Object v){
        return new BigDecimal(((Number)v).doubleValue());
    }

    /**
     * Метод возвращает сумму всех элементов коллекции. результат возврщает в заданном формате Number.
     *
     * @return
     */
    public <T extends Number> T summator() {
        Optional n = objects.stream().reduce((a, b) -> val(a).add(val(b)));
        return (T) n.orElse(BigDecimal.valueOf(0));
    }




    /**
     * Выполняет поочередное деление всех хранящихся элементов в объекте на делитель
     *
     * @param divider делитель в любом формате Number.
     * @param <T>     тип делителя
     */
    public <T extends Number> void splitter(T divider) {
        BigDecimal d = BigDecimal.valueOf(divider.doubleValue());
        objects = (Set) objects.stream().map(n -> (val(n)).divide(d)).collect(Collectors.toSet());
    }//...splitter

    /**
     * Удаляет элемент из коллекции, если он там есть.
     *
     * @param num значение удаляемого элемента
     * @return результат удаления
     */
    public boolean remove(Integer num) {
        return deleteObject(num);
    }

    public boolean contains(BigDecimal n) {
        for (Object num :objects) if (n.equals(num)) return true;
        return false;
    }

    @Override
    public String toString() {
        return dump();
    }

    @Override
    public int hashCode() {
        return Objects.hash(objects);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        MathBoxExt nums2 = (MathBoxExt) obj;
        for (Object n : objects) if (!nums2.contains((BigDecimal) n)) return false;
        return super.equals(obj);
    }


}
