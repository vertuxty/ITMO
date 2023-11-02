package expression;

public class Clear implements Operations{

    private Operations operation;
    private Operations parsed;

    public Clear(Operations operation, Operations parsed) {
        this.operation = operation;
        this.parsed = parsed;
    }

    @Override
    public String toString() {
        return "(" + operation.toString() + " clear " + parsed.toString() + ")";
    }

    @Override
    public int evaluate(int x) {
        return operation.evaluate(x) & ~(1 << parsed.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return operation.evaluate(x, y, z) & ~(1 << parsed.evaluate(x, y, z));
    }
}
