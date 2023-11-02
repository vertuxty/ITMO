package expression.exceptions;

import java.util.Objects;
import expression.Operations;

public class Const implements Operations {
    private final int constant;
    public Const(int constant) {
        this.constant = constant;
    }

    @Override
    public String toString() {
        return String.valueOf(constant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.constant);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Const tmp = (Const) obj;
        return constant == tmp.constant;
    }

    @Override
    public int evaluate(int x) {
        return constant;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return constant;
    }
}
