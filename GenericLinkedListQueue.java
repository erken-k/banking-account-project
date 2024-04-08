import MyExceptions.EmptyQueueException;

public class GenericLinkedListQueue <T> {
    private class Node {
        T value;
        Node next;

        // Node Constructor
        Node(T val, Node n) {
            value = val;
            next = n;
        }
    }
    private Node front = null;
    private Node rear = null;
//receives object of Cllass T
    // adds it to the end of queue
    public void enqueue(T s) {
        if (!empty()) {
            rear.next = new Node(s, null);
            rear = rear.next;
        } else {
            front = rear = new Node(s, null);
        }
    }
//returns boolean representing whther queue is empty or not
    public boolean empty() {
        return front == null;
    }

//returns the element that would otherwise be returned by dequeue
    public T peek() {
        if (empty())
            throw new EmptyQueueException();
        else
            return front.value;
    }

//removes the element according to FIFO
    public T dequeue() {
        if (empty())
            throw new EmptyQueueException();
        else {
            T value = front.value;
            front = front.next;
            if (front == null) rear = null;
            return value;
        }
    }
// converts QUeue to string
    public String toString() {
        String str = "";

        // Walk down the list and append all values
        Node p = front;
        while (p != null) {
            str = str + p.value + " ";
            p = p.next;
        }
        return str;

    }
}
