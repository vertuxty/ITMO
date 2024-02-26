import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Backpack {

    public static ArrayList<Integer> ans = new ArrayList<>();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] mas = new int[n + 1];
        int[] cost = new int[n + 1];
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i < n + 1; i++) {
            mas[i] = sc.nextInt();
        }
        for (int i = 1; i < n + 1; i++) {
            cost[i] = sc.nextInt();
        }
        mas[0] = 0;
        dp[0][0] = 0;

        for (int i = 1; i < n + 1; i++) {
            for (int j = 0; j < m + 1; j++) {
                dp[i][j] = dp[i-1][j];
                if ((j - mas[i]) >= 0) {
                    dp[i][j] = Math.max(dp[i][j], dp[i-1][j-mas[i]] + cost[i]);
                }
            }
        }
        int count = 0;
        for (int i = 0; i < m + 1; i++) {
            count = Math.max(count, dp[n][i]);
        }
        int k = n;
        int l = m;
        while (k > 0 && l >= 0) {
            if (dp[k - 1][l] == dp[k][l]) {
                k--;
            } else {
                l = l - mas[k];
                ans.add(k);
                k--;
            }
        }
        Collections.reverse(ans);
        System.out.println(ans.size());
        for (int pos: ans) {
            System.out.print(pos + " ");
        }
    }
}