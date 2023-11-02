import java.io.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
//"C:/Users/Владимир/OneDrive/Рабочий стол/testing.txt"
public class WsppCountPosition {

    // No error should be thrown in main(String[])
    public static void main(String[] args) throws IOException {
        Map<String, IntList> myMap = new LinkedHashMap<>();
        File name = new File(args[0]);
        Scan sc = new Scan(name);
        int countLines = 0;
        int currLine = 1;
        int numerate = 0;
        int cWord = 0;
        int numLine = 1;
        while (sc.hasNextWord()) {
            if (numLine < countLines) {
//                numLine = sc.countLine();
                numLine = countLines;
                numerate = 0;
            }
            String tempWord = sc.nextWord();
            if (!tempWord.equals("")) {
                numerate++;
                // useless
                if (myMap.containsKey(tempWord)) {
                    myMap.get(tempWord);
                } else {
                    cWord++;
                    myMap.put(tempWord, new IntList());
                }
                myMap.get(tempWord).add(numLine - numLine/2);
                myMap.get(tempWord).add(numerate);
            }
        }

        // finally
        sc.close();

        int maxel = -10^20;
        for (Map.Entry<String, IntList> entry: myMap.entrySet()) { //Сортировка подсчетом:)):):):):))
            IntList tempList = entry.getValue();
            tempList.cut();
            maxel = Math.max(tempList.length()/2, maxel);
        }
        int[] cnt = new int[maxel + 1];
        int ind = 0;
        String[][] tmpStrList = new String[cWord][2];
        String[][] test = new String[cWord][2];

        for (Map.Entry<String, IntList> entry: myMap.entrySet()) {
            IntList tempList = entry.getValue();
            tempList.cut();
            tmpStrList[ind] = new String[] {String.valueOf(tempList.length()/2), entry.getKey()};
            ind++;
        }
        for (int i = 0; i < tmpStrList.length; i++) {
            cnt[Integer.parseInt(tmpStrList[i][0])]++;
        }

        int carry = 0;
        for (int i = 0; i < maxel + 1; i++) {
            int temp = cnt[i];
            cnt[i] = carry;
            carry += temp;
        }
        for(int i = 0; i < tmpStrList.length; i++) {
            test[cnt[Integer.parseInt(tmpStrList[i][0])]] = tmpStrList[i];
            cnt[Integer.parseInt(tmpStrList[i][0])]++;
        }


        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(args[1]), "UTF8"))) {
            for (int j = 0; j < cWord; j++) {
                String tmp = test[j][1];
                IntList tempIntlist = myMap.get(tmp);
                int len = tempIntlist.length()/2;
                out.write(tmp + " " + len);
                for (int i = 0; i < tempIntlist.length(); i = i + 2) {
                    out.write(" " + tempIntlist.getInt(i) + ":" + tempIntlist.getInt(i + 1));
                }
                out.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
