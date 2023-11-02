package expression.generic;


import expression.exceptions.ParsingExceptions;
import expression.exceptions.ParenthesisException;
import expression.exceptions.MissArgumentException;
import expression.exceptions.WrongOperationError;
import expression.exceptions.WrongCharacterException;
import expression.Count;
import expression.exceptions.NumberException;
import expression.generic.types.AbstractTypes;

public class ExpressionParser<T> {
    AbstractTypes<T> modeRun;
    public ExpressionParser(AbstractTypes<T> modeRun) {
        this.modeRun = modeRun;
    }

    public Operations<T> parse(String expression) throws ParsingExceptions {
        return parse(new StringSourse(expression));
    }

    public Operations<T> parse(CharSource source) throws ParsingExceptions {
        return new ParseExpression<T>(modeRun, source).parseExpression();
    }

    private static class ParseExpression<T> extends BaseParser {
        AbstractTypes<T> modeRun;
        private boolean hasUnaryMinus = false;
        private int close = 0;
        private int open = 0;
        public ParseExpression(AbstractTypes<T> modeRun, CharSource source) {
            super(source);
            this.modeRun = modeRun;
        }
        private Operations<T> parseExpression() throws ParsingExceptions {
            Operations<T> expression = parseEquations(null);
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

        private Operations<T> parseEquations(Operations<T> operation) throws ParsingExceptions {
            skipWs();
            final Operations<T> expression = parseValue(operation);
            skipWs();
            return expression;
        }

        private Operations<T> parseUntilFour() throws ParsingExceptions { // Using this for Set and Clear.
            Operations<T> expression = parseEquations(null);
            while (!eof()) {
                if (test('m') || test('s') || test('c') || test(')')) {
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
            } else if (test('m')) {
                return expression;
            } else {
                throw new WrongOperationError("No such operation: " + expression);
            }
        }
        private Operations<T> CheckOnMiss(Operations<T> operation, String message) throws ParsingExceptions {
            if (operation == null) {
                skipWs();
                throw new MissArgumentException(message);
            }
            skipWs();
            return operation;
        }
        private Operations<T> CheckExceptionSetClearMinMax(Operations<T> operation, String operationType) throws ParsingExceptions {
            if (operation == null) {
                throw new MissArgumentException("Miss argument before " + operationType);
            }
            return SetClearMinMax(operation, operationType);
        }
        public Operations<T> parseValue(Operations<T> operation) throws ParsingExceptions {
            skipWs();
            if (test('(')) {
                open++;
                take();
                Operations<T> opInserted = CheckOnMiss(parseExpression(),"Empty equation in brackets") ;
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
                Operations<T> parsed = CheckOnMiss(operation, "Miss argument before +");
                skipWs();
                return parseThirdPriority(parsed);
            } else if (test('*') || test('/')) {
                String operationType = Character.toString(getCh());
                Operations<T> parsed = CheckOnMiss(operation, "Miss argument before: " + getCh());
                skipWs();
                take();
                return parseSecondPriory(parsed, operationType);
            } else if (test('s')) {
                take();
                if (expect("et") && !Character.isLetter(getCh()) && !Character.isDigit(getCh())) {
                    return CheckExceptionSetClearMinMax(operation, "set");
                }
                if (expect("quare")) {
                    return parseUnaryOperation(operation, "square");
                }
                throw new WrongOperationError("No such opeartion!: " + getCh());
            } else if (test('c')) {
                take();
                if (expect("lear") && !Character.isLetter(getCh()) && !Character.isDigit(getCh())) {
                    return CheckExceptionSetClearMinMax(operation, "clear");
                }
                if (expect("ount") && !Character.isLetter(getCh()) && !Character.isDigit(getCh())) {
                    return parseUnaryOperation(operation, "count");
                }
                throw new WrongOperationError("No such opeartion!: " + getCh());
            } else if (expect("min")) {
                return CheckExceptionSetClearMinMax(operation, "min");
            } else if(expect("od")) {
                return parseSecondPriory(operation, "mod");
            } else if (expect("ax")) {
                return CheckExceptionSetClearMinMax(operation, "max");
            } else if (expect("bs")) {
                return parseUnaryOperation(operation, "abs");
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

        private Operations<T> parseThirdPriority(Operations<T> operation) throws ParsingExceptions {
            if (test('-')) {
                take();
                if (operation == null) {
                    if (Character.isDigit(getCh())) {
                        hasUnaryMinus = true;
                        return parseConst();
                    }
                    if (Character.isLetter(getCh())) {
                        return new Unary<>(parseValue(null), true);
                    }
                    if (test('(') || Character.isWhitespace(getCh())) {
                        skipWs();
                        return new Unary<>(parseValue(null), false);
                    }
                    skipWs();
                    if (test('-')) {
                        return new Unary<>(parseThirdPriority(null), false);
                    }
                } else {
                    skipWs();
                    if (test('-')) {
                        Operations<T> parsed = parseThirdPriority(null);
                        skipWs();
                        if (test('*') || test('/')) {
                            String operationType = Character.toString(getCh());
                            take();
                            return parseOperations("-", operation, parseSecondPriory(parsed, operationType));
                        } else if (test('m')) {
                            if (source.checkNext() == 'o') {
                                expect("od");
                                return parseSecondPriory(parseOperations("-", operation, parsed), "mod");
                            }
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

        private Operations<T> parseUnaryOperation(Operations<T> operation, String operationType) throws ParsingExceptions {
            skipWs();
            Operations<T> tmp = parseValue(null);
            skipWs();
            Operations<T> parsed = CheckOnMiss(tmp, "No argument after count");
            return switch (operationType) {
                case "count" -> new Count<>(parsed);
                case "abs" -> new Abs<>(parsed);
                case "square" -> new Square<>(parsed);
                default -> throw new WrongOperationError("Wrong unary op!");
            };
        }

        private Operations<T> SetClearMinMax(Operations<T> operation, String operationType) throws ParsingExceptions { // Modification SetClear
            skipWs();
            Operations<T> parsed = CheckOnMiss(parseUntilFour(), "No argument after " + operationType);
            return parseOperations(operationType, operation, parsed);
        }

        private Operations<T> AddAndSubtract(String operationType, Operations<T> operation) throws ParsingExceptions {
            skipWs();
            Operations<T> parsed = CheckOnMiss(parseValue(null), "No argument in equation after " + operationType);
            skipWs();
            if ((test('*') || test('/'))) {
                String opType = Character.toString(getCh());
                take();
                return parseOperations(operationType, operation, parseSecondPriory(parsed, opType));
            } else if (test('m')) {
                if (source.checkNext() == 'o') {
                    expect("od");
                    return parseSecondPriory(parseOperations(operationType, operation, parsed), "mod");
                }
            }
            return parseOperations(operationType, operation, parsed);
        }

        private Operations<T> multiplyAndDivide(String operationType, Operations<T> operation) throws ParsingExceptions {
            skipWs();
            Operations<T> parsed = CheckOnMiss(parseValue(null), "No argument after " + operationType); // (x * / y) - как выдать ошибку?
            if (test('*') || test('/')){
                String opType = Character.toString(getCh());
                take();
                return parseSecondPriory(parseOperations(operationType, operation, parsed), opType);
            } else if (test('m')) {
                if (source.checkNext() == 'o') {
                    expect("od");
                    return parseSecondPriory(parseOperations(operationType, operation, parsed), "mod");
                }
            }
            else {
                return parseOperations(operationType, operation, parsed);
            }
            throw new WrongOperationError("wrong op!");
        }

        private Operations<T> parseSecondPriory(Operations<T> operation, String operationType) throws ParsingExceptions {
            skipWs();
            if (operationType.equals("*") || operationType.equals("/") || operationType.equals("mod")) {
                return multiplyAndDivide(operationType, operation);
            }
            String message = "Expected: '*' or '/', got: " + getCh();
            throw new WrongOperationError(message);
        }

        private Operations<T> parseOperations(String operationsType, Operations<T> operations1, Operations<T> operations2) throws ParsingExceptions {
            String message = "Invalid input operation: " + operationsType;
            return switch (operationsType) {
                case "+" -> new Add<>(operations1, operations2);
                case "-" -> new Subtract<>(operations1, operations2);
                case "*" -> new Multiply<>(operations1, operations2);
                case "/" -> new Divide<>(operations1, operations2);
                case "min" -> new Min<>(operations1, operations2);
                case "max" -> new Max<>(operations1, operations2);
                case "mod" -> new Mod<>(operations1, operations2);
                default -> throw new WrongOperationError(message);
            };
        }

        private Operations<T> parseVariable(char var) throws ParsingExceptions {
            skipWs();
            hasUnaryMinus = false;
            String message = "Wrong variable name!: " + var;
            return switch (var) {
                case 'x' -> new Variable<>("x");
                case 'y' -> new Variable<>("y");
                case 'z' -> new Variable<>("z");
                default -> throw new WrongCharacterException(message);
            };
        }
        private Operations<T> parseConst() throws ParsingExceptions {
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
            if (betweenZeroNine()) {
                throw new NumberException("Spaces in numbers");
            }
            return new Const<>(newConst.toString());
        }
    }
}
