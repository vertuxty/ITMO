import java.util.Arrays;
import java.util.Scanner;

public class Reverse {
    public static String[] scanOfLines(String[] orderOfLines) {
        int indexLine = 0;
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()){
            if (indexLine >= orderOfLines.length) {
                orderOfLines = Arrays.copyOf(orderOfLines, orderOfLines.length*2);
            }
            String currentLine = sc.nextLine();
            String[] arr = currentLine.split(" ");
            StringBuilder buff = new StringBuilder();
            for (int i = arr.length - 1; i >= 0; i--) {
                if (!arr[i].isEmpty()) {
                    buff.append(arr[i]);
                    buff.append(" ");
                }
            }
            orderOfLines[indexLine] = buff.toString().strip();
            indexLine++;
        }
        return orderOfLines;
    }

    public static void printOfLines(String[] newOrderOfLines) {
        for (int j = newOrderOfLines.length - 1; j >= 0 ; j--) {
            if (newOrderOfLines[j] != null) {
                System.out.println(newOrderOfLines[j]);
            }
        }
    }

    public static void main(String[] args){
        String[] orderOfLines = new String[1];
        String[] newOrderOfLines = scanOfLines(orderOfLines);
        printOfLines(newOrderOfLines);
    }
}
