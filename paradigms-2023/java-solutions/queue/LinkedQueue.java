package queue;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Function;

public class LinkedQueue extends AbstractQueue {
    private static class Node {
        private final Object element;
        private Node next;
        public Node(Object element) {
            Objects.requireNonNull(element);
            this.element = element;
            this.next = null;
        }
    }
//    private int size;
    private Node top;
    private Node firstElement;

    @Override
    protected void enqueueImpl(Object element) {
        Node tempNode = top;
        top = new Node(element);
        if (firstElement == null) {
            firstElement = top;
        } else {
            tempNode.next = top;
        }
        size++;
    }

    @Override
    protected Object dequeueImpl() {
        Object result = firstElement.element;
        firstElement = firstElement.next;
        size--;
        return result;
    }


    @Override
    protected Object elementImpl() {
        return firstElement.element;
    }

    @Override
    protected void clearImpl() {
        firstElement = null;
    }

    @Override
    protected int indexOfImpl(Object element) {
        Node tempNode = firstElement;
        int firstIndex = 0;
        while (!tempNode.element.equals(element)) {
            tempNode = tempNode.next;
            if (tempNode == null) {
                return -1;
            }
            firstIndex++;
        }
        return firstIndex;
    }

    @Override
    protected int lastIndexOfImpl(Object element) {
        Node tempNode = firstElement;
        int lastIndex = -1;
        int count = 0;
        while (tempNode != null) {
            if (tempNode.element.equals(element)) {
                lastIndex = count;
            }
            tempNode = tempNode.next;
            count++;
        }
        return lastIndex;
    }
}
