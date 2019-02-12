#include <stdio.h>
#include <stdlib.h>

int main() {
    int arrayLength = 0;
    printf("Please enter array length: ");

    /*Using scanf to take input from the user*/
    scanf("%i", & arrayLength);

    /*Using the malloc function to allocate memory to the array that we're declaring
    and using the input that the user provided as the size of the array.*/
    int *Array = (int *)malloc(arrayLength *sizeof(int));

    /*Using scanf to take input for the elements of the array*/
    for (int i= 0; i< arrayLength; i++){
        printf("Please enter element:");
        scanf("%i", & Array[i]);
    }
    /*Sorting the array using the selection sort algorithm*/
    for(int i= 0; i< arrayLength-1; i++){
        int iMin = i;
        for(int j= i+1; j< arrayLength; j++){
            if(Array[j]< Array[iMin])
                iMin = j;
        }
        int temp= Array[i];
        Array[i]= Array[iMin];
        Array[iMin] = temp;
    }

    /*Printing the elements of the sored array*/ 
    for(int i= 0; i< arrayLength-1; i++){
        printf("%i, ", Array[i]);
    }
    printf("%i\n", Array[arrayLength-1]);

    /*Using the free function to free up the space that was allocated to the array by malloc function*/
    free(Array);
}