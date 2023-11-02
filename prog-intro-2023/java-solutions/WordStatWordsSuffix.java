import java.io.*;
import java.util.Arrays;

public class WordStatWordsSuffix {

    public static boolean checkWordNotInArray(String word, String[] listOfSuffix, int[] countOfSuffix) {
        for (int i = 0; i < listOfSuffix.length; i++) {
            if (word.equals(listOfSuffix[i])) {
                countOfSuffix[i] += 1;
                return false;
            }
        }
        return true;
    }

//    public static void addSuffixInArray(int count, String tempStr, String[] listOfSuffix, int[] countOfSuffix) {
//        if (count >= listOfSuffix.length) {
//            listOfSuffix = Arrays.copyOf(listOfSuffix, listOfSuffix.length*2 + 1);
//            countOfSuffix = Arrays.copyOf(countOfSuffix, countOfSuffix.length*2 + 1);
//        }
//        listOfSuffix[count] = tempStr;
//        countOfSuffix[count] += 1;
//        count++;
//    }

    public static void printSuffixInLexico(String[] listOfSuffix, int[] countOfSuffix) {
        for (int i = listOfSuffix.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (listOfSuffix[i] != null && listOfSuffix[j] != null) {
                    if (listOfSuffix[j].compareTo(listOfSuffix[j + 1]) > 0) {
                        String tempStr = listOfSuffix[j];
                        listOfSuffix[j] = listOfSuffix[j + 1];
                        listOfSuffix[j + 1] = tempStr;
                        int tempInt = countOfSuffix[j];
                        countOfSuffix[j] = countOfSuffix[j + 1];
                        countOfSuffix[j + 1] = tempInt;
                    }
                }
            }
        }
    }


//"C:/Users/Владимир/OneDrive/Рабочий стол/testing.txt"
    public static void main(String[] args) {
        String[] listOfSuffix = new String[1];
        int[] countOfSuffix = new int[1];
        int count = 0;
        try {
            BufferedReader fileNew = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]),"utf-8"));
            try {
                StringBuilder word = new StringBuilder();
                while (fileNew.ready()) {
                    char symbol = (char) fileNew.read();
                    if (Character.DASH_PUNCTUATION != Character.getType(symbol) && !Character.isLetter(symbol) && symbol != '\'') {
                        if (word.length() > 0) {
                            String tempStr = word.toString().toLowerCase();
                            if (word.length() > 3) {
                                tempStr = word.substring(word.length() - 3).toLowerCase();
                            }
                            if (checkWordNotInArray(tempStr, listOfSuffix, countOfSuffix)) { // Проверяю наличие элемента в массиве
                                if (count >= listOfSuffix.length) {
                                    listOfSuffix = Arrays.copyOf(listOfSuffix, listOfSuffix.length*2 + 1);
                                    countOfSuffix = Arrays.copyOf(countOfSuffix, countOfSuffix.length*2 + 1);
                                }
                                listOfSuffix[count] = tempStr;
                                countOfSuffix[count] += 1;
                                count++;
                            }
                            word.setLength(0);
                        }
                    } else {
                        word.append(symbol);
                    }
                }
                listOfSuffix = Arrays.copyOf(listOfSuffix, count);
                countOfSuffix = Arrays.copyOf(countOfSuffix, count);
                printSuffixInLexico(listOfSuffix, countOfSuffix); //Сортирую в лексикографическом порядке
            } finally {
                fileNew.close();
            }
            BufferedWriter out = new BufferedWriter( new OutputStreamWriter( new FileOutputStream(args[1]), "utf-8"));
            try {
                for (int i = 0; i < listOfSuffix.length; i++) {
                    out.write(listOfSuffix[i] + " " + countOfSuffix[i]);
                    out.newLine();
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
}

