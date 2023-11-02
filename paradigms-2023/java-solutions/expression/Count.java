package expression;

import expression.generic.Operations;
import expression.generic.types.AbstractTypes;
//import expression.TripleExpression;

public class Count<T> implements Operations<T> {
    Operations<T> operations;

    public Count(Operations<T> operation) {
        this.operations = operation;
    }
    @Override
    public T evaluate(AbstractTypes<T> calcs, T x, T y, T z) {
        return calcs.count(operations.evaluate(calcs, x, y, z));
    }

    @Override
    public T evaluate(AbstractTypes<T> calcs, T x) {
        return calcs.count(operations.evaluate(calcs, x));
    }

    @Override
    public String toString() {
        return "count(" + operations.toString() + ")";
    }
}
