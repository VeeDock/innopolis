package part1.lesson02.task03;

public enum Sex {
    MAN("MAN"),
    WOMAN("WOMAN");
    private String sex;

    Sex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return sex;
    }
}
