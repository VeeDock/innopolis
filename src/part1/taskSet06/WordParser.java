//Задание 1. Написать программу, читающую текстовый файл. Программа должна составлять отсортированный по алфавиту список слов, найденных в файле и сохранять его в файл-результат. Найденные слова не должны повторяться, регистр не должен учитываться. Одно слово в разных падежах – это разные слова.

package part1.taskSet06;

import java.io.*;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * Класс для обработки файла и сохранения всех уникальных слов в алфавитном порядке в другом файле.
 */
public class WordParser {
    private File file; //файл оригинал, с которого будем проводить чтение.
    private Set<String> wordSet = new TreeSet<>();
    private File resultFile; //файл по умолчанию, куда будет сохраняться результат. инициализируется в конструкторе.


    /**
     * Инициализация файла, из которого будем считывать все слова, путем ввода имени файла с консоли.
     *
     * @return WordParser
     */
    public WordParser init() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));) {
            this.file = new File(reader.readLine());
            resultFile = new File(file + "_parsed");
        } catch (IOException e) {
            System.out.println("read file error " + e.getMessage());

        }
        return this;
    }

    /**
     * Метод проходит по инициализированному файлу и сохраняет все слова в TreeSet в нижнем регистре
     *
     * @throws IOException
     */
    public void parseFile() throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            Matcher matcher = Pattern.compile("[a-zA-Zа-яА-Я]+").matcher(fileReader.lines().collect(Collectors.joining(System.lineSeparator())));
            while (matcher.find()) {
                wordSet.add(matcher.group().toLowerCase()); //регистр в нашей программе не учитывается, поэтому все слова будем хранить в нижнем регистре
            }
        }
    }

    private void save(File resultFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile))) {
            writer.write(wordSet.stream().collect(Collectors.joining(System.lineSeparator())));
        }
    }

    /**
     * Сохраняет все отпарсенные слова в файл по умолчанию
     *
     * @throws IOException
     */
    public void saveParsed() throws IOException {
        save(resultFile);
    }

    /**
     * Сохраняет все отпарсенные слова в указанный файл.
     *
     * @param resultFile файл, в который сохранить все слова.
     * @throws IOException
     */
    public void saveParsed(String resultFile) throws IOException {
        save(new File(resultFile));
    }
}
