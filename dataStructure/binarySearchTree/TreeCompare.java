import java.util.Scanner;

public class TreeCompare {
    public static void main(String[] args){
        BST<Integer> tree1 = new BST<>();
        BST<Integer> tree2 = new BST<>();
        Scanner scanner1 = new Scanner(System.in);
        int numLines1 = scanner1.nextInt();
        scanner1.nextLine();
        String task1;
        String[] taskItem1;
        for(int i=0; i<numLines1; i++){
            task1 = scanner1.nextLine();
            taskItem1 = task1.split("\\s+");
            Integer intNum1 = new Integer(taskItem1[1]);
            tree1.insert(intNum1);
        }
        int numLines2 = scanner1.nextInt();
        scanner1.nextLine();
        String task2;
        String[] taskItem2;
        for(int j=0; j<numLines2; j++){
            task2 = scanner1.nextLine();
            taskItem2 = task2.split("\\s+");
            Integer intNum2 = new Integer(taskItem2[1]);
            tree2.insert(intNum2);
        }
        scanner1.close();
        if(isSimilar(tree1.getRoot(), tree2.getRoot()))
            System.out.print("The trees have the same shape.");
        else
            System.out.print("The trees do not have the same shape.");
    }
    private static boolean isSimilar(Node N1, Node N2) {
        if(N1==null && N2==null)
            return true;
        if((N1!=null && N2==null)||(N1==null && N2!=null))
            return false;
        return isSimilar(N1.getRightChild(), N2.getRightChild())&& isSimilar(N1.getLeftChild(), N2.getLeftChild());
    }
}
