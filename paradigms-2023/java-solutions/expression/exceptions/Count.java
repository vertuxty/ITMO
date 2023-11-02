package expression.exceptions;

import expression.Operations;
import expression.TripleExpression;

public class Count implements Operations, TripleExpression {
    Operations operations;

    public Count(Operations operation) {
        this.operations = operation;
    }

    @Override
    public int evaluate(int x) {
        return Integer.bitCount(operations.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return Integer.bitCount(operations.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "count(" + operations.toString() + ")";
    }
}
