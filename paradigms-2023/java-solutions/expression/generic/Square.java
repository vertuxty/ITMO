package expression.generic;

import expression.generic.types.AbstractTypes;

public class Square<T> implements Operations<T>{
    Operations<T> operation;
    public Square(Operations<T> operation) {
        this.operation = operation;
    }
    @Override
    public T evaluate(AbstractTypes<T> calcs, T x, T y, T z) {
        return calcs.square(operation.evaluate(calcs, x, y, z));
    }

    @Override
    public T evaluate(AbstractTypes<T> calcs, T x) {
        return calcs.square(operation.evaluate(calcs, x));
    }
    @Override
    public String toString() {
        return "square(" + operation.toString() + ")";
    }
}
