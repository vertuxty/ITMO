package expression.generic;

public interface CharSource {
    boolean hasNext();
    char next();
    char checkNext();
    IllegalArgumentException error(String message);
}
