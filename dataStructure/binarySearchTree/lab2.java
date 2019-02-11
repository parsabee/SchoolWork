import java.util.Scanner;

public class lab2 {
    public static void main(String[] args){
        BST<Integer> binarySearchTree = new BST<Integer>();
        Scanner scanner = new Scanner(System.in);
        int numLines = scanner.nextInt();
        scanner.nextLine();
        String task;
        String[] taskItem;
        for(int i=0; i< numLines; i++){
            task = scanner.nextLine();
            taskItem = task.split("\\s+");
            switch(taskItem[0]){
                case "inorder":
                    binarySearchTree.traverse("inorder", binarySearchTree.getRoot());
                    System.out.print("\n");
                    break;
                case "postorder":
                    binarySearchTree.traverse("postorder", binarySearchTree.getRoot());
                    System.out.print("\n");
                    break;
                case "preorder":
                    binarySearchTree.traverse("preorder", binarySearchTree.getRoot());
                    System.out.print("\n");
                    break;
                case "insert":
                    Integer inNum = new Integer(taskItem[1]);
                    binarySearchTree.insert(inNum);
                    break;
                case "delete":
                    Integer delNum = new Integer(taskItem[1]);
                    binarySearchTree.delete(delNum);
                    break;
                case "min":
                    System.out.println(binarySearchTree.getMin(binarySearchTree.getRoot()).getData());
            }
        }
        scanner.close();

    }
}
