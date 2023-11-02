package expression.parser;

import expression.*;
import expression.Operations;

public class ExpressionParser implements TripleParser {
    public TripleExpression parse(String expression) {
        return parse(new StringSourse(expression));
    }

    public TripleExpression parse(CharSource source) {
        return new ParseExpression(source).parseExpression();
    }

    private static class ParseExpression extends BaseParser implements TripleParser {
        private boolean hasUnaryMinus = false;
        public ParseExpression(CharSource source) {
            super(source);
        }
        private Operations parseExpression() {
            Operations expression = parseEquations(null);
            while (!eof()) {
                if (test(')')) {
                    return parseEquations(expression);
                }
                expression = parseEquations(expression);
            }
            if (eof()) {
                return expression;
            } else {
                throw new IllegalArgumentException("ERROR in parseExpression: " + expression);
            }
        }

        private Operations parseEquations(Operations operation) {
            skipWs();
            final Operations expression = parseValue(operation);
            skipWs();
            return expression;
        }

        private Operations parseUntilFour() { // Using this for Set and Clear.
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
                throw new IllegalArgumentException("ERROR in SetAndClear: " + expression);
            }
        }
        public Operations parseValue(Operations operation) {
            skipWs();
            if (test('(')) {
                take();
                Operations opInserted = parseExpression();
                skipWs();
                return opInserted;
            }
            if (take(')')) {
                skipWs();
                return operation;
            }
            if (test('-')) {
                skipWs();
                return parseThirdPriority(operation);
            } else if (test('+')) {
                skipWs();
                return parseThirdPriority(operation);
            } else if (test('*') || test('/')) {
                skipWs();
                return parseSecondPriory(operation);
            } else if (test('s')) {
                take();
                if (take('e') && take('t')) {
                    return SetAndClear(operation, "set");
                }
                throw new IllegalArgumentException("Not such operation! (SET)");
            } else if (test('c')) {
                take();
                if (take('l') && take('e') && take('a') && take('r')) {
                    return SetAndClear(operation, "clear");
                }
                throw new IllegalArgumentException("Not such operation! (CLEAR)");
            } else if (Character.isLetter(getCh())) {
                skipWs();
                char var = getCh();
                take();
                return parseVariable(var);
            } else if (betweenZeroNine()) {
                skipWs();
                return parseConst();
            } else {
                return operation;
            }
        }
        // A | (1 << b)
        // A & ~(1 << b)

        private Operations parseThirdPriority(Operations operation) {
            if (test('-')) {
                take();
                if (operation == null) {
                    if (Character.isDigit(getCh())) {
                        hasUnaryMinus = true;
                        return parseConst();
                    }
                    if (Character.isLetter(getCh())) {
                        return new Unary(parseValue(null), true);
                    }
                    if (test('(') || Character.isWhitespace(getCh())) {
                        skipWs();
                        return new Unary(parseValue(null), false);
                    }
                    skipWs();
                    if (test('-')) {
                        return new Unary(parseThirdPriority(null), false);
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

        private Operations SetAndClear(Operations operation, String operationType) { // Modification SetClear
            return parseOperations(operationType, operation, parseUntilFour());
        }

        private Operations AddAndSubtract(String operationType, Operations operation) {
            skipWs();
            Operations parsed = parseValue(null);
            skipWs();
            if ((test('*') || test('/') )) {
                return parseOperations(operationType, operation, parseSecondPriory(parsed));
            }
            return parseOperations(operationType, operation, parsed);
        }

        private Operations multiplyAndDivide(char operationType, Operations operation) {
            skipWs();
            Operations parsed = parseValue(null);
            skipWs();
            if (test('*') || test('/')){
                return parseSecondPriory(parseOperations(Character.toString(operationType), operation, parsed));
            } else {
                return parseOperations(Character.toString(operationType), operation, parsed);
            }
        }

        private Operations parseSecondPriory(Operations operation) {
            skipWs();
            if (test('*')) {
                take();
                return multiplyAndDivide('*', operation);
            }
            if (test('/')) {
                take();
                return multiplyAndDivide('/', operation);
            }
            throw new AssertionError("ERORO in parseSecondPriory");
        }

        private Operations parseOperations(String operationsType, Operations operations1, Operations operations2) {
            return switch (operationsType) {
                case "+" -> new Add(operations1, operations2);
                case "-" -> new Subtract(operations1, operations2);
                case "*" -> new Multiply(operations1, operations2);
                case "/" -> new Divide(operations1, operations2);
                case "set" -> new Set(operations1, operations2);
                case "clear" -> new Clear(operations1, operations2);
                default -> throw new AssertionError("Invalid input operation: " + operationsType);
            };
        }

        private Operations parseVariable(char var) {
            skipWs();
            hasUnaryMinus = false;
            return switch (var) {
                case 'x' -> new Variable("x");
                case 'y' -> new Variable("y");
                case 'z' -> new Variable("z");
                default -> throw new IllegalArgumentException("Wrong variable name! Got " + var + ", expected: x or y or z");
            };
        }
        private Operations parseConst() {
            skipWs();
            StringBuilder newConst = new StringBuilder();
            while (betweenZeroNine()) {
                newConst.append(getCh());
                take();
            }
            if (hasUnaryMinus) {
                newConst.insert(0, "-");
                hasUnaryMinus = false;
            }
            skipWs();
            return new Const(Integer.parseInt(newConst.toString()));
        }
        @Override
        public TripleExpression parse(String expression) {
            return null;
        }
    }
}
