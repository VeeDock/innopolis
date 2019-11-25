//Задание 2. Создать генератор текстовых файлов, работающий по следующим правилам:
//        • Предложение состоит из 1<=n1<=15 слов. В предложении после произвольных слов могут находиться запятые.
//        • Слово состоит из 1<=n2<=15 латинских букв
//        • Слова разделены одним пробелом
//        • Предложение начинается с заглавной буквы
//        • Предложение заканчивается (.|!|?)+" "
//        • Текст состоит из абзацев. в одном абзаце 1<=n3<=20 предложений. В конце абзаца стоит разрыв строки и перенос каретки.
//        • Есть массив слов 1<=n4<=1000. Есть вероятность probability вхождения одного из слов этого массива в следующее предложение (1/probability).
//        Необходимо написать метод getFiles(String path, int n, int size, String[] words, int probability), который создаст n файлов размером size в каталоге path. words - массив слов, probability - вероятность.

package part1.taskSet06;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Генератор текстовых файлов из массива слов.
 * Для генерации текстов нет необходимости создавать экземпляр объекта.
 */
public class TextGenerator {

    /**
     * Поле вероятность нахождения слова в следующем предложении
     */
    private int probability;

    /**
     * Поле массива слов для генерации текстов
     */
    private List<String> words;

    private Random rnd = new Random();

    /**
     * Конструктор - создание экземпляра генератора текстов
     * Конструктор приватный, так как используется только в статическом методе {@link TextGenerator#getFiles(String, int, int, String[], int)}
     *
     * @param words       - слова для генерации текстов
     * @param probability - вероятность вхождения каждого слова из массива в следующее предложение (1/probability)
     */
    private TextGenerator(List<String> words, int probability) {
        this.words = words;
        this.probability = probability;

    }

    /**
     * Удаляет из заданного списка слов нужное количество слов в случайном порядке.
     *
     * @param words - список слов, из которого будем удалять слова
     * @param count - количество слов, необходимых для удаления. если больше размера списка, то список будет очищен полностью
     */
    private void removeRandomWords(List<String> words, int count) {
        if (count >= words.size()) {
            words.clear();
            return;
        }
        for (int i = 0; i < count; i++) {
            words.remove(rnd.nextInt(words.size()));
        }
    }


    /**
     * Получает слово из массива слов в соответствии с вероятностью {@link TextGenerator#probability}
     * пройдемся по всему массиву слов и определим за каждое слово попадет оно в набор или нет с вероятностью 1/{@link TextGenerator#probability}
     * если в конце прохода по массиву слов оказалось больше, чем требовалось, снов проходим по этому массиву с определением вероятности
     *
     * @param count   - количество необходимых слов.
     * @param wLength - максимальная длина слова в выборке. Если 0, то ограничений по длине нет.
     * @return возвращает список случайных слов
     */
    private List<String> getRandomWords(int count, int wLength) {
        List<String> words = new ArrayList<>();

        /*
         * вероятность попадания в 1/probability будет определяться путем случайной генерации числа из диапазона от 0 до probability и последующим сравнением полученного числа с 0 для каждого слова.
         * при успешном сравнении, слово будет попадать в следующее предложение.
         * таким образом, мы можем предположить, что каждое число
         * если слов при первом проходе оказалось меньше, чем запрошено в переменной {@link count}, проходим по массиву слов еще раз
         * если слов после прохода оказалось больше, чем требуется по переменной {@link count}, тогда просто случайно удалим некоторые слова из полученного набора слов
         */
        while (words.size() < count) {
            for (String s : this.words) {
                if (wLength > 0 && s.length() > wLength) continue;
                int randomInt = rnd.nextInt(probability);
                if (randomInt == 0) {
                    words.add(s);
                }
            }
        }

        if (words.size() > count) {
            removeRandomWords(words, words.size() - count);
        }

        return words;
    }

    /**
     * Генерация предложения с количеством слов от 1 до 15 включительно.
     *
     * @param size - максимальная длина генерируемого предложения в символах. Если 0, генерируется предложение произвольнойЫ длины, но не более длины предложения из 15 слов.
     * @return возвращает сгенерированное предложение
     */
    private String getSentence(int size) {
        if (size<=2) return "";
        StringBuilder text = new StringBuilder();
        int maxSentenceWLength = 15; //максимальное количество слов в предложении

        int sentenceWLength = rnd.nextInt(maxSentenceWLength); //случайная длина генерируемого предложения для текущего запроса
        /*
         * каждое предложение заканчивается двумя обязательными символами (.|!|?)+" ",
         * поэтому добираем слова покуда не наберем предложение нужного size - 2
         * либо в предложении не окажется случайно определенного количества слов.
         */
        int wordCount = 0;
        List<String> randomWords = getRandomWords(sentenceWLength, size - 2);
        while (text.length() < size - 2 && !randomWords.isEmpty() && wordCount < sentenceWLength) {
            int randomIndex = rnd.nextInt(randomWords.size());
            String word = randomWords.get(randomIndex);
            randomWords.remove(randomIndex);
            if (text.length() + word.length() <= size - 2) {
                boolean addComma = rnd.nextInt(3) == 0; //наличие запятой определим с вероятностью 1/3
                if (text.length() > 0) {
                    if (addComma) text.append(",");
                    text.append(" ");
                }
                text.append(word);
                wordCount++;
            }

            int sizeLeft = size - 2 - text.length();

            if (randomWords.isEmpty()) {
                randomWords = getRandomWords(sentenceWLength, sizeLeft);
            }
        }

        if (text.toString().isBlank()) return "";

        //добавим один из знаков окончания предложения
        int end = rnd.nextInt(3);
        if (end == 0) text.append(".");
        else if (end == 1) text.append("!");
        else text.append("?");

        return text.substring(0,1).toUpperCase() + text.substring(1) + " ";
    }

    /**
     * Генерация рассказа.
     *
     * @return возвращает сгенерированный текст рассказа
     */
    private String getText(int size) {
        StringBuilder text = new StringBuilder();
        int paragraphSize = rnd.nextInt(20);
        while (text.length() < size) {
            text.append(getSentence(size - text.length()));
            if (paragraphSize-- == 0) {
                text.append("\r\n");
                paragraphSize = rnd.nextInt(20);
            }
        }

        return text.toString();
    }

    /**
     * Создание файлов с генерированным текстом в указанном каталоге
     *
     * @param path        - каталог, где будут создаваться файлы
     * @param n           - количество генерируемых файлов
     * @param size        - размер генерируемого файла
     * @param words       - массив слов для генерации файлов
     * @param probability - вероятность вхождения одного из слов из массива в следующее предложение
     */
    public static void getFiles(String path, int n, int size, List<String> words, int probability) throws WrongFileFormatException, IOException {
        if (size < 2) throw new WrongFileFormatException("Размер генерируемого файла должен быть больше 2");
        if (words.isEmpty()) throw new WrongFileFormatException("Невозможно создать текст из пустого набора слов");
        TextGenerator textGenerator = new TextGenerator(words, probability);

        for (int i = 0; i < n; i++) {
            String text = textGenerator.getText(size);
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path, (i+1)+"_story.txt")))){
                writer.write(text);
            }
        }
    }
}
