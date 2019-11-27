//ДЗ_7
//Дан массив случайных чисел. Написать программу для вычисления факториалов всех элементов массива. Использовать пул потоков для решения задачи.
//Особенности выполнения:
//Для данного примера использовать рекурсию - не очень хороший вариант, т.к. происходит большое выделение памяти, очень вероятен StackOverFlow. Лучше перемножать числа в простом цикле при этом создавать объект типа BigInteger
//По сути, есть несколько способа решения задания:
//1) распараллеливать вычисление факториала для одного числа
//2) распараллеливать вычисления для разных чисел
//3) комбинированный
//При чем вычислив факториал для одного числа, можно запомнить эти данные и использовать их для вычисления другого, что будет гораздо быстрее

package part1.task07;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Класс для подсчета факториалов чисел.
 */
public class FactorialCalculator {
    /**
     * Поле для хранения подсчитанных факториалов
     */
    public static Map<Integer, Future<BigInteger>> factorials = new TreeMap<>();

    /**
     * Получение факториалов всех элементов массива
     *
     * @param numbers список значений, для которых будут вычислены факториалы.
     * @return возвращает список подсчитанных значений факториалов
     */
    public static List<BigInteger> getFactorials(List<Integer> numbers) throws ExecutionException, InterruptedException {

        ExecutorService service = Executors.newCachedThreadPool();
        List<BigInteger> results = new ArrayList<>();

        //подготовка потоков вычислений
        for (Integer num : numbers) {
            if (factorials.containsKey(num)) {
                continue;
            }
            factorials.put(num, service.submit(new OneFactorialCalculator(num)));
        }

        //запуск потоков вычислений и сбор результатов
        for (Integer num : numbers) {
            try {
                results.add(factorials.get(num).get());
            } catch (ExecutionException | InterruptedException e) {
                results.add(null);
            }
        }

        return results;
    }
}
