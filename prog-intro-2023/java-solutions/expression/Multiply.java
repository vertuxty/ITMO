package expression;

public class Multiply extends BinaryOperations {

    public Multiply(Operations operations1, Operations operations2) {
        super(operations1, operations2);
    }
    @Override
    public String operationsType() {
        return "*";
    }

    @Override
    public int evaluate(int x) {
        return operations1.evaluate(x) * operations2.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return operations1.evaluate(x, y, z) * operations2.evaluate(x, y, z);
    }
}
