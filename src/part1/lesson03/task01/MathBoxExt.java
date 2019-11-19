//Задание 3. Доработать классы MathBox и ObjectBox таким образом, чтобы MathBox был наследником ObjectBox. Необходимо сделать такую связь, правильно распределить поля и методы. Функциональность в целом должна сохраниться. При попытке положить Object в MathBox должно создаваться исключение.

package part1.lesson03.task01;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Объект, хранящий множество чисел Number. По умолчанию все числа приводятся к формату BigDecimal, как предположительный наивысший возможный вариант числа, содержащегося в массиве Number
 */
public class MathBoxExt<T extends Number> extends ObjectBoxExt {


    /**
     * @param nums массив типов, наследуемых от Number. Предположим, что каких-то нестандартных методов передаваться не будет.
     */
    public MathBoxExt(T[] nums) {
        super(nums);
    }//...constructor


    /**
     *
     * @return сумму всех элементов коллекции. результат возврщает в заданном формате Number.
     */
    public BigDecimal summator() {
        BigDecimal sum = new BigDecimal(0);
        for (Object n : objects) {
            sum = sum.add(BigDecimal.valueOf(((Number)n).doubleValue()));
        }
        return sum;
    }//...summator


    /**
     * Выполняет поочередное деление всех хранящихся элементов в объекте на делитель
     *
     * @param divider делитель в любом формате Number.
     */
    public void splitter(T divider) {
        BigDecimal d = BigDecimal.valueOf(divider.doubleValue());
        Set<T> modifiedNums = new HashSet<>();
        for (Object num :objects) {
            modifiedNums.add((T) BigDecimal.valueOf(((Number)num).doubleValue()).divide(d));
        }
        objects = modifiedNums;
    }//...splitter

    /**
     * Удаляет элемент из коллекции, если он там есть.
     *
     * @param num значение удаляемого элемента
     * @return результат удаления
     */
    public boolean remove(T num) {
        return deleteObject(num);
    }

    public boolean contains(BigDecimal n) {
        for (Object num : objects) if (n.equals(num)) return true;
        return false;
    }

    /**
     * Метод для добавления элементов в набор. 
     * @param object Добавляемый объект
     */
    @Override
    public void addObject(Object object) {
        if (!(object instanceof Number)) throw new ClassCastException("Can't cast class <Object> to <Number>");
        super.addObject(object);
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
