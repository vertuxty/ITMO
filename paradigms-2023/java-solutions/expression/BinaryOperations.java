package expression;

import java.util.Objects;

public abstract class BinaryOperations implements Operations {

    public Operations operations1;
    public Operations operations2;
    public BinaryOperations(Operations operations1, Operations operations2) {
        this.operations1 = operations1;
        this.operations2 = operations2;
    }

    public String operationsType() {
        return null;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(operations1.toString()).append(" ").append(operationsType()).append(" ").append(operations2.toString()).append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BinaryOperations diff = (BinaryOperations) obj;
        return (this.operations1.equals(diff.operations1) && this.operations2.equals(diff.operations2) && operationsType().equals(diff.operationsType()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(operations1, operations2, operationsType());
    }
}