package part1.lesson03.task01;

class UnsupportedNumberTypeException extends Throwable {
    UnsupportedNumberTypeException(){
        super("This number type is unsupported here.");
    }
}
