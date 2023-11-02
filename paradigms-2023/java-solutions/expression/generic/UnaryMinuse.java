package expression.generic;

import expression.generic.Operations;
import expression.generic.types.AbstractTypes;

public class UnaryMinuse<T> implements Operations<T> {
    private int constant;

    public UnaryMinuse(int constant) {
        this.constant = constant;
    }

    public int priority() {
        return 1;
    }

    public String operationsType() {
        return "-";
    }

    @Override
    public T evaluate(AbstractTypes<T> calcs, T x, T y, T z) {
        return null;
    }

    @Override
    public T evaluate(AbstractTypes<T> calcs, T x) {
        return null;
    }

    @Override
    public String toString() {
        return "-" + constant;
    }
}
