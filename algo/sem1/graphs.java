import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class graphs {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();
        int[][] ribs = new int[m][2];

        for (int i = 0; i < m; i++) {
            ribs[i][0] = sc.nextInt();
            ribs[i][1] = sc.nextInt();
        }

        int[][] operations = new int[k][4];

        ArrayList<String> ans = new ArrayList<>();
        String request;

        for (int i = 0; i < k; i++) {
            request = sc.next();
            if (request.equals("cut")) {
                operations[i][1] = 0;
            } else {
                operations[i][1] = 1;
            }
            operations[i][2] = sc.nextInt();
            operations[i][3] = sc.nextInt();
        }

        int[] p = new int[n + 1];
        int[] r = new int[n + 1];

        for (int i = 0; i < n + 1; i++) {
            p[i] = i;
        }

        for (int i = k - 1; i >= 0; i--) {
            if (operations[i][1] == 1) {
                if (getRepresentative(operations[i][2], p) == getRepresentative(operations[i][3], p)) {
                    ans.add("YES");
                } else {
                    ans.add("NO");
                }
            } else {
                team(operations[i][2], operations[i][3], p, r);
            }
        }
        Collections.reverse(ans);
        for (String pos: ans) {
            System.out.println(pos);
        }
    }

    public static int getRepresentative(int x, int[] p) {
        if (x == p[x]) {
            return x;
        }
        int root = getRepresentative(p[x], p);
        p[x] = root;
        return root;
    }

    public static void team(int x, int y, int[] p, int[] r) {
        if (x == y) {
            return;
        }
        x = getRepresentative(x, p);
        y = getRepresentative(y, p);
        if (x == y) {
            return;
        }
        if (r[x] == r[y]) {
            r[x]++;
        }
        if (r[x] > r[y]) {
            p[y] = x;
        } else {
            p[x] = y;
        }
    }
}