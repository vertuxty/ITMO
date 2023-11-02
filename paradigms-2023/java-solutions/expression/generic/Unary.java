package expression.generic;

import expression.generic.types.AbstractTypes;

public class Unary<T> implements Operations<T>{
    private final Operations<T> operation;
    private final boolean hasSkobka;
    public Unary(Operations<T> operation, boolean hasSkobka) {
        this.operation = operation;
        this.hasSkobka = hasSkobka;
    }

    @Override
    public T evaluate(AbstractTypes<T> calcs,T x, T y, T z) {
        return calcs.unary(operation.evaluate(calcs, x, y, z));
    }

    @Override
    public T evaluate(AbstractTypes<T> calcs, T x) {
        return calcs.unary(operation.evaluate(calcs, x));
    }

    @Override
    public String toString() {
        if (hasSkobka) {
            return "-" + operation.toString();
        }
        return "-" + "(" + operation.toString() + ")";
    }
}
