package edu.uoregon.parsab.binarytreetraversal;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Stack;

public class BST<E extends Comparable<E>> implements Parcelable {
    private Node<E> root;

    public BST(){
        root = null;
    }

    protected BST(Parcel in) {
        root = (Node<E>) in.readSerializable();
    }

    public static final Creator<BST> CREATOR = new Creator<BST>() {
        @Override
        public BST createFromParcel(Parcel in) {
            return new BST(in);
        }

        @Override
        public BST[] newArray(int size) {
            return new BST[size];
        }
    };

    public Node<E> getRoot(){
        return root;
    }

    private Node<E> insertNode(Node<E> current, Node<E> n){
        if(current == null)
            current = n;
        else if(current.getData().compareTo(n.getData())< 0) {
            current.setRightChild(insertNode(current.getRightChild(), n));
            current.getRightChild().setParent(current);
        }
        else if(current.getData().compareTo(n.getData())>0) {
            current.setLeftChild(insertNode(current.getLeftChild(), n));
            current.getLeftChild().setParent(current);
        }
        return current;
    }
    public void insert(E data){
        Node<E> node = new Node<E>(data);
        root = insertNode(root, node);
    }

    public Node<E> find(E data){
        boolean done = false;
        Node<E> temp = root;


        while(!done){
            if (temp == null){
                return null;
            }
            if(temp.getData().compareTo(data) == 0){
                done = true;
            } else if (temp.getData().compareTo(data) > 0){
                temp = temp.getLeftChild();
            } else if (temp.getData().compareTo(data) < 0){
                temp = temp.getRightChild();
            }
        }
        return temp;
    }
    private Node<E> findDeleteNode(Node<E> current, E val) {
        if(current == null) {	// If we get to a leaf or null, it means we didn't find the node.
            System.out.println("Node not found.");
        }
        else if(current.getData().compareTo(val) == 0){	// Node has been found. Perform delete operation.
            return deleteCurrentNode(current);
        }
        else if(current.getData().compareTo(val) > 0){	//Traversing to the left
            current.setLeftChild(findDeleteNode(current.getLeftChild(), val));
            if(current.getLeftChild()!= null)
                current.getLeftChild().setParent(current);
        }
        else if(current.getData().compareTo(val) < 0){	//Traversing to the right
            current.setRightChild(findDeleteNode(current.getRightChild(), val));
            if(current.getRightChild()!= null)
                current.getRightChild().setParent(current);
        }
        return current;
    }
    private int IdentifyCase(Node<E> current) {
        if(current.getLeftChild() == null && current.getRightChild() == null){
            return 1;
        }
        else if(current.getLeftChild() != null && current.getRightChild() != null){
            return 3;
        }
        return 2;
    }
    private E findLowestInRightSubTree(Node<E> current) {
        E val = current.getData();
        while(current.getLeftChild() != null){
            current = current.getLeftChild();
            val = current.getData();
        }
        return val;
    }
    private Node<E> deleteCase2(Node<E> current) {
        if(current.getLeftChild() == null){
            return current.getRightChild();
        }
        return current.getLeftChild();
    }
    private Node<E> deleteCase3(Node<E> current) {
        E replaceVal = findLowestInRightSubTree(current.getRightChild());	// Call a method to find the value of the replacement.
        current.setData(replaceVal);								// Set the data of the current node to the replacement value.
        current.setRightChild(findDeleteNode(current.getRightChild(), replaceVal));// Call delete on the right subtree, with the replacement value the one being deleted. Assign whatever is returned to the right subtree.
        if(current.getRightChild()!= null)
            current.getRightChild().setParent(current);
        return current;
    }
    private Node<E> deleteCurrentNode(Node<E> current) {
        int icase = IdentifyCase(current);
        if(icase == 1){ //no children
            return null;
        }
        else if(icase == 2){ // one child
            return deleteCase2(current);
        }
        else if(icase == 3){ //two children
            return deleteCase3(current);
        }
        return null;
    }
    public void delete(E data) {
        root = findDeleteNode(root, data);
    }


    // in-order traversal iterative
    public String inOrder(Node<E> top){
        String traversal = "";
        if(top != null){
            Stack<Node<E>> stack = new Stack<Node<E>>();
            Node<E> node = top;
            while(node!=null){
                stack.push(node);
                node = node.getLeftChild();
            }
            while (stack.size()>0){
                node = stack.pop();
                traversal += node.getData().toString()+"  ";
                if(node.getRightChild()!=null){
                    node = node.getRightChild();
                    while(node!=null){
                        stack.push(node);
                        node = node.getLeftChild();
                    }
                }
            }
            return traversal;
        }
        return null;
    }

    //post-order traversal iterative
    public String postOrder(Node<E> top)
    {
        // Create two stacks
        Stack<Node<E>> s1 = new Stack<Node<E>>();
        Stack<Node<E>> s2 = new Stack<Node<E>>();
        String traversal = "";

        if (top != null){
            // push root to first stack
            s1.push(top);

            // Run while first stack is not empty
            while (!s1.isEmpty())
            {
                // Pop an item from s1 and push it to s2
                Node<E> temp = s1.pop();
                s2.push(temp);

                // Push left and right children of
                // removed item to s1
                if (temp.getLeftChild() != null)
                    s1.push(temp.getLeftChild());
                if (temp.getRightChild() != null)
                    s1.push(temp.getRightChild());
            }

            // Print all elements of second stack
            while (!s2.isEmpty())
            {
                Node<E> temp = s2.pop();
                traversal+= temp.getData().toString()+"  ";
            }
            return traversal;
        }
        return null;
    }

    //pre-order traversal iterative
    public String preOrder(Node<E> top) {

        if (top != null) {

            Stack<Node<E>> nodeStack = new Stack<>();
            nodeStack.push(root);
            String traversal = "";

            while (nodeStack.empty() == false) {

                Node<E> mynode = nodeStack.peek();
                traversal += mynode.getData().toString()+ "  ";
                nodeStack.pop();

                if (mynode.getRightChild() != null) {
                    nodeStack.push(mynode.getRightChild());
                }
                if (mynode.getLeftChild() != null) {
                    nodeStack.push(mynode.getLeftChild());
                }
            }
            return traversal;
        }
        return null;
    }

    public Node<E> getMin(Node<E> top){
        boolean done = false;
        Node<E> temp = top;
        while(!done) {
            if (temp.getLeftChild() == null) {
                done = true;
            } else {
                temp = temp.getLeftChild();
            }
        }
        return temp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(root);
    }
}
