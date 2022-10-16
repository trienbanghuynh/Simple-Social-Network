package Graph;


public final class LinkedStack<T> implements StackInterface<T> {

    private Node topNode;

    public LinkedStack() { // default constructor
        topNode = null;
    }

    public void push(T newEntry) { // add new entry to the stack
        Node newNode = new Node(newEntry, topNode);
        topNode = newNode;
    }
    public T pop() {
        T pop = peek(); // get data from peek
        topNode = topNode.getNextNode(); //set topNode to the next node
        return pop;
    }
    public T peek() {
        if (isEmpty())
            throw new EmptyStackException();
        else
            return topNode.getData();
    }
    public boolean isEmpty() {
        return topNode == null; // return true if topNode == null
    }
    public void clear() {
        topNode = null;
    }

    private class Node {
        private T data;
        private Node nextNode;

        private Node(T dataPortion) {
            this(dataPortion,null);
        }
        private Node(T dataPortion, Node next_Node) {
            data = dataPortion;
            nextNode = next_Node;
        }

        private void setData(T dataPortion) {
            data = dataPortion;
        }
        private void setNextNode(Node next) {
            nextNode = next;
        }

        private T getData() {
            return data;
        }
        private Node getNextNode() {
            return nextNode;
        }
    } // end Node class
} // end BinaryTreePackage.StackAndQueuePackage.LinkedStack
