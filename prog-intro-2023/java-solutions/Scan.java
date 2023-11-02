import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

public class Scan {
    private Reader scan;
    private final int bufferSize = 32;
    private char[] buffer;
    private String tempWord = "";
    StringBuilder lines = new StringBuilder();
    StringBuilder tmpWord = new StringBuilder();
    private int reading = 0;
    private String crrWord = "";

    private String currInt = "";
    private String cLines = "";
    StringBuilder digit = new StringBuilder();
    private int index = 0;
    private int numLines = 1;

    Scan(File name) { // Для чтения файлов тестов
        try {
            this.scan = new FileReader(name, StandardCharsets.UTF_8);
            fillBuffer();
        } catch (IOException e) {
            System.out.println("No such file, error!");
        }
    }

    private boolean fillBuffer() {
        try {
            if (scan.ready()) { //Есть ли символ в файле.
                reading = scan.read(buffer); //Заполняем буфер.
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error!");
        }
        return false;
    }

    public int takeSymbolFromBuffer() {
        try {
            return buffer[index++];
        } catch (Exception e) {
            System.out.println("Error!");
        }
        return 0;
    }

    public String nextWord() {
        if (hasNextWord()) {
            String tmp = crrWord;
            crrWord = "";
            return tmp;
        }
        return "";
    }
    private boolean checkOnWordPart(char a) { // Для проверки на слово
        return ((Character.isLetter(a) || Character.getType(a) == Character.DASH_PUNCTUATION || a == '\''));
    }
    public boolean hasNextWord() {
        try {
            char symbol = (char) takeSymbolFromBuffer();
            while (scan.ready() || index < reading) {
                if (!Character.isWhitespace(symbol) || checkOnWordPart(symbol)) {
                    tmpWord.append(symbol);
                    symbol = (char) takeSymbolFromBuffer();
                } else if ((symbol + "").equals(System.lineSeparator()) && tmpWord.length() > 0) { //Иначе добавляем в слово.
                    crrWord = tmpWord.toString().toLowerCase();
                    tmpWord.setLength(0);
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println("Error!");
        }
        return false;
    }

    public void close() {
        try {
            scan.close();
        } catch (IOException e) {
            System.out.println("Error when closing file!");
        }
    }

    public boolean hasNextInt() {
        try {
            if (currInt.length() != 0) {
                return true;
            }
            char symbol = (char) takeSymbolFromBuffer();
            while (reading != -1) {
                if (!Character.isWhitespace(symbol) && Character.isDigit(symbol)) {
                    digit.append(symbol);
                    symbol = (char) takeSymbolFromBuffer();
                } else {
                    currInt = digit.toString();
                    digit.setLength(0);
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Error!");
        }
        return false;
    }

    public int nextInt() {
        if (hasNextInt()) {
            String tmp = currInt;
            currInt = "";
            return Integer.parseInt(tmp);
        } else {
            throw new NoSuchElementException("No numbers!");
        }
    }

    public boolean nextLine() {
        try {
            char tmp = (char) takeSymbolFromBuffer();
            while (!Character.isWhitespace(tmp) && reading != -1) {
                lines.append(tmp);
                tmp = (char) takeSymbolFromBuffer();
            }
            numLines++;
            if (lines.length() > 0) {
                cLines = lines.toString();
                lines.setLength(0);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error!");
        }
        return false;
    }
}
