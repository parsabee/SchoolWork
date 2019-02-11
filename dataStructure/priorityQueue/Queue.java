/**
 * Created by Parsa on 1/25/2017.
 */
public class Queue<E> {
    private Node<E> head;
    private Node<E> tail;

    public Queue(){

        head = null;
        tail = null;
    }
    public void enqueue(E newData){
        Node<E> temp = new Node<E>(newData, null);
        if(head == null) {
            tail = temp;
            head = temp;
        }
        else {
            tail.setNext(temp);
            tail = temp;
        }
    }

    public Node<E> dequeue() {

        if (isEmpty())
            return null;
        Node<E> temp = head;
        head = head.getNext();
        return temp;
    }
    public boolean isEmpty(){

        if (head == null)
            return true;
        return false;
    }
    public void printQueue(){

        Node<E> temp = head;
        while (temp != null) {
            System.out.println(temp.getData());
            temp = temp.getNext();
        }
    }
}
