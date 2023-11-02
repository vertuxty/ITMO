package expression.exceptions;

import expression.BinaryOperations;
import expression.Operations;
import expression.TripleExpression;

public class CheckedMultiply extends BinaryOperations {

    @Override
    public int evaluate(int x, int y, int z) {
        int val1 = operations1.evaluate(x, y, z);
        int val2 = operations2.evaluate(x, y, z);
        return checkInMultiply(val1, val2);
    }

    @Override
    public int evaluate(int x) {
        int val1 = operations1.evaluate(x);
        int val2 = operations2.evaluate(x);
        return checkInMultiply(val1, val2);
    }

    public CheckedMultiply(Operations operations1, Operations operations2) {
        super(operations1, operations2);
    }
    private int checkInMultiply(int val1, int val2) {
//        System.out.println("Multi");
        int result = val1 * val2;
        if (val1 == 0 || val2 == 0) {
            return 0;
        }
        if (val1 > 0 && val2 > 0 && Integer.MAX_VALUE / val1 < val2) {
            throw new OverflowException("overflow");
        }
        if (val1 < 0 && val2 == Integer.MIN_VALUE || val2 < 0 && val1 == Integer.MIN_VALUE) {
            throw new OverflowException("overflow");
        }
        if (val1 < -1 && val2 > 1 && Integer.MIN_VALUE / val1 < val2) {
            throw new OverflowException("overflow");
        }
        if (val1 > 1 && val2 < -1 && Integer.MIN_VALUE / val2 < val1) {
            throw new OverflowException("overflow");
        }
        if (val1 < 0 && val2 < 0 && Integer.MAX_VALUE / val1 > val2) {
            throw new OverflowException("overflow");
        }
//        }else if (((val1 != 0 && result / val1 != val2) || (val2 != 0 && result / val2 != val1))) {
//            throw new OverflowException("overflow");
//        }
//        if ((val1 > 0 && val2 > 0 && Integer.MAX_VALUE / val2 < val1)
//                || (val1 < 0 && val2 < 0 && Integer.MAX_VALUE / val1 < val2)
//                || (val1 > 0 && val2 < 0 && Integer.MIN_VALUE / val2 < val1)
//                || (val1 < 0 && val2 > 0 && Integer.MIN_VALUE / val1 < val2)
//                || (val1 < 0 && val2 < 0 && (val1 / Integer.MAX_VALUE == -1 || val2)) {
//            throw new OverflowException("overflow");
//        }
        return val1 * val2;
    }
    @Override
    public String operationsType() {
        return "*";
    }
}
