#include <stdio.h>
#define M 13 
typedef int Marray_t[M][M];
void tranpose(Marray_t A) {
    int i, j;
    for(i = 0; i < M; i++) {            // i indicates the number of rows in the matrix,
        for(j = 0; j < i; j++) {		// j indicates the number of rows in the matrix,
            int t1 = A[i][j];			// t1 is the %eax register, and (%ebx) holds A[i][j]
            int t2 = A[j][i];			// t2 is the %edx register, and (%esi, %ecx, 4) holds A[j][i]
            A[j][i]= t1;				//these two lines are where transposition happens
            A[i][j]= t2;
        }
    }
}
int main(){

	int A [13][13];						// create a matrix of 13 rows and 13 columns, filling every row with 1-13
	for(int i=0; i<13; i++){		
		for(int j = 0; j<13; j++){
			A[i][j]= j;
		} 
	}
	for(int i =0; i<13; i++){			// printing the matrix before transposing
		printf("\n");
		for(int j=0; j<13; j++)
			printf("%d ", A[i][j]);
	}
    printf("\n");
	tranpose(A);

	for(int i =0; i<M; i++){			// printing matrix after transposing
		printf("\n");
		for(int j=0; j<M; j++)
			printf("%d ", A[i][j]);
	}
}