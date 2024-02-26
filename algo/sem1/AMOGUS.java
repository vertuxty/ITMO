import java.util.Scanner;

public class AMOGUS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t > 0) {
            long impost = sc.nextLong();
            long crew = sc.nextLong();
            long maxKills = (1 + impost)*impost/2;
            if (crew > maxKills) {
                System.out.println("Crewmates");
                System.out.println(impost);
            } else {
//                if (maxKills == crew) {
//                    System.out.println("Impostors");
//                    System.out.println(impost);
//                }
                long right = (impost + 1);
                long left = -1;
                while (right - left > 1) {
                    long m = (right + left)/2;
                    long a = maxKills - (m + 1)*m/2;
                    if (crew - m * impost + m * (m - 1) / 2 <= 0) {
                        right = m;
                    } else {
                        left = m;
                    }
                }
                System.out.println("Impostors");
                System.out.println(right);
//                System.out.println(impost - right);
            }
            t--;
        }
    }
}
