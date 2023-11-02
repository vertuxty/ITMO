package expression;

public class Unary implements Operations{
    private Operations operation;
    private boolean hasSkobka;
    public Unary(Operations operation, boolean hasSkobka) {
        this.operation = operation;
        this.hasSkobka = hasSkobka;
    }

    @Override
    public String toString() {
        if (hasSkobka) {
//            hasSkobka = false;
            return "-" + operation.toString();
        }
        return "-" + "(" + operation.toString() + ")";
    }

    @Override
    public int evaluate(int x) {
        return -operation.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -operation.evaluate(x, y, z);
    }
}
