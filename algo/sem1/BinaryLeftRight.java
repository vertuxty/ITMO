import java.util.Scanner;

public class BinaryLeftRight {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] massN = new int[n];
        for (int i = 0; i < n; i++) {
            massN[i] = sc.nextInt();
        }
        for (int i = 0; i < m; i++) {
            int tmp = sc.nextInt();
            int indR = BinarySearchRight(tmp, massN);
            int indL = BinarySearchLeft(tmp, massN);

            if (indL < massN.length && indR < massN.length) {
                if (tmp == massN[indL] && tmp == massN[indR]) {
                    System.out.println((indL + 1) + " " + (indR + 1));
                } else {
                    System.out.println(0);
                }
            } else {
                System.out.println(0);
            }
        }
    }

    public static int BinarySearchLeft(int k, int[] massN) {
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

    public static int BinarySearchRight(int k, int[] massN) {
        int right = massN.length;
        int left = -1;
        while (right - left > 1) {
            int m = (right + left)/2;
            if (massN[m] > k) {
                right = m;
            } else {
                left = m;
            }
        }
        return left;
    }

}
