package expression.exceptions;

import expression.Operations;

public class WrapperCheckNegate implements Operations {
    private Operations operationsTest;
    private boolean flagTest;
    public WrapperCheckNegate(Operations operationsTest, boolean flag) {
        this.operationsTest = operationsTest;
        this.flagTest = flag;
    }
    @Override
    public String toString() {
        if (flagTest) {
            return "-" + operationsTest.toString();
        }
        return "-" + "(" + operationsTest.toString() + ")";
    }

    @Override
    public int evaluate(int x) {
        return operationsTest.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return operationsTest.evaluate(x, y, z);
    }
}
