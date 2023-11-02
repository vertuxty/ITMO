package expression.exceptions;

import expression.BinaryOperations;
import expression.Operations;

public class CheckedClear extends BinaryOperations {
    @Override
    public int evaluate(int x) {
        int val1 = operations1.evaluate(x);
        int val2 = operations2.evaluate(x);
        return checkClear(val1, val2);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int val1 = operations1.evaluate(x, y, z);
        int val2 = operations2.evaluate(x, y, z);
        return checkClear(val1, val2);
    }
    public CheckedClear(Operations operations1, Operations operations2) {
        super(operations1, operations2);
    }
    private int checkClear(int val1, int val2) {
//        System.out.println("CLEAR");
//        if (val2 > 31) {
//            throw new OverflowException("overflow");
//        }
        return val1 & ~(1 << val2);
    }
    @Override
    public String operationsType() {
        return "clear";
    }
}
