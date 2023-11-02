package expression.exceptions;

import java.util.Objects;
import expression.Operations;

public class Variable implements Operations {
    private final String var;
    public Variable(String x) throws ParsingExceptions {
        this.var = x;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Variable tmp = (Variable) obj;
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
    public int evaluate(int x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (this.var.equals("x")) {
            return x;
        } else if (this.var.equals("y")) {
            return y;
        } else {
            return z;
        }
    }
}
