package expression.generic;

public class BaseParser {
    public static final char END = '\0';
    protected CharSource source;
    private char ch;

    public char getCh() {
        return ch;
    }

    //use newSource
    public BaseParser(CharSource source) {
        this.source = source;
        take();
    }
    //check, if char equals expected char
    protected boolean take(char expected) {
        if (ch == expected) {
            take();
            return true;
        } else {
            return false;
        }
    }
    //use for build consants
    protected boolean betweenZeroNine() {
        return ch >= '0' && ch <= '9';
    }

    //take another char from source
    protected void take() {
        ch = source.hasNext() ? source.next() : END;
    }

    protected boolean test(char expected) {
        return ch == expected;
    }
    //check on end of stream (file)
    protected boolean eof() {
        return take(END);
    }

    protected void checkEof() {
        if (!eof()) {
            throw new AssertionError("Expected END, found " + ch);
        }
    }

    protected boolean expect(String chars) {
        for (char ch: chars.toCharArray()) {
            if (!expect(ch)) {
                return false;
            }
        }
        return true;
    }
    protected boolean expect(char expected) {
        if (!take(expected)) {
            return false;
        }
        return true;
    }
    //skipWhitespaces
    public void skipWs() {
        while (Character.isWhitespace(ch)) {
            take();
        }
    }
}
