package part1.lesson02.task03;

import exceptions.DubNameAgeException;

import java.util.Arrays;
import java.util.Date;

/**
 * Класс, реализующий интерфейс Sorter. Сортирует переданный массив методом пузырька
 */
public class Sorter1 implements Sorter{
    @Override
    public long sort(Person[] peoples) {
        long start = new Date().getTime();
        //System.out.println(Arrays.toString(peoples));
        boolean sorted = false; //сортируем пузырьком
        Person temp;
        //сортируем мужчин сначала
        while (!sorted){
            sorted = true;
            for(int i = 0; i<peoples.length-1; i++){
                if (peoples[i].getSex() == Sex.WOMAN && peoples[i+1].getSex() == Sex.MAN){
                    temp = peoples[i];
                    peoples[i] = peoples[i+1];
                    peoples[i+1] = temp;
                    sorted = false;
                }
            }
        }


        sorted = false;
        while(!sorted){
            sorted = true;
            for (int i = 0; i < peoples.length-1; i++) {
                if (peoples[i].getAge()<peoples[i+1].getAge() && peoples[i].getSex() == peoples[i+1].getSex()){
                    temp = peoples[i];
                    peoples[i] = peoples[i+1];
                    peoples[i+1] = temp;
                    sorted = false;
                }
            }
        }

        sorted = false;
        while(!sorted){
            sorted = true;
            for (int i = 0; i < peoples.length-1; i++) {
                if(peoples[i].getAge() == peoples[i+1].getAge()){
                    try{
                        String n1 = peoples[i].getName();
                        String n2 = peoples[i+1].getName();
                        if (n1.equals(n2)) throw new DubNameAgeException(peoples[i], peoples[i+1]);
                        if (n1.compareTo(n2)>0){
                            temp = peoples[i];
                            peoples[i] = peoples[i+1];
                            peoples[i+1] = temp;
                            sorted = false;
                        }
                    }catch (DubNameAgeException e){
                        //System.out.println(String.format("%s. Индексы элементов в массиве: %s, %s", e.getMessage(),i,i+1));
                        //удаление лучше делать в другом месте. но, возможно, в этом нет необходимости. просто информируем
                    }
                }
            }
        }

        //System.out.println(Arrays.toString(peoples));

        return new Date().getTime()-start;
    }

}