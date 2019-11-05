package part1.lesson02.task03;

import java.util.Arrays;
import java.util.Random;

/**
 * Задача №3. Основной класс решения. Генерация Person и их сортировка
 * @author VDV
 * @version 0.1
 */
public class task03 {
    static Person[] peoples = new Person[10000];
    static String[][] nameScope;

    static {
        nameScope = new String[][]{
                {"Вася", "m"},
                {"Иван", "m"},
                {"Борис", "m"},
                {"Агния", "f"},
                {"Алевтина", "f"},
                {"Жизель", "f"}
        };
    }

    public static void main(String[] args) {
        fillPersons();
        Sorter sort1 = new Sorter1();
        long sortTime1 = sort1.sort(peoples);
        //System.out.println(Arrays.toString(peoples));
        System.out.println("Время, потраченное на сортировку массива методом пузырька: " + sortTime1/1000. + " сек.");

        fillPersons();
        Sorter sort2 = new Sorter2();
        long sortTime2 = sort2.sort(peoples);
        //System.out.println(Arrays.toString(peoples));
        System.out.println("Время, потраченное на сортировку встроенными методами класса Arrays: " + sortTime2/1000. + " сек.");
    }

    /**
     * Заполняем массив peoples случайными чуваками
     */
    public static void fillPersons(){
        Random rnd = new Random();
        for (int i = 0; i < peoples.length; i++) {
            int r = rnd.nextInt(nameScope.length);
            peoples[i] = new Person(nameScope[r][0],rnd.nextInt(101), nameScope[r][1]);
        }
    }

}
