package expression.exceptions;

import expression.BinaryOperations;
import expression.Operations;
import expression.TripleExpression;

public class CheckedDivide extends BinaryOperations {
    @Override
    public int evaluate(int x, int y, int z) {
        int val1 = operations1.evaluate(x, y, z);
        int val2 = operations2.evaluate(x, y, z);
        return checkInDivide(val1, val2);
    }

    @Override
    public int evaluate(int x) {
        int val1 = operations1.evaluate(x);
        int val2 = operations2.evaluate(x);
        return checkInDivide(val1, val2);
    }

    private int checkInDivide(int val1, int val2) {
        if (val2 == 0) {
            throw new DivideByZeroException("division by zero");
        }
        if (val1 == Integer.MIN_VALUE && val2 == -1) {
            throw new OverflowException("overflow");
        }
        return val1 / val2;
    }

    public CheckedDivide(Operations op1, Operations op2) {
        super(op1, op2);
    }
    @Override
    public String operationsType() {
        return "/";
    }
}
