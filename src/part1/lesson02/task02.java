package part1.lesson02;

import java.util.Random;

public class task02 {
    public static void main(String[] args) {
        int[] n = new int[400];
        Random rnd = new Random();
        for (int i = 0; i < n.length; i++) {
            n[i] = rnd.nextInt();

            Double sqrt = null;
            try {
                if (n[i]<0) throw new ArithmeticException();
                sqrt = Math.sqrt(n[i]);
                System.out.println(sqrt);

                if (Math.floor(sqrt) == sqrt) System.out.println(n[i]);
            } catch (ArithmeticException e) {
                System.out.println("value is negative");
            }

        }
    }
}
