package queue;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class ArrayQueue extends AbstractQueue {
    private int n;
    private Object[] elements;
    private int head;
    private int tail;

    public Object[] getElements() {
        return elements;
    }

    public ArrayQueue() {
        this.n = 5;
        this.elements = new Object[n];
        this.head = -1;
        this.tail = 0;
    }

    @Override
    protected void enqueueImpl(ArrayQueue this, Object element) {
        increaseCapacity();
        this.elements[this.tail] = element;
        this.tail = (this.tail + 1) % this.n;
        if (this.head == -1) {
            this.head++;
        }
        size++;
    }

    @Override
    protected Object elementImpl(ArrayQueue this) {
        assert size() > 0;
        return this.elements[this.head];
    }

    @Override
    public Object dequeueImpl(ArrayQueue this) {
        Object result = this.elements[this.head];
        this.elements[this.head] = null;
        this.head = (this.head + 1) % this.n;
        size--;
        return result;
    }

    @Override
    protected void clearImpl(ArrayQueue this) {
        this.n = 5;
        this.elements = Arrays.copyOf(new Object[this.n], this.n);
        this.head = -1;
        this.tail = 0;
    }
    @Override
    protected int indexOfImpl(ArrayQueue this, Object element) {
        int copyHead = this.head;
        int index = 0;
        if (this.elements[copyHead].equals(element)) {
            return index;
        }
        copyHead = (copyHead + 1)%n;
        index++;
        while (copyHead != this.tail) {
            if (this.elements[copyHead].equals(element)) {
                return index;
            }
            copyHead = (copyHead + 1) % this.n;
            index++;
        }
        return -1;
    }
    @Override
    protected int lastIndexOfImpl(ArrayQueue this, Object element) {
        int last = -1;
        int index = 0;
        int copyHead = this.head;
        if (this.elements[copyHead].equals(element)) {
            last = index;
        }
        index++;
        copyHead = (copyHead + 1)%n;
        while (copyHead != this.tail) {
            if (this.elements[copyHead].equals(element)) {
                last = index;
            }
            copyHead = (copyHead + 1)%this.n;
            index++;
        }
        return last;
    }

    public Object[] toArray(ArrayQueue this) {
        if (this.size() == 0) {
            return new Object[0];
        }
        Object[] increaseArray = new Object[size()];
        if (this.head < this.tail) {
            System.arraycopy(this.elements, this.head, increaseArray, 0, this.tail - this.head);
        } else {
            System.arraycopy(this.elements, this.head, increaseArray, 0, this.elements.length - this.head);
            System.arraycopy(this.elements, 0, increaseArray, this.elements.length - this.head, this.tail);
        }
        return increaseArray;
    }

    private void increaseCapacity() {
        if (size() == this.elements.length) {
            Object[] increaseArray = new Object[this.elements.length*2];
            System.arraycopy(this.elements, this.head, increaseArray, 0, this.elements.length - this.head);
            System.arraycopy(this.elements, 0, increaseArray, this.elements.length - this.head, this.tail);
            this.elements = Arrays.copyOf(increaseArray, increaseArray.length);
            this.head = 0;
            this.tail = increaseArray.length/2;
            this.n = increaseArray.length;
        }
    }
}