package expression.exceptions;

import expression.BinaryOperations;
import expression.Operations;

public class CheckedSet extends BinaryOperations {
    @Override
    public int evaluate(int x) {
        int val1 = operations1.evaluate(x);
        int val2 = operations2.evaluate(x);
        return checkSet(val1, val2);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int val1 = operations1.evaluate(x, y, z);
        int val2 = operations2.evaluate(x, y, z);
        return checkSet(val1, val2);
    }
    public CheckedSet(Operations operations1, Operations operations2) {
        super(operations1, operations2);
    }
    private int checkSet(int val1, int val2) {
//        System.out.println("SET");
//        if (val2 > 31 && val1 != 0) {
//            throw new OverflowException("overflow");
//        }
        return val1 | (1 << val2);
    }
    @Override
    public String operationsType() {
        return "set";
    }
}
