package part1.taskSet06.tests;

import org.junit.Before;
import org.junit.Test;
import part1.taskSet06.WordParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class WordParserTest {

    WordParser wordParser;

    @Before
    public void readFile() {
        ByteArrayInputStream in = new ByteArrayInputStream("src/part1/taskSet06/texts/textSample.txt".getBytes()); //имитируем ввод данных пользователем с консоли.
        System.setIn(in); //подменяем System.in нужным потоком байт
        wordParser = new WordParser();
    }

    @Test
    public void parseFile() throws IOException {
        wordParser.parseFile();
        wordParser.saveParsed("src/part1/taskSet06/texts/parseResult.txt");
    }
}