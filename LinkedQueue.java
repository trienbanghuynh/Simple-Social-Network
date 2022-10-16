package Graph;



public final class LinkedQueue <T> implements QueueInterface <T> {
    private Node firstNode;
    private Node lastNode;

    public LinkedQueue() { // default constructor
        firstNode = null;
        lastNode = null;
    }
    public void enqueue(T newEntry) {
        Node newNode = new Node(newEntry); // call default constructor
        if (isEmpty())
            firstNode = newNode;
        else
            lastNode.setNextNode(newNode);
        lastNode = newNode;
    }

    public T dequeue() {
        T front = getFront();
        // might throw EmptyQueueException
        // Assertion: firstNode != null
        firstNode.setData(null);
        firstNode = firstNode.getNextNode();
        if (firstNode == null)
            lastNode = null;
        return front;
    }

    public T getFront() {
        if (isEmpty())
            throw new EmptyQueueException();
        else
            return firstNode.getData();
    }

    public boolean isEmpty() {
        return (firstNode == null) && (lastNode == null);
    }

    public void clear() {
        firstNode = null;
        lastNode = null;
    }

    private class Node {
        private T data;
        private Node nextNode;

        private Node(T dataPortion) {
            this (dataPortion, null);
        }
        private Node(T dataPortion, Node next_Node) {
            data = dataPortion;
            nextNode = next_Node;
        }
        private void setData (T newData) {
            data = newData;
        }
        private void setNextNode (Node next) {
            nextNode = next;
        }
        private T getData() {
            return data;
        }
        private Node getNextNode() {
            return nextNode;
        }
    } // end Node class
} // end LinkedQueue


/* Test Code Result
Create a queue:


isEmpty() returns true

Add to queue to get
Joe Jess Jim Jill Jane Jerry


isEmpty() returns false



Testing getFront and dequeue:

Joe is at the front of the queue.
Joe is removed from the front of the queue.

Jess is at the front of the queue.
Jess is removed from the front of the queue.

Jim is at the front of the queue.
Jim is removed from the front of the queue.

Jill is at the front of the queue.
Jill is removed from the front of the queue.

Jane is at the front of the queue.
Jane is removed from the front of the queue.

Jerry is at the front of the queue.
Jerry is removed from the front of the queue.


The queue should be empty: isEmpty() returns true


Add to queue to get
Joe Jess Jim


Testing clear:


isEmpty() returns true


Add to queue to get
Joe Jess Jim

Joe is at the front of the queue.
Joe is removed from the front of the queue.

Jess is at the front of the queue.
Jess is removed from the front of the queue.

Jim is at the front of the queue.
Jim is removed from the front of the queue.



The queue should be empty: isEmpty() returns true

The next calls will throw an exception.

Exception in thread "main" EmptyQueueException
	at LinkedQueue.getFront(LinkedQueue.java:31)
	at Driver.testQueueOperations(Driver.java:76)
	at Driver.main(Driver.java:12)

Process finished with exit code 1

 */