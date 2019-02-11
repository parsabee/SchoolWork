public class pQueue<E extends Comparable> {
    private MaxHeap myHeap;
    public pQueue (int s) {
        myHeap = new MaxHeap(s);
    }
    public void insert(E data){
        myHeap.insert(data);
    }
    public Comparable<E> maximum(){
        return myHeap.maximum();
    }
    public Comparable<E> extractMax(){
        return myHeap.extractMax();
    }
    public boolean isEmpty(){
        if(myHeap.getLength() == 0)
            return true;
        return false;
    }
    public void buildMaxHeap(E[] arr){
        myHeap.setLength(arr.length);
        myHeap.buildHeap(arr);
    }
    public void print(){
        System.out.print("Current Queue: ");
        for(int i = 1; i< (myHeap.getLength()); i++)
            System.out.print( myHeap.getArray()[i] + ",");
        System.out.println(myHeap.getArray()[myHeap.getLength()]);
    }
}
