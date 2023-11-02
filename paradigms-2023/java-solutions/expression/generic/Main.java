package expression.generic;

import expression.generic.ExpressionParser;
import expression.exceptions.ParsingExceptions;
import expression.generic.types.*;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws Exception {
        AbstractTypes<Double> calcs = new TypeDouble();
        GenericTabulator newTab = new GenericTabulator();
        Operations<Double> parser = new ExpressionParser<>(new TypeDouble()).parse("(((1763888844 / 1874634817) min (z min 905198484)) max ((z min -1365994525) * (z * 588916679)))");
        System.err.println(parser.evaluate(new TypeDouble(), calcs.parseConstant("-4") ,calcs.parseConstant("-8"), calcs.parseConstant("0")));
//        System.err.println(2147483647*2147483647);
        System.err.println(parser.toString());
//        System.out.println(Arrays.deepToString(newTab.tabulate("i", "(2 + 7 + -x -x/-10*(-100))", -2, 2, -2, 2, -2, 2)));
    }
}
