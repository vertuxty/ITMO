package expression.generic;

public class StringSourse implements CharSource {
    private final String string;
    private int pos;

    public String getString() {
        return string;
    }

    public int getPos() {
        return pos;
    }

    public StringSourse(String string) {
        this.string = string;
    }

    @Override
    public boolean hasNext() {
        return pos < string.length();
    }

    @Override
    public char next() {
        return string.charAt(pos++);
    }
    public char checkNext() {
        return string.charAt(pos + 1);
    }

    @Override
    public IllegalArgumentException error(String message) {
        return new IllegalArgumentException(pos + ": " + message);
    }
}
