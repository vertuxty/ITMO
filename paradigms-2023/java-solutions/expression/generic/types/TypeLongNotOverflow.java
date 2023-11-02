package expression.generic.types;

public class TypeLongNotOverflow implements AbstractTypes<Long> {
    @Override
    public Long add(Long x, Long y) {
        return x + y;
    }

    @Override
    public Long min(Long val1, Long val2) {
        return val1 < val2 ? val1 : val2;
    }

    @Override
    public Long mod(Long x, Long y) {
        return (long) x%y;
    }

    @Override
    public Long abs(Long x) {
        return Math.abs(x);
    }

    @Override
    public Long square(Long x) {
        return x*x;
    }

    @Override
    public Long max(Long val1, Long val2) {
        return val1 < val2 ? val2 : val1;
    }

    @Override
    public Long parseConstant(String constant) {
        return Long.parseLong(constant);
    }

    @Override
    public Long subtract(Long x, Long y) {
        return x - y;
    }

    @Override
    public Long multiply(Long x, Long y) {
        return x * y;
    }

    @Override
    public Long divide(Long x, Long y) {
        return x / y;
    }

    @Override
    public Long count(Long x) {
        return (long) Long.bitCount(x);
    }

    @Override
    public Long unary(Long x) {
        return -x;
    }
}
