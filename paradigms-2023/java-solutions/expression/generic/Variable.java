package expression.generic;

import expression.generic.types.AbstractTypes;

import java.util.Objects;

public class Variable<T> implements Operations<T> {
    private final String var;
    public Variable(String x) {
        this.var = x;
    }

    public boolean equals(Variable<T> obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Variable<T> tmp = obj;
        return var.equals(tmp.var);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.var);
    }

    @Override
    public String toString() {
        return var;
    }

    @Override
    public T evaluate(AbstractTypes<T> calcs, T x) {
        return x;
    }

    @Override
    public T evaluate(AbstractTypes<T> calcs, T x, T y, T z) {
        if (this.var.equals("x")) {
            return x;
        } else if (this.var.equals("y")) {
            return y;
        } else {
            return z;
        }
    }
}
