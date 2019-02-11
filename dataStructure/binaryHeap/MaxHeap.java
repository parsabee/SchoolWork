import java.util.Comparator;

public class MaxHeap<E extends Comparable> {
    private E[] myArray;
    private int maxSize;
    private int length;
    public MaxHeap(int s){
        myArray = (E[]) new Comparable[s + 1];
        this.maxSize = s;
        this.length = 0;

    }
    public E[] getArray(){
        return myArray;
    }
    public void setArray(E[] newArray){
        myArray = newArray;
    }
    public int getMaxSize(){
        return maxSize;
    }
   public void setMaxSize(int ms){
        maxSize = ms;
    }
    public int getLength(){
        return length;
    }
    public void setLength(int l){
        length = l;
    }
    private int parent(int pos){
        if (pos == 1)
            return pos;
        return (pos)/2;
    }

    public void insert(E data){
        myArray[++length]= data;
        int current = length;
        E temp = myArray[current];
        while (myArray[current].compareTo(myArray[parent(current)]) > 0){
            myArray[current]= myArray[parent(current)];
            myArray[parent(current)]= temp;
            current = parent(current);
        }
    }

    public Comparable<E> maximum(){
        return myArray[1];
    }
    public Comparable<E> extractMax(){
        if(this.getLength()!=0){
            E popped = myArray [1];
            myArray [1] = myArray [length];
            length = length-1;
            heapify(myArray, 1);
            return popped;
        }
        else
            return null;
    }
    public void heapify(E[] unheaped, int index){
        myArray = unheaped;
        int li = 2*index;
        int ri = 2*index+1;
        int largest;
        if (li<= length && unheaped[li].compareTo(unheaped[ri])>0)
            largest = li;
        else
            largest = index;
        if(ri<= length && unheaped[ri].compareTo(unheaped[li])>0)
            largest = ri;
        if (largest != index){
            E temp = unheaped[index];
            unheaped[index]=unheaped[largest];
            unheaped[largest]=temp;
            heapify(unheaped, largest);
        }
    }

    public void buildHeap(E[] arr) {
        maxSize = arr.length-1;
        for(int i = maxSize/2; i>= 1; i--){
            heapify(arr, i);
        }
    }
}
