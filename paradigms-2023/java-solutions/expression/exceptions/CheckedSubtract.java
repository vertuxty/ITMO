package expression.exceptions;

import expression.BinaryOperations;
import expression.Operations;

public class CheckedSubtract extends BinaryOperations {

    @Override
    public int evaluate(int x) {
        int val1 = operations1.evaluate(x);
        int val2 = operations2.evaluate(x);
        return checkSubtract(val1, val2);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int val1 = operations1.evaluate(x, y, z);
        int val2 = operations2.evaluate(x, y, z);
        return checkSubtract(val1, val2);
    }

    public CheckedSubtract(Operations operations1, Operations operations2) {
        super(operations1, operations2);
    }

    private int checkSubtract(int val1, int val2) {
//        System.out.println("SUB");
        if (val2 == Integer.MIN_VALUE && val1 >= 0) {
            throw new OverflowException("overflow");
        }
        if (val1 > 0 && val2 < 0 && Integer.MAX_VALUE - val1 < -val2) {
            throw new OverflowException("overflow");
        }
        if (val1 < 0 && val2 > 0 && Integer.MIN_VALUE - val1 > -val2) {
            throw new OverflowException("overflow");
        }
        return val1 - val2;
    }
    @Override
    public String operationsType() {
        return "-";
    }
}
