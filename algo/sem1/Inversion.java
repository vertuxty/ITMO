import java.util.Arrays;
import java.util.Scanner;

public class Inversion {
    private static long count = 0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        a = mergeSort(a);
//        count+=1;
        System.out.println(count);
    }


    public static int[] mergeSort(int[] a) {
        int n = a.length;
        if (n <= 1) {
            return a;
        } else {
            int[] a1 = Arrays.copyOfRange(a, 0, n/2);
            int[] a2 = Arrays.copyOfRange(a,n/2, n);
            a1 = mergeSort(a1);
            a2 = mergeSort(a2);
            return merge(a1, a2);
        }
    }

    public static int[] merge(int[] a, int[] b) {
        int n = a.length;
        int m = b.length;
        int[] c = new int[n+m];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < n && j < m) {
            if (a[i] <= b[j]) {
                c[k++] = a[i++];
            } else {
                c[k++] = b[j++];
                count += (n - i);
            }
        }
        while (i < n) {
            c[k++] = a[i++];
        }
        while (j < m) {
            c[k++] = b[j++];
        }
        return c;
    }
}
