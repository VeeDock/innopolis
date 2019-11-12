//Задание 2. Создать класс ObjectBox, который будет хранить коллекцию Object.
//        ◦ У класса должен быть метод addObject, добавляющий объект в коллекцию.
//        ◦ У класса должен быть метод deleteObject, проверяющий наличие объекта в коллекции и при наличии удаляющий его.
//        ◦ Должен быть метод dump, выводящий содержимое коллекции в строку.

package part1.lesson03.task01;

import java.util.HashSet;
import java.util.Set;


/**
 * Класс для хранения коллекции Object. 
 */
class ObjectBox {
    private Set objects = null;

    ObjectBox() {
        objects = new HashSet();
    }

    void addObject(Object object){
        objects.add(object);
    }

    /**
     *  Удаляет заданный объект из набора.
     * @param obj объект, предназначенный для удаления.
     * @return результат удаления из набора
     */
    boolean deleteObject(Object obj){
        for (Object o :objects) {
            if (obj.equals(o)) {
                objects.remove(o);
                return true;
            }
        }
        return false;
    }

    public String dump(){
        if (objects == null) return "[]";
        return objects.toString();
    }
}
