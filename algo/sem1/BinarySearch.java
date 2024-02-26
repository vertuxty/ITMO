import java.util.Arrays;
import java.util.Scanner;

public class BinarySearch {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] massN = new int[n];
        int[] massK = new int[k];
        for (int i = 0; i < n; i++) {
            massN[i] = sc.nextInt();
        }
        Arrays.sort(massN);
        for (int i = 0; i < k; i++) {
            massK[i] = sc.nextInt();
        }
        for (int i = 0; i < k; i++) {
            int ind = BinarySearching(massK[i], massN);
            if (ind < n) {
                if (massK[i] == massN[ind]) {
                    System.out.println("YES");
                } else {
                    System.out.println("NO");
                }
            } else {
                System.out.println("NO");
            }
        }
    }


    public static int BinarySearching(int k, int[] massN) {
        int right = massN.length;
        int left = -1;
        while (right - left > 1) {
            int m = (right + left)/2;
            if (massN[m] >= k) {
                right = m;
            } else {
                left = m;
            }
        }
        return right;
    }
}