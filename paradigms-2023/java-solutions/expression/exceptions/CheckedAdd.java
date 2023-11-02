package expression.exceptions;

import expression.BinaryOperations;
import expression.Operations;
import expression.TripleExpression;
import expression.Variable;

import java.awt.desktop.SystemSleepEvent;

public class CheckedAdd extends BinaryOperations {

    @Override
    public int evaluate(int x, int y, int z) {
        int val1 = operations1.evaluate(x, y, z);
        int val2 = operations2.evaluate(x, y, z);
        return checkInAdd(val1, val2);
    }

    public CheckedAdd(Operations operations1, Operations operations2) {
        super(operations1, operations2);
    }

    @Override
    public int evaluate(int x) {
        int val1 = operations1.evaluate(x);
        int val2 = operations2.evaluate(x);
        return checkInAdd(val1, val2);
    }

    private int checkInAdd(int val1, int val2) {
//        System.out.println("ADD");
        if ((val1 > 0 && val2 > 0 && Integer.MAX_VALUE - val1 < val2)
                || (val1 < 0 && val2 < 0 && Integer.MIN_VALUE - val1 > val2)) {
            throw new OverflowException("overflow");
        }
        return val1 + val2;
    }
    @Override
    public String operationsType() {
        return "+";
    }
}
