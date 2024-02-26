import java.util.Arrays;
import java.util.Scanner;

public class AntiQuickSort {

    public static int ind = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i + 1;
        }
        if (n < 3) {
            for (int i = 0; i < n; i++) {
                System.out.print(a[i] + " ");
            }
        }
        if (n >= 3) {
            for (int i = 2; i < n; i++) {
                int tmp = a[i];
                a[i] = a[i/2];
                a[i/2] = tmp;
            }
            for (int i = 0; i < n; i++) {
                System.out.print(a[i] + " ");
            }
        }
    }
}