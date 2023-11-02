import java.io.*;
import java.util.LinkedHashMap;
import java.util.*;
//"C:/Users/Владимир/OneDrive/Рабочий стол/testing.txt"
public class Wspp {
    public static void main(String[] args) throws IOException {
        Map<String, IntList> myMap = new LinkedHashMap<>();
        File name = new File(args[0]);
        Scan sc = new Scan(name);
        int numerate = 0;
        while (sc.hasNextWord()) {
            String tempWord = sc.nextWord();
            if (tempWord.length() > 0) {
                numerate++;
                if (myMap.containsKey(tempWord)) {
                    myMap.get(tempWord);
                } else {
                    IntList list = new IntList();
                    myMap.put(tempWord, list);
                }
                myMap.get(tempWord).add(numerate);
            }
        }
        sc.close();
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(args[1]), "UTF8"))) {
            for (Map.Entry<String, IntList> entry: myMap.entrySet()) {
                IntList tempIntList = entry.getValue();
//                tempIntList = tempIntList;
                tempIntList.cut();
                int len = tempIntList.length();
//                System.err.println(entry.getKey() + " " + len);
                out.write(entry.getKey() + " " + len);
                for (int pos : tempIntList.getIntList()) {
                    out.write(" " + pos);
                }
                out.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
//        for (Map.Entry<String, IntList> entry: myMap.entrySet()) {
//            System.out.print(entry.getKey() + " ");
//            IntList tempIntList = entry.getValue();
//            System.out.print(tempIntList.length());
//            for (int pos : tempIntList.getIntList()) {
//                System.out.print(" " + pos);
//            }
//            System.out.println();
//        }
    }
}
