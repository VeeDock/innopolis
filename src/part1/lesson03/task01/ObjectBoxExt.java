//Задание 3. Доработать классы MathBox и ObjectBox таким образом, чтобы MathBox был наследником ObjectBox. Необходимо сделать такую связь, правильно распределить поля и методы. Функциональность в целом должна сохраниться. При попытке положить Object в MathBox должно создаваться исключение.

package part1.lesson03.task01;

import java.util.HashSet;
import java.util.Set;


/**
 * Класс для хранения коллекции Object.
 */
class ObjectBoxExt {
    public Set objects = null;

    ObjectBoxExt() {
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

    /**
     * Возвращает набор объектов в виде строки.
     * @return
     */
    public String dump(){
        if (objects == null) return "[]";
        return objects.toString();
    }
}
