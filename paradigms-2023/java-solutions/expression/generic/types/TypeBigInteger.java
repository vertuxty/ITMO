package expression.generic.types;

import java.math.BigInteger;

public class TypeBigInteger implements AbstractTypes<BigInteger> {

    @Override
    public BigInteger add(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    public BigInteger min(BigInteger val1, BigInteger val2) {
        return val1.compareTo(val2) <= 0 ? val1 : val2;
    }

    @Override
    public BigInteger mod(BigInteger x, BigInteger y) {
        return x.mod(y);
    }

    @Override
    public BigInteger abs(BigInteger x) {
        return x.abs();
    }

    @Override
    public BigInteger square(BigInteger x) {
        return x.multiply(x);
    }

    @Override
    public BigInteger max(BigInteger val1, BigInteger val2) {
        return val1.compareTo(val2) >= 0 ? val1 : val2;
    }

    @Override
    public BigInteger parseConstant(String constant) {
        return new BigInteger(constant);
    }

    @Override
    public BigInteger subtract(BigInteger x, BigInteger y) {
        return x.subtract(y);
    }

    @Override
    public BigInteger multiply(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    @Override
    public BigInteger divide(BigInteger x, BigInteger y) {
        return x.divide(y);
    }

    @Override
    public BigInteger count(BigInteger x) {
        return new BigInteger(String.valueOf(x.bitCount()));
    }

    @Override
    public BigInteger unary(BigInteger x) {
        return x.negate();
    }
}
