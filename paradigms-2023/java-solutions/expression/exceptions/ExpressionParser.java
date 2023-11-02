package expression.exceptions;

import expression.TripleExpression;
import expression.Operations;

public class ExpressionParser implements TripleParser {

    @Override
    public TripleExpression parse(String expression) throws ParsingExceptions {
        return parse(new StringSourse(expression));
    }

    public TripleExpression parse(CharSource source) throws ParsingExceptions {
        return new ParseExpression(source).parseExpression();
    }

    private static class ParseExpression extends BaseParser implements TripleParser {
        private boolean hasUnaryMinus = false;
        private int close = 0;
        private int open = 0;
        public ParseExpression(CharSource source) {
            super(source);
        }
        private Operations parseExpression() throws ParsingExceptions {
            Operations expression = parseEquations(null);
            while (!eof()) {
                if (close + 1 <= open && test(')')) {
                    return parseEquations(expression);
                } else if (test(')') && open < close + 1) {
                    throw new ParenthesisException("Not enough opened brackets");
                }
                expression = parseEquations(expression);
            }
            if (close < open) {
                throw new ParenthesisException("No closed bracket");
            }
            if (eof()) {
                return expression;
            } else {
                throw new IllegalArgumentException("It's end of file, but there were found: " + expression);
            }
        }

        private Operations parseEquations(Operations operation) throws ParsingExceptions {
            skipWs();
            final Operations expression = parseValue(operation);
            skipWs();
            return expression;
        }

        /*
        * Пояснение к названиям методов:
        * parseSecondPriory - парсим выражения после знака * или /
        * parseThirdPriority - парсим выражения после знака - или +
        * parseUntilFour - парсим выражения после set или clear;
        * */

        private Operations parseUntilFour() throws ParsingExceptions { // Using this for Set and Clear.
            Operations expression = parseEquations(null);
            while (!eof()) {
                if (test('s') || test('c') || test(')')) {
                    return expression;
                }
                expression = parseEquations(expression);
            }
            if (eof()) {
                return expression;
            } else if (test('s')) {
                return expression;
            } else if (test('c')) {
                return expression;
            } else {
                throw new WrongOperationError("No such operation: " + expression);
            }
        }
        private Operations CheckOnMiss(Operations operation, String message) throws ParsingExceptions {
            if (operation == null) {
                skipWs();
                throw new MissArgumentException(message);
            }
            skipWs();
            return operation;
        }
        private Operations CheckExceptionSetClear(Operations operation, String operationType) throws ParsingExceptions {
            if (operation == null) {
                throw new MissArgumentException("Miss argument before " + operationType);
            }
            return SetAndClear(operation, operationType);
        }
        public Operations parseValue(Operations operation) throws ParsingExceptions {
            skipWs();
            if (test('(')) {
                open++;
                take();
                Operations opInserted = CheckOnMiss(parseExpression(),"Empty equation in brackets") ;
                skipWs();
                return opInserted;
            }
            if (take(')')) {
                close++;
                skipWs();
                return CheckOnMiss(operation, "Empty eqution in brakets");
            }
            if (test('-')) {
                skipWs();
                return CheckOnMiss(parseThirdPriority(operation), "No arguments after '-'");
            } else if (test('+')) {
                Operations parsed = CheckOnMiss(operation, "Miss argument before +");
                skipWs();
                return parseThirdPriority(parsed);
            } else if (test('*') || test('/')) {
                Operations parsed = CheckOnMiss(operation, "Miss argument before: " + getCh());
                skipWs();
                return parseSecondPriory(parsed);
            } else if (test('s')) {
                take();
                if (take('e') && take('t') && !Character.isLetter(getCh())) { // 2 set3
                    return CheckExceptionSetClear(operation, "set");
                }
                throw new WrongOperationError("Not such operation!: set" + getCh());
            } else if (test('c')) {
                take();
                if (test('l')) {
                    if (take('l') && take('e') && take('a') && take('r') && !Character.isLetter(getCh()) && !Character.isDigit(getCh())) {
                        return CheckExceptionSetClear(operation, "clear");
                    }
                    throw new WrongOperationError("Not such operation!: clear" + getCh());
                }
                if (test('o')) {
                    if (take('o') && take('u') && take('n') && take('t') && !Character.isLetter(getCh()) && !Character.isDigit(getCh())) {
                        return CountParse(operation);
                    }
                    throw new WrongOperationError("No such opeartion!: count" + getCh());
                }
            } else if (Character.isLetter(getCh())) {
                skipWs();
                char var = getCh();
                take();
                return parseVariable(var);
            } else if (betweenZeroNine()) {
                skipWs();
                return parseConst();
            } else if ((!Character.isDigit(getCh()) && !Character.isLetter(getCh()) && !Character.isWhitespace(getCh())
                    && getCh() != '-' && getCh() != '+' && getCh() != '*' && getCh() != '/'
                    && getCh() != ')' && getCh() != '(' && getCh() != 's' && getCh() != 'c')
                    && getCh() != END) {
                throw new WrongCharacterException("No such symbol in arithmetical equation: " + getCh());
            }
            return CheckOnMiss(operation, "Miss last argument");
        }

        // A | (1 << b)
        // A & ~(1 << b)

