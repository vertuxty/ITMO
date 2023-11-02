package expression.generic;

import expression.generic.types.AbstractTypes;

public class Min<T> extends BinaryOperations<T> {
    public Min(Operations<T> operations1, Operations<T> operations2) {
        super(operations1, operations2);
    }

    @Override
    public T makes(AbstractTypes<T> calcs, T x, T y) {
        return calcs.min(x, y);
    }
    @Override
    public String operationsType() {
        return "min";
    }
}
