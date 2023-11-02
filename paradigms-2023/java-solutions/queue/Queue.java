package queue;

import java.util.function.Predicate;
import java.util.function.Function;

/* Model:
// Model?
* Let immutable(queue) - queue is immutable.

  Pred: element != null
  Post: queue[size + 1] = element, size' = size + 1 && forall 0 <= i < size && 0 < i' < size' - 1: queue[i] = queue'[i'];
    enqueue();

  prod: size() > 0;
  post: R = queue[0], size' = size - 1, queue[0] = null && forall 1 <= i < size && 0 < i' < size': queue[i] = queue'[i'],
    dequeue();

  Prod: size() > 0;
  Post: R = queue[0] && immutable(queue)
    element()

  Pred: true
  Post: size() = 0 && queue.isEmpty() == true
    clear()

  Pred: true
  Post: R = size() == 0 && immutable(queue);
    isEmpty()

  Pred: true
  Post: R = size && immutable(queue)
    size()
    *
  Pred: element != null;
  Post: R = last insertion of element
    lastIndexOf();
    *
  Pred: element != null
  Post: R = first insertion of element
    indexOf();
* */

public interface Queue {
    int size();
    Object element();
    void enqueue(Object element);
    Object dequeue();
    boolean isEmpty();
    void clear();
    int indexOf(Object element);
    int lastIndexOf(Object element);
}

