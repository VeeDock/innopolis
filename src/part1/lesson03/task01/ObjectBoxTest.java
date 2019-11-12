package part1.lesson03.task01;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ObjectBoxTest {
    private ObjectBox objectBox;

    @Before
    public void setUp() {
        objectBox = new ObjectBox();
        System.out.println("ObjectBox before: " + objectBox.dump());
    }

    @Test
    public void addObject() {
        objectBox.addObject(12312);
        objectBox.addObject("sdlkfjsd");
    }

    @Test
    public void deleteObject() {
        String test = "test test";
        Integer num = 12312312;
        Integer num2 = 1111;
        objectBox.addObject(test);
        objectBox.addObject(num);
        objectBox.addObject(num2);
        objectBox.deleteObject(num);
    }

    @After
    public void after(){
        System.out.println("ObjectBox after: " + objectBox.dump());
    }

}