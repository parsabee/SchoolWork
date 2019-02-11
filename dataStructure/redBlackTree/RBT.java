public class RBT<E extends Comparable<E>> {
    private Node<E> root;

    public RBT(){
        root = null;
    }

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
            if (root == null) {
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
            } else if (temp.getData().compareTo(data) <= 0){
                if (temp.getRightChild() == null || temp.getRightChild().getData()==null){
                    temp.setRightChild(new Node<E>(data));
                    temp.getRightChild().setParent(temp);
                    temp.getRightChild().setColor('r');
                    fixInsert(temp.getRightChild());
                    done = true;
                }
                temp = temp.getRightChild();
            }
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
        // Return the node that corresponds with the given data
        // Note: No need to worry about duplicate values added to the tree
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
    // HINT: You may want to create extra methods such as fixDelete or fixInsert
}
