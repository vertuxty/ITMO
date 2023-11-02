package expression.generic.types;

import expression.exceptions.DivideByZeroException;
import expression.exceptions.OverflowException;

public class TypeInt implements AbstractTypes<Integer> {

    final boolean overflowFlag;

    public TypeInt(boolean overflowFlag) {
        this.overflowFlag = overflowFlag;
    }

    @Override
    public Integer add(Integer val1, Integer val2) {
        if (overflowFlag) {
            if ((val1 > 0 && val2 > 0 && Integer.MAX_VALUE - val1 < val2)
                    || (val1 < 0 && val2 < 0 && Integer.MIN_VALUE - val1 > val2)) {
                throw new OverflowException("overflow");
            }
        }
        return val1 + val2;
    }

    @Override
    public Integer min(Integer val1, Integer val2) {
        return val1 <= val2 ? val1 : val2;
    }

    @Override
    public Integer mod(Integer x, Integer y) {
        return x%y;
    }

    @Override
    public Integer abs(Integer x) {
        if ( overflowFlag && x == Integer.MIN_VALUE) {
            throw new OverflowException("overflow");
        }
        return Math.abs(x);
    }

    @Override
    public Integer square(Integer x) {
        return multiply(x, x);
    }

    @Override
    public Integer max(Integer val1, Integer val2) {
        return val1 <= val2 ? val2 : val1;
    }

    @Override
    public Integer subtract(Integer val1, Integer val2) {
        if (overflowFlag) {
            if (val2 == Integer.MIN_VALUE && val1 >= 0) {
                throw new OverflowException("overflow");
            }
            if (val1 > 0 && val2 < 0 && Integer.MAX_VALUE - val1 < -val2) {
                throw new OverflowException("overflow");
            }
            if (val1 < 0 && val2 > 0 && Integer.MIN_VALUE - val1 > -val2) {
                throw new OverflowException("overflow");
            }
        }
        return val1 - val2;
    }

    @Override
    public Integer multiply(Integer val1, Integer val2) {
        if (val1 == 0 || val2 == 0) {
            return 0;
        }
        if (overflowFlag) {
            if (val1 > 0 && val2 > 0 && Integer.MAX_VALUE / val1 < val2) {
                throw new OverflowException("overflow");
            }
            if (val1 < 0 && val2 == Integer.MIN_VALUE || val2 < 0 && val1 == Integer.MIN_VALUE) {
                throw new OverflowException("overflow");
            }
            if (val1 < -1 && val2 > 1 && Integer.MIN_VALUE / val1 < val2) {
                throw new OverflowException("overflow");
            }
            if (val1 > 1 && val2 < -1 && Integer.MIN_VALUE / val2 < val1) {
                throw new OverflowException("overflow");
            }
            if (val1 < 0 && val2 < 0 && Integer.MAX_VALUE / val1 > val2) {
                throw new OverflowException("overflow");
            }
        }
        return val1 * val2;
    }

    @Override
    public Integer divide(Integer val1, Integer val2) {
        if (overflowFlag) {
            if (val2 == 0) {
                throw new DivideByZeroException("division by zero");
            }
            if (val1 == Integer.MIN_VALUE && val2 == -1) {
                throw new OverflowException("overflow");
            }
        }
        return val1 / val2;
    }

    public Integer parseConstant(String constant) {
        int value = Integer.parseInt(constant);
        if (overflowFlag) {
            try {
                if (!String.valueOf(value).equals(constant)) {
                    throw new OverflowException("overflow");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Wrong argument got in Integer value: " + constant);
            }
        }
        return Integer.parseInt(constant);
    }

//    @Override
//    public Integer set(Integer val1, Integer val2) {
//        return val1 | (1 << val2);
//    }
//
//    @Override
//    public Integer clear(Integer val1, Integer val2) {
//        return val1 & ~(1 << val2);
//    }

    @Override
    public Integer count(Integer val) {
        return Integer.bitCount(val);
    }

    @Override
    public Integer unary(Integer val) {
        if (val == Integer.MIN_VALUE && overflowFlag) {
            throw new OverflowException("overflow");
        }
        return -val;
    }

}
