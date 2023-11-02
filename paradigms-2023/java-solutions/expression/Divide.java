package expression;

public class Divide extends BinaryOperations {

    public Divide(Operations operations1, Operations operations2) {
        super(operations1, operations2);
    }

    @Override
    public String operationsType() {
        return "/";
    }

    public int priority() {
        return 2;
    }

    @Override
    public int evaluate(int x) {
        return operations1.evaluate(x) / operations2.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return operations1.evaluate(x, y, z) / operations2.evaluate(x, y, z);
    }
}
