import java.util.Scanner;

public class Wires {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); //сколько проводов дано изначально.
        int k = sc.nextInt(); //Сколько открезков равной длинны надо получить.
        int[] wires = new int[n]; //храним длинны проводов.
        int suma = 0;
//        System.out.println(k);
        for (int i = 0; i < n; i++) {
            wires[i] = sc.nextInt();
            suma += wires[i];
        }
        if (suma == k) {
            System.out.println(1);
        } else {
            System.out.println(Wires(k, wires));
        }
    }

    public static long Wires(int k, int[] arr) {
        long l = 0;
        long r = (long) Math.pow(10, 7) + 1; //Граница L_i
        while (r - l > 1) {
            long m = (r + l)/2;
            int count = 0;
            for (int otrez: arr) {
                count += otrez/m;
            }
            if (count >= k) {
                l = m; // Если count >= k, то необходимо увеличить m => l = m
            } else {
                r = m; //Иначе, мы уменьшаем m => r = m;
            }
//            System.out.println(r + " " + l);
        }
        return l;
    }
}