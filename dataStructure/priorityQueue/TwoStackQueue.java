/**
 * Created by Parsa on 1/25/2017.
 */
public class TwoStackQueue<E> {
    private Stack<E> stack1;
    private Stack<E> stack2;

    public TwoStackQueue(){
        stack1 = new Stack<E>();
        stack2 = new Stack<E>();
    }
    public void enqueue(E newData){
        stack1.push(newData);
    }

    public Node<E> dequeue() {
        if(!stack2.isEmpty())
            return stack2.pop();
        else{
            Node<E> temp;
            while(!stack1.isEmpty()){
                temp = stack1.pop();
                stack2.push(temp.getData());
            }
            return stack2.pop();
        }

    }
    public boolean isEmpty(){
        if(stack2.isEmpty() && stack1.isEmpty())
            return true;
        return false;
    }
    public void printQueue(){
        if (!stack2.isEmpty()){
            Node<E> temp = stack2.pop();
            while (temp!=null){
                System.out.println(temp);
                temp = temp.getNext();
            }
        }
        Node<E> temp2 = stack1.pop();
        while(temp2!= null){
            System.out.println(temp2);
            temp2 = temp2.getNext();
        }
    }
}
