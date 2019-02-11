package edu.uoregon.parsab.binarytreetraversal;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Stack;


public class RBT<E extends Comparable<E>> implements Parcelable {
    private Node<E> root;

    public RBT(){
        root = null;
    }

    protected RBT(Parcel in) {
        root = (Node<E>) in.readSerializable();
    }

    public static final Creator<RBT> CREATOR = new Creator<RBT>() {
        @Override
        public RBT createFromParcel(Parcel in) {
            return new RBT(in);
        }

        @Override
        public RBT[] newArray(int size) {
            return new RBT[size];
        }
    };

    public Node<E> getRoot(){
        return root;
    }
    public void setRoot(Node<E> node){
        this.root = node;
    }
    public void insert(E data){
        // Preform a regular insert
        // Check to make sure the tree remains an RBT tree
        boolean done = false;
        Node<E> temp = root;

        while(!done){
            if (root == null || root.getData()==null) {
                root = new Node<E>(data);
                root.setColor('b');
                done = true;
            } else if (temp.getData().compareTo(data) > 0){
                if (temp.getLeftChild() == null || temp.getLeftChild().getData()==null){
                    temp.setLeftChild(new Node<E>(data));
                    temp.getLeftChild().setParent(temp);
                    temp.getLeftChild().setColor('r');
                    fixInsert(temp.getLeftChild());
                    done = true;
                }
                temp = temp.getLeftChild();
            } else if (temp.getData().compareTo(data) < 0){
                if (temp.getRightChild() == null || temp.getRightChild().getData()==null){
                    temp.setRightChild(new Node<E>(data));
                    temp.getRightChild().setParent(temp);
                    temp.getRightChild().setColor('r');
                    fixInsert(temp.getRightChild());
                    done = true;
                }
                temp = temp.getRightChild();
            }
            else
                done = true;
        }
    }
    private void fixInsert(Node<E> z){
        Node<E> y;
        if(z.getParent().getParent()!= null){
            while(z.getParent().getColor()=='r'){
                if(z.getParent() == z.getParent().getParent().getLeftChild()){
                    y = z.getParent().getParent().getRightChild();
                    if((y != null)&&(y.getColor()== 'r')){
                        z.getParent().setColor('b');
                        y.setColor('b');
                        if(z.getParent().getParent()!= root){
                            z.getParent().getParent().setColor('r');
                            z = z.getParent().getParent();
                        }
                        else
                            z = z.getParent();
                    }
                    else{
                        if(z == z.getParent().getRightChild()) {
                            z = z.getParent();
                            leftRotate(z);
                        }
                        z.getParent().setColor('b');
                        z.getParent().getParent().setColor('r');
                        rightRotate(z.getParent().getParent());
                    }
                }
                else{
                    y = z.getParent().getParent().getLeftChild();
                    if((y != null)&&(y.getColor()== 'r')){
                        z.getParent().setColor('b');
                        y.setColor('b');
                        if(z.getParent().getParent()!= root){
                            z.getParent().getParent().setColor('r');
                            z = z.getParent().getParent();
                        }
                        else
                            z = z.getParent();
                    }
                    else{
                        if(z == z.getParent().getLeftChild()) {
                            z = z.getParent();
                            rightRotate(z);
                        }
                        z.getParent().setColor('b');
                        z.getParent().getParent().setColor('r');
                        leftRotate(z.getParent().getParent());
                    }
                }
            }
        }
    }

    public Node<E> search(E data){
        boolean done = false;
        Node<E> temp = root;
        while(!done){
            if (temp == null){
                return null;
            }
            if(temp.getData().compareTo(data) == 0){
                done = true;
            } else if (temp.getData().compareTo(data) > 0){
                if(temp.getLeftChild()!=null)
                    temp = temp.getLeftChild();
                else
                    temp = null;
            } else if (temp.getData().compareTo(data) < 0){
                if(temp.getRightChild()!=null)
                    temp = temp.getRightChild();
                else
                    temp = null;
            }
        }
        return temp;
    }

