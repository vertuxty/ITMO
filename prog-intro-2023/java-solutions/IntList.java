import java.awt.image.AreaAveragingScaleFilter;
import java.util.Arrays;

public class IntList {
    private int count = 0;
//    private int[] intList = new int[] {2, 0};
    private int[] intList = new int[1];

    public void add(int values) {
        if (count >= intList.length) {
            intList = Arrays.copyOf(intList, intList.length*2);
        }
        intList[count] = values;
        count++;
    }
    public void replace(int i, int a) {
        intList[i] = a;
    }

//    public int getInd() {
//        return intList[0];
//    }
//
    public int getInt(int i) {
        return intList[i];
    }
//    }
    public int length() {
        return intList.length;
    }

//    public int getCount() {
//        return count;
//    }
    public void cut() {
        intList = Arrays.copyOf(intList, count);
    }
//    public String
    public int[] getIntList() {
        return Arrays.copyOf(intList, count);
    }

    public String toString() {
        return Arrays.toString(intList);
    }
}
