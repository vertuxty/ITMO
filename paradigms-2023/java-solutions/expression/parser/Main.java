package expression.parser;

import expression.TripleExpression;

public class Main {
    public static void main(String[] args) {
        System.out.println("Expected: ((x+y)+z)");
        System.out.println("Got: ");
        TripleExpression parser = new ExpressionParser().parse("-(-2147483648)");
        System.out.println(parser.evaluate(0, 0, 0));
    }
}
