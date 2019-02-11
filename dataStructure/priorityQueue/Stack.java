/**
 * Created by Parsa on 1/25/2017.
 */
public class Stack<E> {
    private Node<E> top;

    public Stack(){

        top = null;
    }
    public void push(E newData){
        top = new Node<E>(newData, top);
    }
    public Node<E> pop(){

        if(this.isEmpty())
            return null;
        Node<E> temp = top;
        top = top.getNext();
        return temp;
    }
    public boolean isEmpty(){

        if(top == null)
            return true;
        return false;
    }
    public void printStack(){

        Node<E> temp = top;
        while(temp != null){
            System.out.println(temp.getData());
            temp = temp.getNext();
        }
    }
}
