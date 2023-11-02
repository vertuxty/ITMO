package expression.generic;

import expression.generic.types.AbstractTypes;

public class Subtract<T> extends BinaryOperations<T> {

    public Subtract(Operations<T> operations1, Operations<T> operations2) {
        super(operations1, operations2);
    }

    @Override
    public T makes(AbstractTypes<T> calcs, T x, T y) {
        return calcs.subtract(x, y);
    }

    @Override
    public String operationsType() {
        return "-";
    }
}
