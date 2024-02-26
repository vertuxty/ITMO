import java.util.ArrayList;
import java.util.Scanner;

public class generateString {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        String a = "Aa";
        String b = "BB";
        int m = (int) Math.ceil(Math.log(k)/Math.log(2));
        StringBuilder str;
        String tmp = Integer.toBinaryString(k - 1);
        int lenMax = tmp.length();
        for (int i = 0; i < k; i++)
        {
            str = new StringBuilder(Integer.toBinaryString(i));
            int len = str.length();
            StringBuilder suffix = new StringBuilder();
            for (int j = 0; j < len; j++)
            {
                if (str.charAt(j) == '0')
                {
                    suffix.append("Aa");
                } else {
                    suffix.append("BB");
                }
            }

            StringBuilder prefix = new StringBuilder();
            int p = 0;
            for (int j = 0; j < 20 - len * 2; j += 2)
            {
                prefix.append("Aa");
                p = j;
            }
            StringBuilder all = new StringBuilder();
            all.append(prefix.toString());
            all.append(suffix.toString());
//            System.out.println(lenMax + " " + len);
            System.out.println(all);
//            System.out.println(str + "\n === \n");
        }
    }
}