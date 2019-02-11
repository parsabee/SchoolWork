import java.util.Scanner;

public class lab3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numLines = scanner.nextInt();
        pQueue<Integer> heap = new pQueue<Integer>(numLines);
        scanner.nextLine();
        String task;
        String[] taskItem;
        for(int i=0; i< numLines; i++){
            task = scanner.nextLine();
            taskItem = task.split("\\s+");
            switch(taskItem[0]){
                case "insert":
                    Integer num = new Integer(taskItem[1]);
                    heap.insert(num);
                    break;
                case "maximum":
                    System.out.println(heap.maximum());
                    break;
                case "extractMax":
                    System.out.println(heap.extractMax());
                    break;
                case "isEmpty":
                    if(heap.isEmpty())
                        System.out.println("Empty");
                    else
                        System.out.println("Not Empty");
                    break;
                case "print":
                    heap.print();
                    break;
                case "build":
                    String array = taskItem[1];
                    array = array.replaceAll("\\[", "").replaceAll("\\]","");
                    String[] array_s = array.split(",");
                    Integer[] array_i = new Integer[array_s.length+1];
                    for(int j = 1; j<= array_s.length; j++ ) {
                        array_i[j]= new Integer(array_s[j-1]);
                    }
                    heap.buildMaxHeap(array_i);
            }
        }
        scanner.close();
    }
}