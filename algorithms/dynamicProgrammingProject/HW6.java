import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

public class HW6 {
    static Set<String> dict = new HashSet<String>(); 

    public static boolean Iterative(String line) {
        System.out.println("Iterative Solution:");
        boolean[] topDownIsAWord = new boolean[line.length()+1];
        int[] isAWordIndexes = new int[line.length()+1];
        String word;
        int n = line.length();
        topDownIsAWord[n] = true;
        isAWordIndexes[n] = line.length();
        
        for (int i = line.length(); i > -1; i--) {
            for (int j = i; j <line.length(); j++){ 
                word = line.substring(i, j+1);
                if (dict.contains(word) && topDownIsAWord[j+1]) {
                    topDownIsAWord[i] = true;
                    isAWordIndexes[i] = j;
                } 
            }
        }
        
        if (!topDownIsAWord[0]) {
            System.out.println("NO, cannot be split!");
            System.out.println(line + "\n");
            return false;
        }

        System.out.println("YES, can be split!");
        int k = 0;
        while (k < line.length()) {
            word = line.substring(k, isAWordIndexes[k]+1);
            System.out.print(word + " ");
            k = isAWordIndexes[k]+1;
        }
        System.out.println("\n");
        
        return true;  
    }
    
    public static void Recursive(String line) {
    System.out.println("Recursive Solution:");
	   	 int[] SP = new int[line.length()+1];
	   	 int[] LOC = new int[line.length()+1];
    	 Arrays.fill(SP, -1);
	   	 SP[line.length()] = 1;
	   	 LOC[line.length()] = line.length();
    	 int recursive = memSP(SP, LOC, line, 0);
    	 
         if (recursive != 1) {
             System.out.println("NO, cannot be split!");
             System.out.println(line + "\n");
             return;
         }

         System.out.println("YES, can be split!");
         int k = 0;
         String word;
         while (k < line.length()) {
             word = line.substring(k, LOC[k]+1);
             System.out.print(word + " ");
             k = LOC[k]+1;
         }
         System.out.println("\n");
    }

     public static int memSP(int[] SP, int[] LOC, String line, int i) {   
    	 String word;
    	 if (SP[i] == -1){
    		 SP[i] = 0;
    		 for (int j = i; j < line.length(); j++) {
    			 word = line.substring(i, j+1);
    			 if (dict.contains(word) && memSP(SP, LOC, line, j+1) == 1) {
    				 SP[i] = 1;
    				 LOC[i] = j;
    			 }
    		 }
    	 }
         return SP[i];
     }        

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("diction10k.txt"));


        String str;
        while ((str = reader.readLine()) != null) {
            dict.add(str);
        } 
        reader.close();
        String line;

        Scanner scanner = new Scanner(System.in);
        int numProblems = scanner.nextInt();
        scanner.nextLine();
        for(int i = 0; i < numProblems; ++i) {
            line = scanner.nextLine();
            Iterative(line);
            Recursive(line);
        }
        scanner.close();
        

    }
}