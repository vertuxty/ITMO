package expression.generic.types;

import expression.exceptions.DivideByZeroException;
import expression.exceptions.OverflowException;
public class TypeDouble implements AbstractTypes<Double> {

    @Override
    public Double add(Double val1, Double val2) {
        return val1 + val2;
    }

    @Override
    public Double min(Double val1, Double val2) {
        return Math.min(val1, val2);
    }

    @Override
    public Double mod(Double x, Double y) {
        return x%y;
    }

    @Override
    public Double abs(Double x) {
        return Math.abs(x);
    }

    @Override
    public Double square(Double x) {
        return x*x;
    }

    @Override
    public Double max(Double val1, Double val2) {
        return Math.max(val1, val2);
    }

    @Override
    public Double subtract(Double val1, Double val2) {
        return val1 - val2;
    }

    @Override
    public Double multiply(Double val1, Double val2) {
        return val1 * val2;
    }

    @Override
    public Double divide(Double val1, Double val2) {
        return val1 / val2;
    }

    public Double parseConstant(String constant) {
        return Double.parseDouble(constant);
    }

    @Override
    public Double count(Double val) {
        return (double) Long.bitCount(Double.doubleToLongBits(val));
    }

    @Override
    public Double unary(Double x) {
        return -x;
    }
}
