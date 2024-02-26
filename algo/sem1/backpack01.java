import java.util.Scanner;

public class backpack01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] mas = new int[n + 1];
        boolean[][] dp = new boolean[n + 1][m + 1];
        dp[0][0] = true;
        mas[0] = 0;
        for (int i = 1; i < n + 1; i++) {
//            System.out.println("+");
            mas[i] = sc.nextInt();
        }
        for (int i = 1; i < m + 1; i++) {
            dp[0][i] = false;
        }
        for (int i = 1; i < n + 1; i++) {
            for (int j = 0; j < m + 1; j++) {
                dp[i][j] = dp[i-1][j];
                if (j - mas[i] >=0) {
                    dp[i][j] = dp[i][j] || dp[i - 1][j - mas[i]];
                }
//                System.out.println("+");
            }
        }
        int ans = 0;
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < m + 1; j++) {
                if (dp[i][j]) {
                    ans = Math.max(ans, j);
                }
            }
        }
        System.out.println(ans);
    }
}
