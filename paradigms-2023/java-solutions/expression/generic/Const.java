package expression.generic;

import expression.generic.types.AbstractTypes;

import java.util.Objects;

public class Const<T> implements Operations<T> {
    private final String constant;
    public Const(String constant) {
        this.constant = constant;
    }


    @Override
    public T evaluate(AbstractTypes<T> calcs, T x, T y, T z) {
        return calcs.parseConstant(constant);
    }

    @Override
    public T evaluate(AbstractTypes<T> calcs, T x) {
        return calcs.parseConstant(constant);
    }

    @Override
    public String toString() {
        return constant;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.constant);
    }

    public boolean equals(Const<T> obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Const<T> tmp = obj;
        return constant.equals(tmp.constant);
    }
}
