package expression.generic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import expression.exceptions.DivideByZeroException;
import expression.generic.ExpressionParser;
import expression.exceptions.OverflowException;
import expression.exceptions.ParsingExceptions;
import expression.generic.types.*;

public class GenericTabulator implements Tabulator {

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        AbstractTypes<?> modeRun = switch (mode) {
            case "i" -> new TypeInt(true);
            case "d" -> new TypeDouble();
            case "bi" -> new TypeBigInteger();
            case "u" -> new TypeInt(false);
            case "l" -> new TypeLongNotOverflow();
            case "s" -> new TypeShortNotOverflow();
            case "f" -> new TypeFloatNotOverflow();
            default -> throw new IllegalArgumentException("Wrong mode!");
        };
//        System.err.println(mode);
        return evaluateTabulateTable(modeRun, expression, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] evaluateTabulateTable(AbstractTypes<T> modeRun, String expression ,int x1, int x2, int y1, int y2, int z1, int z2) throws ParsingExceptions {
        Object[][][] tabulateTable = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        Operations<T> parser = new ExpressionParser<>(modeRun).parse(expression);
//        <R exrends modeRun.getClass()>
//        System.err.println(expression);
//        System.err.println(modeRun.getClass());
        for (int i = 0; i <= x2 - x1; i++) {
            for (int j = 0; j <= y2 - y1; j++) {
                for (int k = 0; k <= z2 - z1; k++) {
//                    System.err.println(i + " " + j + " "+ k);
//                    System.err.println(parser.toString());
                    try {
//                        System.err.println(parser.toString() + " parsed Expr " + modeRun.getClass());
                        T result = parser.evaluate(modeRun, modeRun.parseConstant(String.valueOf(x1 + i)), modeRun.parseConstant(String.valueOf(y1 + j)), modeRun.parseConstant(String.valueOf(z1 + k)));
                        tabulateTable[i][j][k] = result;
                    } catch (OverflowException | ArithmeticException | DivideByZeroException e) {
                        tabulateTable[i][j][k] = null;
                    }
                }
            }
        }
//        System.out.println(Arrays.deepToString(tabulateTable));
        return tabulateTable;
    }
}
