package expression.generic;

import expression.generic.types.AbstractTypes;

public class Abs<T> implements Operations<T> {
    Operations<T> operation;
    public Abs(Operations<T> operation) {
        this.operation = operation;
    }

    public String toString() {
        return "abs(" + operation.toString() + ")";
    }

    @Override
    public T evaluate(AbstractTypes<T> calcs, T x, T y, T z) {
        return calcs.abs(operation.evaluate(calcs, x, y, z));
    }

    @Override
    public T evaluate(AbstractTypes<T> calcs, T x) {
        return calcs.abs(operation.evaluate(calcs, x));
    }
}
