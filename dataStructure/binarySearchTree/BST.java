public class BST<E extends Comparable<E>> {
    private Node<E> root;

    public BST(){
        root = null;
    }

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


    public void traverse(String order, Node<E> top) {
        if (top != null){
            switch (order) {
                case "preorder":
                    if (top.getData() != null) {
                        System.out.print(top.getData().toString() + " ");
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
                        System.out.print(top.getData().toString() + " ");
                    }
                    break;
            }
        }
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
}
