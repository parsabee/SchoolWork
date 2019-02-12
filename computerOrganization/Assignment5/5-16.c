#include<stdio.h>
#include<time.h>
#include <stdlib.h>
#include <sys/time.h>
#define VECTOR_SIZE 10000000
typedef long long data_t;
typedef data_t *vec_ptr;
data_t * get_vec_start(vec_ptr v) {
return v;
}
int vec_length(vec_ptr v) {
return VECTOR_SIZE;
}
void inner4(vec_ptr u, vec_ptr v, data_t *dest) 
 { 
	 long long i; 
	 int length = vec_length(u); 
	 int unrolled_length = length-3;
	 data_t *udata = get_vec_start(u); 
	 data_t *vdata = get_vec_start(v); 
	 data_t sum = (data_t) 0; 

	 for (i = 0; i < unrolled_length; i+=4) { 
	 	sum = sum + udata[i] * vdata[i];
	 	sum = sum + udata[i+1] * vdata[i+1];
	 	sum = sum + udata[i+2] * vdata[i+2];
	 	sum = sum + udata[i+3] * vdata[i+3];
	 	printf("%d \n", sum);
	 } 
	 for (; i < unrolled_length; i++) {
	 	sum = sum + udata[i] * vdata[i];
	 	// printf("%d \n", sum);
	 }

	 *dest = sum; 
 }
int main(){
	srand(time(NULL));
	struct timeval start;
	gettimeofday(&start, NULL);
	vec_ptr ap =(vec_ptr) malloc(sizeof(data_t)*VECTOR_SIZE);
	for (data_t i = 0; i < VECTOR_SIZE; i++) {
		ap[i] = rand();
	}
	vec_ptr bp =(vec_ptr) malloc(sizeof(data_t)*VECTOR_SIZE);
	for (data_t i = 0; i < VECTOR_SIZE; i++) {
		bp[i] = rand();
	}
	data_t dest = (data_t) 0;
	data_t * destp = &dest;
	inner4(ap, bp, destp);
	struct timeval current;
	gettimeofday(&current, NULL);
	long long elapsed = (current.tv_sec - start.tv_sec)*1000000LL + (current.tv_usec - start.tv_usec);
	long long beginning = start.tv_sec + start.tv_usec;
	free(ap);
	free(bp);
	printf("\n");
	printf("%f\n", elapsed/1000000.0);
}
