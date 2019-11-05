package part1.lesson02.task03;

import exceptions.DubNameAgeException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

/**
 * Класс для сортировки стандартными средствами класса Arrays
 * @author VDV
 */
public class Sorter2 implements Sorter{
    @Override
    public long sort(Person[] peoples) {
        long start = new Date().getTime();
        Arrays.sort(peoples, Comparator.comparing(Person::getSex).thenComparing((p1,p2)->{
            int age1 = p1.getAge(), age2 = p2.getAge();
            try{
                if (age1 == age2 && p1.getName().equals(p2.getName())) throw new DubNameAgeException(p1,p2);
            }catch (DubNameAgeException e){
                //System.out.println(e.getMessage());
            }
            finally {
                return age2-age1;
            }
        }).thenComparing(Person::getName));
        return new Date().getTime()-start;
    }
}