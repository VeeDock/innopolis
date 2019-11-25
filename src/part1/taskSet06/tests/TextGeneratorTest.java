package part1.taskSet06.tests;

import org.junit.Before;
import org.junit.Test;
import part1.taskSet06.TextGenerator;
import part1.taskSet06.WordParser;
import part1.taskSet06.WrongFileFormatException;

import java.io.IOException;
import java.util.List;


public class TextGeneratorTest {
    private List<String> words;

    @Before
    public void init() throws IOException {
        //сгенерируем начальный список слов из куска текста на английском языке.
        //в массив сложим слова длиной не более 15 латинских букв
        WordParser wordParser = new WordParser("src/part1/taskSet06/texts/textSample.txt");
        wordParser.parseFile();
        words = wordParser.getWords(15);

    }

    /**
     * Тест генерации текстовых файлов нужной длины
     */
    @Test
    public void getFiles() throws WrongFileFormatException, IOException {
        TextGenerator.getFiles("src/part1/taskSet06/texts/genFiles", 15, 1000, words, 10);
    }
}