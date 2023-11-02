package expression.generic;

import expression.generic.types.AbstractTypes;

public interface Operations<T> {
    T evaluate(AbstractTypes<T> calcs, T x, T y, T z);
    T evaluate(AbstractTypes<T> calcs, T x);
    String toString();
}
