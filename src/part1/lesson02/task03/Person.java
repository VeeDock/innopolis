package part1.lesson02.task03;

public class Person{
    int age;
    private Sex sex;
    private String name;

    public Person(String name, int age, String sex){
        this.age = age<0||age>100?0:age;
        this.sex = sex.equals("m")?Sex.MAN:Sex.WOMAN;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(name + "-" + sex + "-" + age);
    }
}
