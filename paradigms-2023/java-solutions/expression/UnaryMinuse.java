package expression;

public class UnaryMinuse implements Operations{
    private int constant;

    public UnaryMinuse(int constant) {
        this.constant = constant;
    }

    public int priority() {
        return 1;
    }

    public String operationsType() {
        return "-";
    }

    @Override
    public String toString() {
        return "-" + constant;
    }


    @Override
    public int evaluate(int x) {
        return -constant;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -constant;
    }
}
