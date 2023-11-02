package expression.generic.types;

public class TypeFloatNotOverflow implements AbstractTypes<Float>{

    @Override
    public Float add(Float val1, Float val2) {
        return val1 + val2;
    }

    @Override
    public Float min(Float val1, Float val2) {
        return Math.min(val1, val2);
    }

    @Override
    public Float mod(Float x, Float y) {
        return x%y;
    }

    @Override
    public Float abs(Float x) {
        return Math.abs(x);
    }

    @Override
    public Float square(Float x) {
        return (float) x*x;
    }

    @Override
    public Float max(Float val1, Float val2) {
        return Math.max(val1, val2);
    }

    @Override
    public Float parseConstant(String constant) {
        return Float.parseFloat(constant);
    }

    @Override
    public Float subtract(Float val1, Float val2) {
        return val1 - val2;
    }

    @Override
    public Float multiply(Float val1, Float val2) {
        return val1 * val2;
    }

    @Override
    public Float divide(Float val1, Float val2) {
        return val1 / val2;
    }

    @Override
    public Float count(Float val) {
        return (float) Integer.bitCount(Float.floatToIntBits(val));
    }

    @Override
    public Float unary(Float val) {
        return -val;
    }
}
