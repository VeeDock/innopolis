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
public class MathBoxExt {
    private Set<BigDecimal> nums = new HashSet<>();
    private final Set<Class> classes;

    {
        /**
         * набор возможных классов, с которыми оперирует объект
         * возьмем в обработку только 8 основных типов данных, наследуемых от Number
         */
        classes = new HashSet<>(Arrays.asList(Long.class, Byte.class, Integer.class, Short.class, Double.class, Float.class, BigInteger.class, BigDecimal.class));
    }

    public MathBoxExt(Number[] nums) throws UnsupportedNumberTypeException {
        for (int i = 0; i < nums.length; i++) {
            if (!classes.contains(nums[i].getClass())) throw new UnsupportedNumberTypeException();
            this.nums.add(BigDecimal.valueOf(nums[i].doubleValue()));
        }
    }//...constructor

    /**
     * Метод возвращает сумму всех элементов коллекции. результат возврщает в заданном формате Number.
     *
     * @param clazz определяет возвращаемый тип
     * @return
     */
    public <T extends Number> T summator(Class<T> clazz) {
        Optional<BigDecimal> n = nums.stream().reduce((a, b) -> a.add(b));
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
        nums = nums.stream().map(n -> n.divide(d)).collect(Collectors.toSet());
    }//...splitter

    /**
     * Удаляет элемент из коллекции, если он там есть.
     *
     * @param num значение удаляемого элемента
     * @return результат удаления
     */
    public boolean remove(Integer num) {
        BigDecimal n = BigDecimal.valueOf((double) num);
        boolean removed = false;
        Iterator<BigDecimal> iter = nums.iterator();
        while (iter.hasNext()) {
            BigDecimal nextNum = iter.next();
            if (nextNum.equals(n)) {
                nums.remove(nextNum);
                removed = true;
                break;
            }
        }
        return removed;
    }

    public boolean contains(BigDecimal n) {
        for (BigDecimal num :nums) if (n.equals(num)) return true;
        return false;
    }

    @Override
    public String toString() {
        return nums.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(nums);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        MathBoxExt nums2 = (MathBoxExt) obj;
        for (BigDecimal n : nums) if (!nums2.contains(n)) return false;
        return super.equals(obj);
    }


}
