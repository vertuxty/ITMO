package search;

public class BinarySearchMax {
    //Pred: args.length > 0
    //Post: true
    public static void main(String[] args) {
        int[] array = new int[args.length];
        long sum = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = Integer.parseInt(args[i]);
            sum+= array[i];
        }
        if (array.length == 1) {
            System.out.println(array[0]);
        } else {
            if (sum%2 == 0) {
                System.out.println(recursiveSearch(array, -1, array.length, array.length - 1));
            } else {
                System.out.println(iterativeSearchBorder(array));
            }
        }
    }
    //Pred: массив отсортированный по возрастанию и циклически сдвинутый, l=-1, r=array.length, len = array.length
    //Post: R = array[l] - при l>=0 || R == array[array.length - 1] - при l + 1 = 0, причем -1 <= l <= array.length - 1, причем R - максимум в массиве
    public static int recursiveSearch(int[] array, int l, int r, int len) {
        //Pred: -1 <= l < r <= array.length &&  l < len <= array.length - 1;
        if (r - l > 1) {
            //Pred: -1 <= l < r <= array.length &&  l < len <= array.length - 1; && (r - l) > 1;
            int m = (l + r)/2;
            //Post: -1 <= l < r <= array.length &&  l < len <= array.length - 1; && (r - l) > 1 && m == (l + r)/2;
            /* Попытка док-ва l < m < r;
            Знаем: m = (r + l)/2, тогда 2*m > r + l, так же знаем, что r > l + 1 -> 2*m > 2*l + 1 - > m > l
            Знаем: m = (r + l)/2 && r - l > 1 -> Представим m*2 = (r + l) - (r + l)%2, тогда (r + l)%2 > (l - r) -> m*2 < 2*r
            */
            //Post: -1 <= l < r <= array.length &&  l < len <= array.length - 1; && (r - l) > 1 && m == (l + r)/2 && l < m < r;
            if (array[len] < array[m]) {
                //Pred: -1 <= l < r <= array.length &&  l < len <= array.length - 1; && (r - l) > 1 && m == (l + r)/2 && l < m < r && array[len] < array[m];
                // l' = (l + r)/2 && -1 < l' < len <= array.length - 1 && l' < r <= array.length && (l < l' < r) && array[len] < array[l']
                return recursiveSearch(array, m, r, len);
                //Post: R == l + 1 && 0 <= 1 + l < array.length
            } else {
                //Pred: -1 <= l < r <= array.length &&  l < len <= array.length - 1; && (r - l) > 1 && m == (l + r)/2 && l < m < r && array[len] >= array[m];
                // r' = (l + r)/2 && -1 <= l < r' < array.length && len' = m && l < len'
                return recursiveSearch(array, l, m, m);
                //Post: R == l + 1 && 0 <= 1 + l < array.length
            }

        } else {
            //Pred: -1 <= l < r <= array.length &&  -1 <= l < len <= array.length - 1 && (r - l) < 1;
            if (l + 1 == 0) {
                //Pred: -1 <= l < r <= array.length && l + 1 == 0 ;
                return array[array.length - 1];
            } else {
                //Pred: -1 <= l < r <= array.length && l + 1 > 0 ;
                return array[l];
            }
            //Post: R == l + 1 && -1 <= l < r <= array.length &&  l < len <= array.length - 1 && (r - l) < 1;
        }
    }
    //Pred: массив отсортированный по возрастанию и циклически сдвинутый.
    //Post: R = array[l], при l >= 0 || R == array[array.length - 1] - при l + 1 = 0, -1 <= l <= array.length, причем R - максимум в массиве
    public static int iterativeSearchBorder(int[] array) {
        //Pred: true
        int l = -1;
        //Post: l == -1
        //Pred: l == -1
        int len = array.length - 1;
        //Post: len == array.length && l == -1
        int r = array.length;
        //Post: len == array.length && l == -1 && r == array.length
        //Inv: -1 <= l' < len' <= r' <= array.length;
        while (r - l > 1) {
            //Pred: (r' - l') > 1 && -1 <= l' < len' <= r' <= array.length;
            int m = (r + l)/2;
            //Post: m == (r' + l')/2 && (r' - l') > 1 && -1 <= l' < len' <= r' <= array.length;
            /* Попытка док-ва l' < m < r';
            Знаем: m = (r' + l')/2, тогда 2*m > r' + l', так же знаем, что r' > l' + 1 -> 2*m > 2*l' + 1 - > m > l'
            Знаем: m = (r' + l')/2 && r' - l' > 1 -> Представим m*2 = (r' + l') - (r' + l')%2, тогда (r' + l')%2 > (l' - r') -> m*2 < 2*r'
            */
            //Pred: m == (r' + l')/2 && (r' - l') > 1 && -1 <= l' < len' <= r' <= array.length && l' < m < r';
            if (array[len] < array[m]) {
                //Pred: m == (r' + l')/2 && (r' - l') > 1 && -1 <= l' < len' <= r' <= array.length && l' < m < r' && array[len'] < array[m];
                l = m;
                //Post: l' == m && m == (r' + l')/2 && (r' - l') > 1 && -1 < l' < len' <= r' <= array.length && l' < m < r' && array[len'] < array[m] -->
                // l' = (r' + l')/2 && -1 < l' < len' <= r' <= array.length && array[len'] < array[l'] && (r' - l') > 1;
                // (r' - l') > 1 && -1 < l' < len' <= r' <= array.length ;
            } else {
                //Pred: m == (r' + l')/2 && (r' - l') > 1 && -1 <= l' < len' <= r' <= array.length && l' < m < r' && array[len'] >= array[m];
                r = m;
                //Post: r' == m && m == (r' + l')/2 && (r' - l') > 1 && -1 <= l' < len' <= r' < array.length && l' < m < r' && array[len'] >= array[m] --->
                // r' == (l' + r')/2 && -1 <= l' < len' <= r' < array.length && array[len'] >= array[r'] && (r' - l') > 1;
                len = r;
                //Post: len' == r' && r' == (l' + r')/2 && -1 <= l' < len' <= r' < array.length && array[len'] >= array[r'] -->
                // (r' - l') > 1 && -1 <= l' < len' <= r' < array.length
            }
            // (r' - l') > 1 && -1 <= l' < len' <= r' <= array.length
        }
        // ## r = r', l = l', len = len'
        //Post: (r - l) < 1 && -1 <= l < len <= r <= array.length
        //Pred: -1 <= l < len < r <= array.length
        if (l + 1 == 0) {
            //Pred: -1 <= l < len < r <= array.length && l + 1 == 0
            return array[array.length - 1];
            //R == array[array.length - 1];
        }
        //Pred: -1 <= l < len < r <= array.length && l > 0;
        return array[l];
        //R == array[l]
    }

}
