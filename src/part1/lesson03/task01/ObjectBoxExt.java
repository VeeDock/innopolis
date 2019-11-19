//Задание 3. Доработать классы MathBox и ObjectBox таким образом, чтобы MathBox был наследником ObjectBox. Необходимо сделать такую связь, правильно распределить поля и методы. Функциональность в целом должна сохраниться. При попытке положить Object в MathBox должно создаваться исключение.

package part1.lesson03.task01;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * Класс для хранения коллекции Object.
 */
class ObjectBoxExt<T> {
    public Set<T> objects = new HashSet<>();


    public ObjectBoxExt(T[] objects){
        for (T t : objects) {
            addObject(t);
        }
    }

    public void addObject(T object){
        objects.add(object);
    }

    /**
     *  Удаляет заданный объект из набора.
     * @param obj объект, предназначенный для удаления.
     * @return результат удаления из набора
     */
    public boolean deleteObject(Object obj){
        for (Object o :objects) {
            if (obj.equals(o)) {
                objects.remove(o);
                return true;
            }
        }
        return false;
    }

    /**
     * @return Возвращает набор объектов в виде строки.
     */
    public String dump(){
        if (objects == null) return "[]";
        return objects.toString();
    }
}