    private Node<E> getMin(Node<E> top){
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
    private Node<E> getMax(Node<E> top){
        boolean done = false;
        Node<E> temp = top;
        while(!done) {
            if (temp.getRightChild() == null) {
                done = true;
            } else {
                temp = temp.getRightChild();
            }
        }
        return temp;
    }

    private void RB_Transplant(Node<E> u, Node<E> v){
        if (u.getParent()==null)
            this.root = v;
        else if(u == u.getParent().getLeftChild()){
            u.getParent().setLeftChild(v);
        }
        else
            u.getParent().setRightChild(v);
        if(v==null){
            Node<E> x = new Node<E>(null);
            x.setParent(u.getParent());
        }
        else
            v.setParent(u.getParent());
    }
    public void delete(E data){
        Node<E> to_be_deleted = search(data);
        RB_Delete(to_be_deleted);
    }
    private void RB_Delete(Node<E> z){
        if(z!=null){
            Node<E> y = z;
            Node<E> x;
            char y_original_color = y.getColor();
            if (z.getLeftChild()==null || z.getLeftChild().getData()==null){
                x = z.getRightChild();
                if(x == null){
                    x=new Node<E>(null);
                    x.setColor('b');
                    x.setParent(z);
                    x.getParent().setRightChild(x);
                }
                RB_Transplant(z,z.getRightChild());
            }
            else if(z.getRightChild()==null || z.getRightChild().getData()==null){
                x = z.getLeftChild();
                if(x==null){
                    x=new Node<E>(null);
                    x.setColor('b');
                    x.setParent(z);
                    x.getParent().setLeftChild(x);
                }
                RB_Transplant(z, z.getLeftChild());
            }
            else{
                y = getMax(z.getLeftChild());
                y_original_color = y.getColor();
                x= y.getLeftChild();
                if(x==null){
                    x=new Node<E>(null);
                    x.setColor('b');
                    x.setParent(y);
                    x.getParent().setLeftChild(x);
                }
                if(y.getParent()==z){
                    x.setParent(y);
                }
                else{
                    RB_Transplant(y, y.getLeftChild());
                    y.setLeftChild(z.getLeftChild());
                    y.getLeftChild().setParent(y);
                }
                RB_Transplant(z, y);
                y.setRightChild(z.getRightChild());
                y.getRightChild().setParent(y);
                y.setColor(z.getColor());
            }
            if(y_original_color == 'b'){
                fixDelete(x);
            }
        }
    }

    private void fixDelete(Node<E> x){
        while(x!= this.root && x.getColor()=='b'){
            if(x==x.getParent().getLeftChild()){
                Node<E> w = x.getParent().getRightChild();
                if(w.getColor()=='r'){
                    w.setColor('b');
                    x.getParent().setColor('r');
                    leftRotate(x.getParent());
                    w = x.getParent().getRightChild();
                }
                null_handling(w);
                if(w.getLeftChild().getColor()=='b'&&w.getRightChild().getColor()=='b'){
                    w.setColor('r');
                    x=x.getParent();
                }
                else{
                    if(w.getRightChild().getColor()=='b'){
                        w.getLeftChild().setColor('b');
                        w.setColor('r');
                        rightRotate(w);
                        w =x.getParent().getRightChild();
                    }
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor('b');
                    w.getRightChild().setColor('b');
                    leftRotate(x.getParent());
                    x = this.root;
                }
            }
            else{
                Node<E> w = x.getParent().getLeftChild();
                if(w.getColor()=='r'){
                    w.setColor('b');
                    x.getParent().setColor('r');
                    rightRotate(x.getParent());
                    w = x.getParent().getLeftChild();
                }
                null_handling(w);
                if(w.getRightChild().getColor()=='b'&&w.getLeftChild().getColor()=='b'){
                    w.setColor('r');
                    x=x.getParent();
                }
                else{
                    if(w.getLeftChild()==null || w.getLeftChild().getColor()=='b'){
                        if(w.getRightChild()!=null)
                            w.getRightChild().setColor('b');
                        w.setColor('r');
                        leftRotate(w);
                        w =x.getParent().getLeftChild();
                    }
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor('b');
                    if(w.getLeftChild()!=null)
                        w.getLeftChild().setColor('b');
                    rightRotate(x.getParent());
                    x = this.root;
                }
            }
        }
        x.setColor('b');
    }
    private void null_handling(Node<E> w){
        if(w.getRightChild()==null){
            Node<E> w_RC = new Node<E>(null);
            w_RC.setColor('b');
            w_RC.setParent(w);
            w.setRightChild(w_RC);
        }
        if(w.getLeftChild()==null){
            Node<E> w_LC = new Node<E>(null);
            w_LC.setColor('b');
            w_LC.setParent(w);
            w.setLeftChild(w_LC);
        }
    }

    public void traverse(String order, Node<E> top) {
        // Preform a preorder traversal of the tree
        if (top != null){
            switch (order) {
                case "preorder":
                    if (top.getData() != null) {
                        System.out.print(top.getData().toString()+ " ");
                        traverse("preorder", top.getLeftChild());
                        traverse("preorder", top.getRightChild());
                    }
                    break;
                case "inorder":
                    if (top.getData() != null) {
                        traverse("inorder", top.getLeftChild());
                        System.out.print(top.getData().toString() + " ");
                        traverse("inorder", top.getRightChild());
                    }
                    break;
                case "postorder":
                    if (top.getData() != null) {
                        traverse("postorder", top.getLeftChild());
                        traverse("postorder", top.getRightChild());
                        System.out.print(top.getData().toString() +" ");
                    }
                    break;
            }
        }
    }
    public void rightRotate(Node<E> y){
        Node<E> x = y.getLeftChild();
        if(x.getRightChild()!= null)
            x.getRightChild().setParent(y);
        y.setLeftChild(x.getRightChild());
        x.setRightChild(y);
        x.setParent(y.getParent());
        if(y.getParent()!= null) {
            if (y.getParent().getLeftChild() == y)
                y.getParent().setLeftChild(x);
            else if (y.getParent().getRightChild() == y)
                y.getParent().setRightChild(x);
        }
        y.setParent(x);
        if(getRoot()==y)
            root = x;
    }
    public void leftRotate(Node<E> x) {
        Node<E> y = x.getRightChild();
        if(y.getLeftChild()!= null)
            y.getLeftChild().setParent(x);
        x.setRightChild(y.getLeftChild());
        y.setLeftChild(x);
        y.setParent(x.getParent());
        if(x.getParent()!= null){
            if(x.getParent().getLeftChild()==x)
                x.getParent().setLeftChild(y);
            else if(x.getParent().getRightChild()==x)
                x.getParent().setRightChild(y);
        }
        x.setParent(y);
        if(getRoot()==x)
            root = y;
    }
    public boolean isRBT(){
        //property 5 check
        if(this.root.getColor()!='b')
            return false;
        //property 1 check
        boolean prop1 = color_check(this.root);
        if(prop1==false)
            return false;
        //property 3 check
        boolean prop3 = red_node_check(this.root);
        if(prop3==false)
            return false;
        //property 4 check
        boolean prop4 = black_height_balance_check(this.root);
        if(prop4==false)
            return false;
        return true;
    }
    private boolean red_node_check(Node<E> top){
        if(top!=null){
            if(top.getColor()!='r'){
                if(top.getRightChild()!=null)
                    red_node_check(top.getRightChild());
                if(top.getLeftChild()!=null)
                    red_node_check(top.getLeftChild());
            }
            else{
                null_handling(top);
                if(top.getLeftChild().getColor()!='b' && top.getRightChild().getColor()!='b')
                    return false;
                else{
                    if(top.getLeftChild()!=null)
                        red_node_check(top.getLeftChild());
                    if(top.getRightChild()!=null)
                        red_node_check(top.getRightChild());
                }
            }
        }
        return true;
    }
    private int get_black_height(Node<E> node){
        int black_height=0;
        Node<E> temp=node;
        while(temp!=null && temp.getData()!=null){
            if(temp.getColor()=='b')
                black_height++;
            if(temp.getLeftChild()!=null)
                temp = temp.getLeftChild();
            else
                temp = temp.getRightChild();
        }
        if(temp==null || temp.getData()==null)
            black_height++;
        return black_height;
    }
    private boolean black_height_balance_check(Node<E> top){
        if(top!=null){
            if(get_black_height(top.getLeftChild())!=get_black_height(top.getRightChild()))
                return false;
            if(top.getLeftChild()!=null)
                black_height_balance_check(top.getLeftChild());
            if(top.getRightChild()!=null)
                black_height_balance_check(top.getRightChild());
        }
        return true;
    }
    private boolean color_check(Node<E> top){
        if(top!=null){
            if(top.getColor()!='b' && top.getColor()!='r')
                return false;
            if(top.getRightChild()!=null)
                color_check(top.getRightChild());
            if(top.getLeftChild()!=null)
                color_check(top.getLeftChild());
        }
        return true;
    }

    // in-order traversal iterative
    public String inOrder(Node<E> top, boolean showColor){
        String traversal = "";
        String traversal_colored = "";
        if(top != null){
            Stack<Node<E>> stack = new Stack<Node<E>>();
            Node<E> node = top;
            while(node!=null){
                stack.push(node);
                node = node.getLeftChild();
            }
            while (stack.size()>0){
                node = stack.pop();
                if(node.getData() != null){
                    traversal += node.getData().toString()+"  ";
                    traversal_colored += node.getData().toString()+node.getColor()+"  ";
                    if(node.getRightChild()!=null){
                        node = node.getRightChild();
                        while(node!=null){
                            stack.push(node);
                            node = node.getLeftChild();
                        }
                    }
                }
            }
            if(showColor)
                return traversal_colored;
            else
                return traversal;
        }
        return null;
    }

    //post-order traversal iterative
    public String postOrder(Node<E> top, boolean showColor)
    {
        // Create two stacks
        Stack<Node<E>> s1 = new Stack<Node<E>>();
        Stack<Node<E>> s2 = new Stack<Node<E>>();
        String traversal = "";
        String traversal_colored = "";


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
                traversal_colored += temp.getData().toString()+temp.getColor()+"  ";
            }
            if(showColor)
                return traversal_colored;
            else
                return traversal;
        }
        return null;
    }

    //pre-order traversal iterative
    public String preOrder(Node<E> top, boolean showColor) {

        if (top != null) {

            Stack<Node<E>> nodeStack = new Stack<>();
            nodeStack.push(root);
            String traversal = "";
            String traversal_colored = "";

            while (nodeStack.empty() == false) {

                Node<E> mynode = nodeStack.peek();
                traversal += mynode.getData().toString()+ "  ";
                traversal_colored += mynode.getData().toString()+mynode.getColor()+"  ";
                nodeStack.pop();

                if (mynode.getRightChild() != null) {
                    nodeStack.push(mynode.getRightChild());
                }
                if (mynode.getLeftChild() != null) {
                    nodeStack.push(mynode.getLeftChild());
                }
            }
            if(showColor)
                return traversal_colored;
            else
                return traversal;
        }
        return null;
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
