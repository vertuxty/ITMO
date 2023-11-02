package queue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class ArrayQueueADT {
    //Pred: true
    private int n = 5;
    //Post: n = 5;
    //Pred: true
    protected Object[] elements = new Object[n];
    //Post: elements = new Object[n];
    //Pred: true
    private HashMap<Object, Integer> insertions = new HashMap<>();
    //Post: insertions = new HashMap<>();
    //Pred: true
    protected int head = -1;
    //Post: head = -1
    //Pred: true
    protected int tail = 0;
    //Post: tail = 0;

    //Pred: true
    //Post: R = new ArrayQueueADT();
    public static ArrayQueueADT create() {
        return new ArrayQueueADT();
    }



    //Pred: element != null && queueADT != null
    //Post: queueADT.elements[queueADT.tail] = element;
    public static void enqueue(final ArrayQueueADT queueADT, Object element) {
        Objects.requireNonNull(element);
        increaseCapacity(queueADT);
        queueADT.elements[queueADT.tail] = element;
        queueADT.tail = (queueADT.tail + 1) % queueADT.n;
        if (queueADT.head == -1) {
            queueADT.head++;
        }
        insertionOnAdd(queueADT, element);
    }



    //Prod: size() > 0 && queueADT != null;
    //Post: R = elements[head];
    public static Object element(final ArrayQueueADT queueADT) {
        assert size(queueADT) > 0;
        return queueADT.elements[queueADT.head];
    }



    //prod: size() > 0 && queueADT != null;
    //post: R = queueADT.elements[queueADT.head], size--;
    public static Object dequeue(final ArrayQueueADT queueADT) {
        assert size(queueADT) > 0;
        Object result = queueADT.elements[queueADT.head];
        queueADT.elements[queueADT.head] = null;
        queueADT.head = (queueADT.head + 1) % queueADT.n;
        insertionOnRemove(queueADT, result);
        return result;
    }

    //Pred: true
    //Post: forall element in queueADT.elements (element != null): element in newArray(), newArray().length = size(queueADT), immutable(elements)
    public static Object[] toArray(ArrayQueueADT queueADT) {
        if (size(queueADT) == 0) {
            return new Object[0];
        }
        Object[] increaseArray = new Object[size(queueADT)];
        if (queueADT.head < queueADT.tail) {
            System.arraycopy(queueADT.elements, queueADT.head, increaseArray, 0, queueADT.tail - queueADT.head);
        } else {
            System.arraycopy(queueADT.elements, queueADT.head, increaseArray, 0, queueADT.elements.length - queueADT.head);
            System.arraycopy(queueADT.elements, 0, increaseArray, queueADT.elements.length - queueADT.head, queueADT.tail);
        }
        return increaseArray;
    }


    //Pred: queueADT != null
    //Post: R = size of queue
    public static int size(ArrayQueueADT queueADT) {
        if (queueADT.head == -1) {
            return 0;
        }
        if (queueADT.head > queueADT.tail) {
            return queueADT.elements.length - queueADT.head + queueADT.tail;
        } else if (queueADT.head < queueADT.tail){
            return queueADT.tail - queueADT.head;
        } else {
            if (queueADT.elements[queueADT.tail] == null) {
                return 0;
            } else {
                return queueADT.elements.length - queueADT.head + queueADT.tail;
            }
        }
    }

    //Pred: queueADT != null
    //Post: R = size(queueADT) == 0;
    public static boolean isEmpty(final ArrayQueueADT queueADT) {
        return size(queueADT) == 0;
    }

    //Pred: queueADT != null
    //Post: size = 0
    public static void clear(final ArrayQueueADT queueADT) {
        queueADT.insertions.clear();
        queueADT.n = 5;
        queueADT.elements = Arrays.copyOf(new Object[queueADT.n], queueADT.n);
        queueADT.head = -1;
        queueADT.tail = 0;
    }



    //Pred: size(queueADT) > 0 && queueADT != null;
    //Post: R = queueADT.elements[queueADT.tail] && R != null, immutable(elements)
    public static Object peek(ArrayQueueADT queueADT) {
        assert size(queueADT) > 0;
        if (queueADT.tail == 0) {
            return queueADT.elements[queueADT.n - 1];
        }
        return queueADT.elements[queueADT.tail - 1];
    }




    //Pred: size(queueADT) > 0 && queueADT != null;
    //Post: R = queueADT.elements[queueADT.tail] && R != null, size--;
    public static Object remove(ArrayQueueADT queueADT) {
        assert size(queueADT) > 0;
        Object result;
        if (queueADT.tail == 0) {
            queueADT.tail = queueADT.n - 1;
            result = queueADT.elements[queueADT.tail];
        } else {
            result = queueADT.elements[--queueADT.tail];
        }
        queueADT.elements[queueADT.tail] = null;
        if (queueADT.head == queueADT.tail) {
            queueADT.tail = 0;
            queueADT.head = -1;
        }
        insertionOnRemove(queueADT, result);
        return result;
    }



    //Pred: element != null queueADT != null
    //Post: queueADT.elements[queueADT.head] = element, size++;
    public static void push(ArrayQueueADT queueADT,Object element) {
        Objects.requireNonNull(element);
        increaseCapacity(queueADT);
        if (queueADT.head == 0) {
            queueADT.head = queueADT.n - 1;
        } else if (queueADT.head > 0) {
            --queueADT.head;
        } else {
            ++queueADT.head;
            ++queueADT.tail;
        }
        queueADT.elements[queueADT.head] = element;
        insertionOnAdd(queueADT, element);
    }




    //Pred: result != null && queueADT != null
    //Post: queueADT.insertion.get(result) = queueADT.insertion.get(result) - 1
    private static void insertionOnRemove(ArrayQueueADT queueADT, Object result) {
        if (queueADT.insertions.get(result) == 1) {
            queueADT.insertions.remove(result);
        } else {
            queueADT.insertions.replace(result, queueADT.insertions.get(result) - 1);
        }
    }
    //Pred: element != null && queueADT != null
    //Post: queueADT.insertion.get(element) = queueADT.insertion.get(element) + 1 || queueADT.insertions.put(element, 1)
    private static void insertionOnAdd(ArrayQueueADT queueADT, Object element) {
        if (queueADT.insertions.containsKey(element)) {
            queueADT.insertions.replace(element, queueADT.insertions.get(element) + 1);
        } else {
            queueADT.insertions.put(element, 1);
        }
    }

    //Pred: queueADT != null;
    //Post: queueADT.elements.length = queueADT.elements.length*2 && queueADT.head = 0 && queueADT.tail = queueADT.elements.length;
    private static void increaseCapacity(ArrayQueueADT queueADT) {
        if (size(queueADT) == queueADT.elements.length) {
            Object[] increaseArray = new Object[queueADT.elements.length*2];
            System.arraycopy(queueADT.elements, queueADT.head, increaseArray, 0, queueADT.elements.length - queueADT.head);
            System.arraycopy(queueADT.elements, 0, increaseArray, queueADT.elements.length - queueADT.head, queueADT.tail);
            queueADT.elements = Arrays.copyOf(increaseArray, increaseArray.length);
            queueADT.head = 0;
            queueADT.tail = queueADT.elements.length/2;
            queueADT.n = increaseArray.length;
        }
    }
}

