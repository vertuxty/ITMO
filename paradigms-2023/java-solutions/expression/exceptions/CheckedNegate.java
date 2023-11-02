package expression.exceptions;

import expression.BinaryOperations;
import expression.Operations;
import expression.TripleExpression;
import expression.Unary;

public class CheckedNegate implements TripleExpression, Operations {

    private Operations operations;
//    private boolean hasSkobka;
    @Override
    public int evaluate(int x, int y, int z) {
        int val = operations.evaluate(x, y, z);
        return checkNegate(val);
    }

    public int evaluate(int x) {
        int val = operations.evaluate(x);
        return checkNegate(val);
    }

    public CheckedNegate(Operations operations) {
        this.operations = operations;
//        this.hasSkobka = hasSkobka;
    }

    private int checkNegate(int val) {
//        System.out.println("NEGATE");
        if (val == Integer.MIN_VALUE) {
            throw new OverflowException("overflow");
        }
        return -val;
    }

    @Override
    public String toString() {
        return operations.toString(); //так ведь? вроде да, т.к я буду вызывать метод toString у Wrapped, который уже в свою очередь вызовет нужный мне)
//        return WrapperCheckNegate.toString();
    }
}
