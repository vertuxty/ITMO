package expression.generic.types;

public class TypeShortNotOverflow implements AbstractTypes<Short> {
    @Override
    public Short add(Short x, Short y) {
        return (short) (x + y);
    }

    @Override
    public Short min(Short val1, Short val2) {
        return val1 < val2 ? val1 : val2;
    }

    @Override
    public Short mod(Short x, Short y) {
        return (short) (x%y);
    }

    @Override
    public Short abs(Short x) {
        return (short) Math.abs(x);
    }

    @Override
    public Short square(Short x) {
        return (short) (x*x);
    }

    @Override
    public Short max(Short val1, Short val2) {
        return val1 < val2 ? val2 : val1;
    }

    @Override
    public Short parseConstant(String constant) {
        return (short) Integer.parseInt(constant);
    }

    @Override
    public Short subtract(Short x, Short y) {
        return (short) (x - y);
    }

    @Override
    public Short multiply(Short x, Short y) {
        return (short) (x * y);
    }

    @Override
    public Short divide(Short x, Short y) {
        return (short) (x / y);
    }

    @Override
    public Short count(Short x) {
        return (short) Integer.bitCount(x & 0xffff);
    }

    @Override
    public Short unary(Short x) {
        return (short)-x;
    }
}
