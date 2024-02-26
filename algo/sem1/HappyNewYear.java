import java.util.Scanner;

public class HappyNewYear {
    static double eps = 0.00001;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // лампочки
        String A = sc.next(); // Высота 1 конца.
        double[] h = new double[n]; //массив высот
        h[0] = Double.parseDouble(A); // заночим 1 высоту.
        Double minHight = BinaryDouble(h[0], h);
//        StringBuilder sb = new StringBuilder();
//        int tochnost = 0;
//        boolean fl = false;
//        int i = 0;
//        while (tochnost < 3) {
//            if (str.charAt(i) == '.') {
//                fl = true;
//            }
//            sb.append(str.charAt(i));
//            i++;
//            if (fl) {
//                tochnost++;
//            }
//        }
        String result = String.format("%.2f", minHight);
        System.out.println(result);
    }

    public static double BinaryDouble(double A, double[] h) {
        double right = A;
        double left = 0;
        while (right - left > eps) {
            h[1] = (left + right)/2;
            boolean high = true;
            for (int i = 2; i < h.length; i++) {
                h[i] = 2*h[i-1] - h[i-2] + 2;
                if (h[i] < 0) { // Если лампочка касается пола, нас не устраивает
                    high = false;
                    break;
                }
            }
            if (high) {//Если она не касается пола, то все круто, передвигаем границу ниже
                right = h[1];
            } else {
                left = h[1]; //иначе сдвигаем ее выше
            }
        }
        return h[h.length - 1];
    }
}