        private Operations parseThirdPriority(Operations operation) throws ParsingExceptions {
            if (test('-')) {
                take();
                if (operation == null) {
                    if (Character.isDigit(getCh())) {
                        hasUnaryMinus = true;
                        return parseConst();
                    }
                    if (Character.isLetter(getCh())) {
                        return new WrapperCheckNegate(new CheckedNegate(parseValue(null)), true);
                    }
                    if (test('(') || Character.isWhitespace(getCh())) {
                        skipWs();
                        return new WrapperCheckNegate(new CheckedNegate(parseValue(null)), false);
                    }
                    skipWs();
                    if (test('-')) {
                        return new WrapperCheckNegate(new CheckedNegate(parseThirdPriority(null)), false);
                    }
                } else {
                    skipWs();
                    if (test('-')) {
                        Operations parsed = parseThirdPriority(null);
                        skipWs();
                        if (test('*') || test('/')) {
                            return parseOperations("-", operation, parseSecondPriory(parsed));
                        }
                        return parseOperations("-", operation, parsed);
                    }
                    return AddAndSubtract("-", operation);
                }
            }
            if (operation != null) {
                if (test('+')) {
                    take();
                    return AddAndSubtract("+", operation);
                }
            }
            return operation;
        }

        private Operations CountParse(Operations operation) throws ParsingExceptions {
            skipWs();
            Operations tmp = parseValue(null);
            skipWs();
            Operations parsed = CheckOnMiss(tmp, "No argument after count");
            return new Count(parsed);
        }

        private Operations SetAndClear(Operations operation, String operationType) throws ParsingExceptions { // Modification SetClear
            skipWs();
            Operations parsed = CheckOnMiss(parseUntilFour(), "No argument after " + operationType);
            return parseOperations(operationType, operation, parsed);
        }

        private Operations AddAndSubtract(String operationType, Operations operation) throws ParsingExceptions {
            skipWs();
            Operations parsed = CheckOnMiss(parseValue(null), "No argument in equation after " + operationType);
            skipWs();
            if ((test('*') || test('/') )) {
                return parseOperations(operationType, operation, parseSecondPriory(parsed));
            }
            return parseOperations(operationType, operation, parsed);
        }

        private Operations multiplyAndDivide(char operationType, Operations operation) throws ParsingExceptions {
            skipWs();
            Operations parsed = CheckOnMiss(parseValue(null), "No argument after " + operationType); // (x * / y) - как выдать ошибку?
            if (test('*') || test('/')){
                return parseSecondPriory(parseOperations(Character.toString(operationType), operation, parsed));
            } else {
                return parseOperations(Character.toString(operationType), operation, parsed);
            }
        }

        private Operations parseSecondPriory(Operations operation) throws ParsingExceptions {
            skipWs();
            if (test('*')) {
                take();
                return multiplyAndDivide('*', operation);
            }
            if (test('/')) {
                take();
                return multiplyAndDivide('/', operation);
            }
            String message = "Expected: '*' or '/', got: " + getCh();
            throw new WrongOperationError(message);
        }

        private Operations parseOperations(String operationsType, Operations operations1, Operations operations2) throws ParsingExceptions {
            String message = "Invalid input operation: " + operationsType;
            return switch (operationsType) {
                case "+" -> new CheckedAdd(operations1, operations2);
                case "-" -> new CheckedSubtract(operations1, operations2);
                case "*" -> new CheckedMultiply(operations1, operations2);
                case "/" -> new CheckedDivide(operations1, operations2);
                case "set" -> new CheckedSet(operations1, operations2);
                case "clear" -> new CheckedClear(operations1, operations2);
                default -> throw new WrongOperationError(message);
            };
        }

        private Operations parseVariable(char var) throws ParsingExceptions {
            skipWs();
            hasUnaryMinus = false;
            String message = "Wrong variable name!: " + var;
            return switch (var) {
                case 'x' -> new Variable("x");
                case 'y' -> new Variable("y");
                case 'z' -> new Variable("z");
                default -> throw new WrongCharacterException(message);
            };
        }
        private Operations parseConst() throws ParsingExceptions {
            skipWs();
            int value = 0;
            StringBuilder newConst = new StringBuilder();
            while (betweenZeroNine()) {
                newConst.append(getCh());
                take();
            }
            if (test('s')) {
                throw new WhitespaceException("No whitespace between " + newConst + " and set: " + newConst + "set");
            }
            if (test('c')) {
                take('c');
                if (test('l')) {
                    throw new WhitespaceException("No whitespace between " + newConst + " and clear: " + newConst + "clear");
                }
                if (test('o')) {
                    throw new WhitespaceException("No whitespace between " + newConst + " and count: " + newConst + "clear");
                }
            }
            if (hasUnaryMinus) {
                newConst.insert(0, "-");
                hasUnaryMinus = false;
            }
            skipWs();
            try {
                value = Integer.parseInt(newConst.toString());
            } catch (NumberFormatException e) {
                throw new OverflowException("overflow for input string: " + newConst);
            }
            if (betweenZeroNine()) {
                throw new NumberException("Spaces in numbers");
            }
            return new Const(value);
        }
        @Override
        public TripleExpression parse(String expression) throws ParsingExceptions {
            return null;
        }
    }
}
