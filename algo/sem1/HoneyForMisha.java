import java.util.Arrays;
import java.util.Scanner;

public class HoneyForMisha {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int p = sc.nextInt();
        int[] a = new int[n];
        long summa = 0;
        for (int i = 0; i < n; i++) {
            int temp = sc.nextInt();
            while (temp >= p && m > 0) {
                m--;
                summa += p;
                temp -= p;
            }
            if (m == 0) {
                break;
            } else {
                a[i] = temp;
            }
        }
//        System.out.println(Arrays.toString(a));
//        System.out.println(summa);
        a = sort(a);
        int i = n - 1;
        while (i >= 0 && m > 0) {
            m--;
            summa += a[i];
            i--;
        }
        System.out.println(summa);
    }

    public static int[] merge(int[] a, int[] b) {
        int n = a.length;
        int m = b.length;
        int[] c = new int[n + m];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < n || j < m) {
            if (j == m || (i < n && a[i] <= b[j])) {
                c[k++] = a[i++];
            } else {
                c[k++] = b[j++];
            }
        }
        return c;
    }

    public static int[] sort(int[] a) {
        int n = a.length;
        if (n <= 1) {
            return a;
        }
        int[] a1 = Arrays.copyOfRange(a, 0, n/2);
        int[] a2 = Arrays.copyOfRange(a, n/2, n);
        a1 = sort(a1);
        a2 = sort(a2);
        return merge(a1, a2);
    }
}