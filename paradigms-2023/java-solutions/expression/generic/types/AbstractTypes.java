package expression.generic.types;

public interface AbstractTypes<T> {
//    T makes(AbstractTypes<T> calcs, T x, T y);
    T add(T x, T y);
    T min(T x, T y);
    T mod(T x, T y);
    T abs(T x);
    T square(T x);
    T max(T x, T y);
    T parseConstant(String constant);
    T subtract(T x, T y);
    T multiply(T x, T y);
    T divide(T x, T y);
//    T set(T x, T y);
//    T clear(T x, T y);
    T count(T x);
    T unary(T x);
}
