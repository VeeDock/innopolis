package part1.lesson02.task03;

import exceptions.DubNameAgeException;

/**
 *
 */
public interface Sorter {
    /**
     *
     * @param peoples Массив элементов Person для сортировки
     * @return Возвращает время, затраченное на сортировку
     */
    long sort(Person[] peoples);

    /**
     *
     * @param p1 Объект Person
     * @param p2 Объект Person
     * @return Результат совпадения имени и возраста объектов
     */
    default boolean checkDubs(Person p1, Person p2) {
        return p1.getName().equals(p2.getName()) && p1.getAge() == p2.getAge();
    }
}
