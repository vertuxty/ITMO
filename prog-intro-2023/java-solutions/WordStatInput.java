import java.io.*;
import java.util.Arrays;

public class WordStatInput {

    public static void main(String[] args) {
        String[] listOfWords = new String[1];
        int[] countOfWords = new int[1];
        int count = 0;
        try {
            BufferedReader fileNew = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]),"utf-8"));
            try {
                StringBuilder word = new StringBuilder();
                while (fileNew.ready()) {
                    char symbol = (char) fileNew.read();
                    if (Character.DASH_PUNCTUATION != Character.getType(symbol) && !Character.isLetter(symbol) && symbol != '\'') {
                        if (word.length() != 0) {
                            count++;
                            int temp = 0;
                            for (int i = 0; i < listOfWords.length; i++) {
                                if (word.toString().toLowerCase().equals(listOfWords[i])) {
                                    countOfWords[i] += 1;
                                    temp++;
                                    break;
                                }
                            }
                            if (temp == 0) {
                                if (count >= listOfWords.length) {
                                    listOfWords = Arrays.copyOf(listOfWords, listOfWords.length*2);
                                    countOfWords = Arrays.copyOf(countOfWords, countOfWords.length*2);
                                }
                                listOfWords[count] = word.toString().toLowerCase();
                                countOfWords[count] += 1;
                            }
                            word.delete(0, word.length());
                        }
                    } else {
                        word.append(symbol);
                    }
                }
            } finally {
                fileNew.close();
            }
            BufferedWriter out = new BufferedWriter( new OutputStreamWriter( new FileOutputStream(args[1]), "utf-8"));
            try {
                for (int i = 0; i < listOfWords.length; i++) {
                    if (listOfWords[i] != null) {
                        out.write(listOfWords[i] + " " + countOfWords[i]);
                        out.newLine();
                    }
                }
            } finally {
                out.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File name does not exist: " + e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    //Если надо будет вывести в порядке кол-ва вхождений, то использовать сортировку по массиву countOfWords.
    //Аналогично сортировке, только сравниваем в порядке лексографическом.
}

