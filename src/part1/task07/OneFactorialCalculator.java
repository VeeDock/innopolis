package part1.task07;

import java.math.BigInteger;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Класс для вычисления факториала числа в отдельном потоке.
 */
public class OneFactorialCalculator implements Callable<BigInteger> {
    private int number;

    public OneFactorialCalculator(int num) {
        number = num;
    }

    @Override
    public BigInteger call() {

        BigInteger num = null;
        int from = 0;

        //найдем сначала ближайшее вычисленное значение для запрашиваемого числа, чтобы производить меньше вычислений.
        for (int i = number; i > 1; i--) {
            Future<BigInteger> factorial = FactorialCalculator.factorials.get(i);
            if (factorial != null && factorial.isDone()) {
                try {
                    num = factorial.get();
                    from = i;
                    break;
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        if (num == null) {
            num = BigInteger.valueOf(1);
        }

        for (int i = number; i > from; i--) {
            num = num.multiply(BigInteger.valueOf(i));
        }

        return num;
    }
}
