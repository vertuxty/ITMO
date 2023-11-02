package expression.generic;

//import expression.generic.types.GenericsExp;

import expression.generic.types.AbstractTypes;

import java.util.Objects;

public abstract class BinaryOperations<T> implements Operations<T> {

    public Operations<T> operations1;
    public Operations<T> operations2;
    public BinaryOperations(Operations<T> operations1, Operations<T> operations2) {
        this.operations1 = operations1;
        this.operations2 = operations2;
    }

    public abstract T makes(AbstractTypes<T> calcs, T x, T y);

    public String operationsType() {
        return null;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(operations1.toString()).append(" ").append(operationsType()).append(" ").append(operations2.toString()).append(")");
        return sb.toString();
    }

    public boolean equals(Operations<T> obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BinaryOperations<T> diff = (BinaryOperations<T>) obj;
        return (this.operations1.equals(diff.operations1) && this.operations2.equals(diff.operations2) && operationsType().equals(diff.operationsType()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(operations1, operations2, operationsType());
    }
    /* Запускаем evaluate -> зная calcs определяем тип операции. Вызываем метод makes, оторый будет делать операцию, в зависимости от типа calcs
    * */

    @Override
    public T evaluate(AbstractTypes<T> calcs, T x, T y, T z) {
        return makes(calcs, operations1.evaluate(calcs, x, y, z), operations2.evaluate(calcs, x, y, z));
    }
    @Override
    public T evaluate(AbstractTypes<T> calcs, T x) {
        return makes(calcs, operations1.evaluate(calcs, x), operations2.evaluate(calcs, x));
    }
}