import java.util.Arrays;
import java.util.Scanner;

public class A1sort {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        int[] a = new int[count];
        for (int i = 0; i < count; i++) {
            a[i] = sc.nextInt();
        }
        int [] aSort = sort(a);
        for (int i = 0; i < aSort.length; i++) {
            if (i == aSort.length -1) {
                System.out.print(aSort[i]);
            } else {
                System.out.print(aSort[i] + " ");
            }
        }
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