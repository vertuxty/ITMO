package expression.generic.types;

public class TypeIntNotOverflow implements AbstractTypes<Integer> {

    @Override
    public Integer add(Integer x, Integer y) {
        return x + y;
    }

    @Override
    public Integer min(Integer val1, Integer val2) {
        return val1 < val2 ? val1 : val2;
    }

    @Override
    public Integer mod(Integer x, Integer y) {
        return x%y;
    }

    @Override
    public Integer abs(Integer x) {
        return Math.abs(x);
    }

    @Override
    public Integer square(Integer x) {
        return x*x;
    }

    @Override
    public Integer max(Integer val1, Integer val2) {
        return val1 < val2 ? val2 : val1;
    }

    @Override
    public Integer parseConstant(String constant) {
        return Integer.parseInt(constant);
    }

    @Override
    public Integer subtract(Integer x, Integer y) {
        return x - y;
    }

    @Override
    public Integer multiply(Integer x, Integer y) {
        return x * y;
    }

    @Override
    public Integer divide(Integer x, Integer y) {
        return x / y;
    }

    @Override
    public Integer count(Integer x) {
        return Integer.bitCount(x);
    }

    @Override
    public Integer unary(Integer x) {
        return -x;
    }
}
