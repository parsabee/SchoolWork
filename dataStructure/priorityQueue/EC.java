/**
 * Created by Parsa on 1/25/2017.
 */
import java.util.Scanner;

public class EC {
    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);
        while(scan.hasNext()){
            String input = scan.nextLine();
            if(isPalindrome(input))
                System.out.println("This is a Palindrome.");
            else
                System.out.println("Not a Palindrome.");
        }
    }
    public static boolean isPalindrome(String s){
        // Create a stack and a Queue of chars that represents the passed in string
        // Hint: While you loop through the given string, push the same char onto your stack
        // that you enqueue into your Queue. This way you can use dequeue to get
        // the string from left to right, but you pop the string from right to left
        // Compare your Queue and Stack to see if the input String was a Palindrome or not
        Stack<Character> stack = new Stack<Character>();
        TwoStackQueue<Character> queue = new TwoStackQueue<Character>();
        if(s.length() == 1)
            return false;
        for (int i = 0; i < s.length(); i++){
            stack.push(s.charAt(i));

            queue.enqueue(s.charAt(i));
        }
        for (int i = 0; i< s.length(); i++){
            if(queue.dequeue().getData() != stack.pop().getData())
                return false;
        }
        return true;
    }
}
