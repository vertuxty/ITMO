package expression;

public class Set extends BinaryOperations {

//    private Operations operation;
//    private Operations parsed;

    public Set(Operations operation, Operations parsed) {
//        this.operation = operation;
//        this.parsed = parsed;
        super(operation, parsed);
    }

//    @Override
//    public String toString() {
//        return "(" + operations1.toString() + " set " + parsed.toString() + ")";
//    }
    @Override
    public String operationsType() {
        return "set";
    }

    @Override
    public int evaluate(int x) {
        return operations1.evaluate(x) | (1 << operations2.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return operations1.evaluate(x, y, z) | (1 << operations2.evaluate(x, y, z));
    }
}
