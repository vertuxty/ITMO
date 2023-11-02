package search;

public class BinarySearch {

    //Pred: args.length > 0;
    //Post: true;

    public static void main(String[] args) {
        //Pred: args.length > 0;
        int[] array = new int[args.length - 1];
        //Post: array.length = args.length - 1 >= 0;
        //Pred: args[0] is StringDigit && args.length > 0; ( array.length >= 0 <=> args.length > 0)
        int x = Integer.parseInt(args[0]);
        //Pred: true
        long suma = 0;
        //Post:suma = 0;
        //Post: x == Integer.parseInt(args[0]) && args.length >= 0;
        //Inv: 1 <= i' < array.length && args.length >= 0;
        for (int i = 1; i < array.length + 1; i++) {
            //Pred: Inv && args[í'] is StringDigit
            array[i - 1] = Integer.parseInt(args[i]);
            suma += array[i - 1];
            //Post: forall array[i' - 1] = Integer.parseInt(args[i']);
        }
        //Pred: true
        if (args.length == 1) {
            System.out.println(x);
        } else {
            if (suma%2 == 0) {
                System.out.println(iterativeSearch(x, array));
            } else {
                System.out.println(recursiveSearch(x, array, -1, array.length));
            }
        }

    }

    //Pred: array.length > 0, значение x, массив отсортирован по убыванию
    //Post: R == r && -1 < r < array.length, r - минимальный индекс: array[r] <= x;;
    public static int iterativeSearch(int x, int[] array) {
        //Pred: true;
        int l = -1;
        //Post: l = -1
        //Pred: true;
        int r = array.length;
        //Post: r = array.length;
        //Inv:  -1 <= l' < r' <= array.length && (array[l'] > x || array[r'] <= x);
        while ((r - l) > 1) {
            //Pred:-1<= l' < r' <= array.length && (array[l'] > x || array[r']) <= x && (r' - l') > 1;
            int m = (r + l)/2;
            //Post:-1 <= l' < r' <= array.length && (array[l'] > x || array[r']) <= x && (r' - l') > 1 && m = (r' + l')/2;
            /* Попытка док-ва l' < m < r';
            Знаем: m = (r' + l')/2, тогда 2*m > r' + l', так же знаем, что r' > l' + 1 -> 2*m > 2*l' + 1 - > m > l'
            Знаем: m = (r' + l')/2 && r' - l' > 1 -> Представим m*2 = (r' + l') - (r' + l')%2, тогда (r' + l')%2 > (l' - r') -> m*2 < 2*r'
            */
            //Pred:-1 <= l' < r' <= array.length && (array[l'] > x || array[r']) <= x && (r' - l') > 1 && m = (r' + l')/2 && r' > m > l';
            if (array[m] > x) {
                //Pred: -1 <= l' < r' <= array.length && (array[l'] > x || array[r']) && (r' - l') > 1 && m = (r' + l')/2 && r' > m > l' && array[m] > x;
                l = m;
                //Post: l' = m && -1 <= l' < r' <= array.length && (array[l'] > x || array[r']) ---->
                //-1 <= l' < r' <= array.length && (array[l'] > x || array[r'])
            } else {
                //Pred: -1 <= l' < r' <= array.length && (array[l'] > x || array[r']) && (r' - l') > 1 && m = (r' + l')/2 && r' > m > l' && array[m] <= x;
                r = m;
                //Post: r' = m && -1 <= l' < r' <= array.length && (array[l'] > x || array[r'])-->
                // -1 <= l' < r' <= array.length && (array[l'] > x || array[r'])
            }
            //Post: -1 <= l' < r' <= array.length && (array[l'] > x || array[r'])
        }
        // ### r = r', l = l'
        //Post: (r - l) < 1 && array.length > r > l >= -1 && array[r] <= x -->
        //(r - l) < 1 && array.length > r > -1 && array[r] <= x
        return r;
        //Post: R == r && array.length > r > -1 && array[r] <= x;
    }

    //Pred: l => -1 && r <= array.length, сам массив array - остортированный по убыванию, и значение x
    //Post: R == r && -1 < r < array.length && array[r] <= x - причем r  минимальный такой индекс;
    public static int recursiveSearch(int x, int[] array, int l, int r) {
        //Pred: l >= -1 && r <= array.length
        if (r - l > 1) {
            //Pred: l >= -1 && r <= array.length && (r - l) > 1
            int m = (l + r) / 2;
            //Post: l >= -1 && r <= array.length && m == (l + r)/2 && (r - l) > 1;
            /* Попытка док-ва l < m < r;
            Знаем: m = (r + l)/2, тогда 2*m > r + l, так же знаем, что r > l + 1 -> 2*m > 2*l + 1 - > m > l
            Знаем: m = (r + l)/2 && r - l > 1 -> Представим m*2 = (r + l) - (r + l)%2, тогда (r + l)%2 > (l - r) -> m*2 < 2*r
            */
            //Pred: l >= -1 && r <= array.length && l < m < r && m == (l + r)/2 && (r - l) > 1;
            if (array[m] > x) {
                //Pred: l >= -1 && r <= array.length && l < m < r && m == (l + r)/2 && (r - l) > 1 && array[m] > x --->
                // l' = (l + r)/2 && -1 <= l < l' < r <= array.length && array[m] > x -->
                // l' = (l + r)/2 &&  -1 < l' < r < array.length
                return recursiveSearch(x, array, m, r);
                //R == r && -1 < r < array.length && array[r] <= x;
            } else {
                //Pred: l >= -1 && r <= array.length && l < m < r && m == (l + r)/2 && (r - l) / 2 && array[m] < x;
                // r' = (l + r)/2 && -1 <= l < r' < r <= array.length && array[m] < x;
                // r' = (l + r)/2 && l < r' < array.length
                return recursiveSearch(x, array, l, m);
                //R == r && -1 < r < array.length && array[r] <= x;
            }
        } else {
            //Pred: l >= -1 && r <= array.length && (r - l) < 1;
            return r;
            //Post: R == r && -1 < r < array.length && array[r] <= x;
        }
    }
}
