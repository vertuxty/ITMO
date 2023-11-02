package queue;

import java.util.HashMap;
import java.util.Objects;

import java.util.function.Function;
import java.util.function.Predicate;


public abstract class AbstractQueue implements Queue {
    protected int size;
    protected HashMap<Object, Integer> insertions = new HashMap<>();

    protected abstract Object elementImpl();
    protected abstract void enqueueImpl(Object element);
    protected abstract Object dequeueImpl();
    protected abstract void clearImpl();

    protected abstract int lastIndexOfImpl(Object element);
    protected abstract int indexOfImpl(Object element);

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return size;
    }

    public void enqueue(Object element) {
        Objects.requireNonNull(element);
        enqueueImpl(element);
        insertionOnAdd(element);
    }

    public Object dequeue() {
        assert size() > 0;
        Object result = dequeueImpl();
        // size--
        insertionOnRemove(result);
        return result;
    }

    public void clear() {
        insertions.clear();
        size = 0;
        clearImpl();
    }

    public Object element() {
        assert size() > 0;
        return elementImpl();
    }

    protected void insertionOnAdd(Object element) {
        if (this.insertions.containsKey(element)) {
            this.insertions.replace(element, this.insertions.get(element) + 1);
        } else {
            this.insertions.put(element, 1);
        }
    }

    protected void insertionOnRemove(Object result) {
        if (this.insertions.containsKey(result)) {
            if (this.insertions.get(result) == 1) {
                this.insertions.remove(result);
            } else {
                this.insertions.replace(result, this.insertions.get(result) - 1);
            }
        }
    }

    public int indexOf(Object element) {
        if (size() == 0) {
            return -1;
        }
        Objects.requireNonNull(element);
        return indexOfImpl(element);
    }

    public int lastIndexOf(Object element) {
        if (size() == 0) {
            return -1;
        }
        Objects.requireNonNull(element);
        return lastIndexOfImpl(element);
    }
}
