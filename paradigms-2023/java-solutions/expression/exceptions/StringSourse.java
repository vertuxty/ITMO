package expression.exceptions;

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

    @Override
    public IllegalArgumentException error(String message) {
        return new IllegalArgumentException(pos + ": " + message);
    }
}
