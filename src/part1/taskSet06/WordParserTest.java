package part1.taskSet06;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class WordParserTest {

    WordParser wordParser;

    @Before
    public void readFile() {
        ByteArrayInputStream in = new ByteArrayInputStream("sometext.txt".getBytes()); //имитируем ввод данных пользователем с консоли.
        System.setIn(in); //подменяем System.in нужным потоком байт
        wordParser = new WordParser().init();
    }

    @Test
    public void parseFile() throws IOException {
        wordParser.parseFile();
        wordParser.saveParsed("parseResult.txt");
    }
}