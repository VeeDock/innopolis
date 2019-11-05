package exceptions;

import part1.lesson02.task03.Person;

/**
 * Класс пользовательского исключения для выбрасывания при совпадении имени и возраста
 */
public class DubNameAgeException extends Exception {
    private Person p1, p2;
    public DubNameAgeException() {
        super("Недопустимое совпадение имени и возраста двух объектов Person в одном массиве.");
    }
    public DubNameAgeException(Person p1, Person p2){
        super(String.format("Недопустимое равенство двух объектов. Объект {%s} равен {%s}.", p1, p2));
        this.p1 = p1;
        this.p2 = p2;

    }
}
