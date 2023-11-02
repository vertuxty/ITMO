package queue;

import java.util.*;

// Модель?
public class ArrayQueueModule {
    //Pred: true
    private static int n = 5;
    //Post: n = 5;
    //Pred: true
    protected static Object[] elements = new Object[n];
    //Post: elements = new Object[n];
    //Pred: true
    protected static final HashMap<Object, Integer> insertions = new HashMap<>();
    //Post: insertions = new HashMap<>();
    //Pred: true
    protected static int head = -1;
    //Post: head = -1
    //Pred: true
    protected static int tail = 0;
    //Post: tail = 0;


    //Pred: element != null
    //Post: elements[tail] = element;
    public static void enqueue(Object element) {
        Objects.requireNonNull(element);
        increaseCapacity();
        elements[tail] = element;
        tail = (tail + 1) % n;
        if (head == -1) {
            head++;
        }
        insertionOnAdd(element);
    }



    //Prod: size() > 0;
    // :NOTE: Что на счёт старых element
    //Post: R = elements[head] && R != null;
    public static Object element() {
        assert size() > 0;
        return elements[head];
    }


    //prod: size() > 0;
    // :NOTE: Что на счёт старых element
    //post: R = elements[head] && R != null, size--;
    public static  Object dequeue() {
        assert size() > 0;
        Object result = elements[head];
        elements[head] = null;
        head = (head + 1) % n;
        insertionOnRemove(result);
        return result;
    }




    //Pred: size() > 0;
    // :NOTE: Что на счёт старых element
    //Post: R = elements[tail] && R != null
    public static Object peek() {
        assert size() > 0;
        if (tail == 0) {
            return elements[n - 1];
        }
        return elements[tail - 1];
    }


    //Pred: size() > 0;
    //Post: R = elements[tail] && R != null, size()--;
    public static Object remove() {
        assert size() > 0;
        Object result;
        if (tail == 0) {
            tail = n - 1;
            result = elements[tail];
        } else {
            result = elements[--tail];
        }
        elements[tail] = null;
        if (head == tail) {
            tail = 0;
            head = -1;
        }
        insertionOnRemove(result);
        return result;
    }

    //Pred: element != null
    //Post: elements[head] = element, size++;
    public static void push(Object element) {
        Objects.requireNonNull(element);
        increaseCapacity();
        if (head == 0) {
            head = n - 1;
        } else if (head > 0) {
            --head;
        } else {
            ++head;
            ++tail;
        }
        elements[head] = element;
        insertionOnAdd(element);
    }


    //Pred: true
    //Post: R = size
    public static int size() {
        if (head == -1) {
            return 0;
        }
        if (head > tail) {
            return elements.length - head + tail;
        } else if (head < tail){
            return tail - head;
        } else {
            if (elements[tail] == null) {
                return 0;
            } else {
                return elements.length - head + tail;
            }
        }
    }



    //Pred: true
    //Post: R = size() == 0;
    public static boolean isEmpty() {
        return size() == 0;
    }

    //Pred: true
    //Post: forall element in queueADT.elements (element != null): element in newArray(), newArray().length = size(queueADT), immutable(elements)
    public static Object[] toArray() {
        if (size() == 0) {
            return new Object[0];
        }
        Object[] increaseArray = new Object[size()];
        if (head < tail) {
            System.arraycopy(elements, head, increaseArray, 0, tail - head);
        } else {
            System.arraycopy(elements, head, increaseArray, 0, elements.length - head);
            System.arraycopy(elements, 0, increaseArray, elements.length - head, tail);
        }
        return increaseArray;
    }

    //Pred: true
    //Post: size() = 0
    public static void clear() {
        insertions.clear();
        n = 5;;
        elements = Arrays.copyOf(new Object[n], n);
        head = -1;
        tail = 0;
    }

    //Pred: result != null
    //Post: insertion.get(result) = insertion.get(result) - 1
    private static void insertionOnRemove(Object result) {
        if (insertions.containsKey(result)) {
            if (insertions.get(result) == 1) {
                insertions.remove(result);
            } else {
                insertions.replace(result, insertions.get(result) - 1);
            }
        }
    }

    //Pred: element != null
    //Post: insertion.get(element) = insertion.get(element) + 1 || insertions.put(element, 1)
    private static void insertionOnAdd(Object element) {
        if (insertions.containsKey(element)) {
            insertions.replace(element, insertions.get(element) + 1);
        } else {
            insertions.put(element, 1);
        }
    }

    //Pred: true
    //Post: elements.length = elements.length*2 && head = 0 && tail = elements.length/2;
    private static void increaseCapacity() {
        if (size() == elements.length) {
            Object[] increaseArray = new Object[elements.length*2];
            System.arraycopy(elements, head, increaseArray, 0, elements.length - head);
            System.arraycopy(elements, 0, increaseArray, elements.length - head, tail);
            elements = Arrays.copyOf(increaseArray, increaseArray.length);
            head = 0;
            tail = increaseArray.length/2;
            n = increaseArray.length;
        }
    }

}
