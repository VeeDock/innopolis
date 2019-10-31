package part1.lesson02;

import java.io.FileNotFoundException;

public class task01 {
    public static void main(String[] args) {
        try {
            System.out.println("Hello, world!");
            String str = null;
            //str.equals("");
            int[] nums = new int[11];
            System.out.println(nums[12]);
            throw new FileNotFoundException("fof");
        } catch (NullPointerException e) {
            System.out.println("NPE here");
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("ArrayIndexOutOfBoundsException here");
        } catch (FileNotFoundException e) {
            System.out.println("file not found exception");
        }
    }
}
