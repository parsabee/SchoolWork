#include <cstdlib>
#include <cstdio>
#include <mpi.h>

#define N_ITER 1000

void swap(double **&input, double **&output){
	double **temp = input;
	input = output;
	output = temp; 
}

int main(int argc, char *argv[]){

	static int n = 1000;

	int proc_size, proc_id, i, j, iter;

	/*Initializng MPI*/
	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &proc_id);
	MPI_Comm_size(MPI_COMM_WORLD, &proc_size);
	MPI_Status status;

	/*
	halo takes the value of the rows that are sent, 
	and ghost takes the last element of the rows that are sent
	*/
	double halo, ghost;

	double c = 0.1;
	double ds = 1.0/(10+1);
	double dt = (ds*ds)/(4*c);

	double num = c*(dt/(ds*ds));

	MPI_Barrier(MPI_COMM_WORLD);
	double start = MPI_Wtime();

	/***************
	MASTER PROCESSOR
	***************/

	if(proc_id==0){
		double **input = new double*[(n+2)/2];
		double **output = new double*[(n+2)/2];

		for(i=0; i<(n+2)/2; i++){
			input[i] = new double[(n+2)/2];
			output[i] = new double[(n+2)/2];
		}

		/*
		INITIALIZING
		*/
		for(i=0; i<((n+2)/2); i++){
			for(j=0; j<(n+2)/2; j++){
				if(i==0 || j==0){
					input[i][j] = 100;
					output[i][j] =100;
				}
				else{
					input[i][j] = -100;
					output[i][j] = 0;
				}
				
			}
		}

		/*
		following is what master processor starts with:

		-------------------------------------------
		_input_temp:

		100		100		100		100		100		...
		100		-100		-100		-100		-100		...
		100		-100		-100		-100		-100		...
		100		-100		-100		-100		-100		...
		100		-100		-100		-100		-100		...
		...		...		...		...		...		...
		-------------------------------------------
		_output_temp:

		100		100		100		100		100		...
		100		0		0		0		0		...
		100		0		0		0		0		...
		100		0		0		0		0		...
		100		0		0		0		0		...
		...		...		...		...		...		...
		-------------------------------------------
		*/
		
		for(iter=0; iter<N_ITER ; iter++){
			for(i=1; i<((n+2)/2); i++){
				for(j=1; j<((n+2)/2); j++){
					if(i==((n+2)/2)-1){
						MPI_Recv(&halo, 1, MPI_DOUBLE, 1, 1, MPI_COMM_WORLD, &status);
						if(j==(((n+2)/2)-1)){
							MPI_Recv(&ghost, 1, MPI_DOUBLE, 2, 31, MPI_COMM_WORLD, &status);
							output[i][j] = 
							input[i][j] + (num * (halo + input[i-1][j] - 
							(4*input[i][j]) + ghost + input[i][j-1]));
							MPI_Send(&input[i][j], 1, MPI_DOUBLE, 2, 13, MPI_COMM_WORLD);
						}
						else{
							output[i][j] = 
							input[i][j] + (num * (halo + input[i-1][j] - 
							(4*input[i][j]) + input[i][j+1] + input[i][j-1]));
						}
						MPI_Send(&input[i][j], 1, MPI_DOUBLE, 1, 2, MPI_COMM_WORLD);
					}
					else{
						if(j==(((n+2)/2)-1)){
							MPI_Recv(&ghost, 1, MPI_DOUBLE, 2, 31, MPI_COMM_WORLD, &status);
							output[i][j] = 
							input[i][j] + (num * (halo + input[i-1][j] - 
							(4*input[i][j]) + ghost + input[i][j-1]));
							MPI_Send(&input[i][j], 1, MPI_DOUBLE, 2, 13, MPI_COMM_WORLD);
						}
						else{
							output[i][j] = 
							input[i][j] + (num * (halo + input[i-1][j] - 
							(4*input[i][j]) + input[i][j+1] + input[i][j-1]));
						}
					}
				}
			}
			swap(input, output);
		}

		/*for debugging and checking the results*/
		// printf("###rank0###\n");
		// for(i=0; i<(n+2)/2; i++){
		// 	for(j=0; j<(n+2)/2; j++){
		// 		printf("%0.2f	", output[i][j]);
		// 	}
		// 	printf("\n");
		// }
		for(i=0; i<(n+2)/2; i++){
			delete [] input[i];
			delete [] output[i];
		}
		delete [] input;
		delete [] output;
	}

	/*****
	rank 1
	*****/
	if(proc_id==1){

		double **input = new double*[(n+2)/2];
		double **output = new double*[(n+2)/2];

		for(i=0; i<(n+2)/2; i++){
			input[i] = new double[(n+2)/2];
			output[i] = new double[(n+2)/2];
		}

		/*
		INITIALIZING
		*/
		for(i=0; i<(n+2)/2; i++){
			for(j=0; j<(n+2)/2; j++){
				if(i==((n+2)/2)-1 || j==0){
					input[i][j] = 100;
					output[i][j] =100;
				}
				else{
					input[i][j] = -100;
					output[i][j] = 0;
				}
				
			}
		}

		/*
		following is what processor rank 1 starts with:

		-------------------------------------------
		_input_temp:
		...		...		...		...		...		...
		100		-100		-100		-100		-100		...
		100		-100		-100		-100		-100		...
		100		-100		-100		-100		-100		...
		100		-100		-100		-100		-100		...
		100		100		100		100		100		...
		-------------------------------------------
		_output_temp:
		...		...		...		...		...		...
		100		0		0		0		0		...
		100		0		0		0		0		...
		100		0		0		0		0		...
		100		0		0		0		0		...
		100		100		100		100		100		...
		-------------------------------------------
		*/
		

		for(iter=0; iter<N_ITER; iter++){
			for(i=0; i<((n+2)/2)-1; i++){
				for(j=1; j<((n+2)/2); j++){
					if(i==0){
						MPI_Send(&input[i][j], 1, MPI_DOUBLE, 0, 1, MPI_COMM_WORLD);
						MPI_Recv(&halo, 1, MPI_DOUBLE, 0, 2, MPI_COMM_WORLD, &status);
						if(j==((n+2)/2)-1){
							MPI_Recv(&ghost, 1, MPI_DOUBLE, 3, 42, MPI_COMM_WORLD, &status);
							output[i][j] = 
							input[i][j] + (num * (input[i+1][j] + halo - 
							(4*input[i][j]) + ghost + input[i][j-1]));
							MPI_Send(&input[i][j], 1, MPI_DOUBLE, 3, 24, MPI_COMM_WORLD);
						}
						else{
							output[i][j] = 
							input[i][j] + (num * (input[i+1][j] + halo - 
							(4*input[i][j]) + input[i][j+1] + input[i][j-1]));
						}
					}else{
						if(j==((n+2)/2)-1){
							MPI_Recv(&ghost, 1, MPI_DOUBLE, 3, 42, MPI_COMM_WORLD, &status);
							output[i][j] = 
							input[i][j] + (num * (input[i+1][j] + input[i-1][j] - 
							(4*input[i][j]) + ghost + input[i][j-1]));
							MPI_Send(&input[i][j], 1, MPI_DOUBLE, 3, 24, MPI_COMM_WORLD);
						}
						else{
							output[i][j] = 
							input[i][j] + (num * (input[i+1][j] + input[i-1][j] - 
							(4*input[i][j]) + input[i][j+1] + input[i][j-1]));
						}
					}
				}
			}
			swap(input, output);
		}
		
		
		/*for debugging and checking the results*/
		// printf("###rank1###\n");
		// for(i=0; i<(n+2)/2; i++){
		// 	for(j=0; j<(n+2)/2; j++){
		// 		printf("%0.2f	", output[i][j]);
		// 	}
		// 	printf("\n");
		// }
		for(i=0; i<(n+2)/2; i++){
			delete [] input[i];
			delete [] output[i];
		}
		delete [] input;
		delete [] output;
	}

	/*****
	rank 2
	*****/
	if(proc_id == 2){
		double **input = new double*[(n+2)/2];
		double **output = new double*[(n+2)/2];

		for(i=0; i<(n+2)/2; i++){
			input[i] = new double[(n+2)/2];
			output[i] = new double[(n+2)/2];
		}

		/*
		INITIALIZING
		*/
		for(i=0; i<(n+2)/2; i++){
			for(j=0; j<(n+2)/2; j++){
				if(i==0 || j==((n+2)/2)-1){
					input[i][j] = 100;
					output[i][j] =100;
				}
				else{
					input[i][j] = -100;
					output[i][j] = 0;
				}
				
			}
		}


		/*
		following what processor rank 2 starts with:

		-------------------------------------------
		_input_temp:

		...		100		100		100		100		100
		...		-100		-100		-100		-100		100
		...		-100		-100		-100		-100		100
		...		-100		-100		-100		-100		100
		...		-100		-100		-100		-100		100
		...		...		...		...		...		...
		-------------------------------------------
		_output_temp:

		...		100		100		100		100		100
		...		0		0		0		0		100
		...		0		0		0		0		100
		...		0		0		0		0		100
		...		0		0		0		0		100
		...		...		...		...		...		...
		-------------------------------------------
		*/


		for(iter=0; iter<N_ITER ; iter++){
			for(i=1; i<((n+2)/2); i++){
				for(j=0; j<(((n+2)/2)-1); j++){
					if(i==(((n+2)/2)-1)){
						MPI_Recv(&halo, 1, MPI_DOUBLE, 3, 43, MPI_COMM_WORLD, &status);
						if(j==0){
							MPI_Send(&input[i][j], 1, MPI_DOUBLE, 0, 31, MPI_COMM_WORLD);
							MPI_Recv(&ghost, 1, MPI_DOUBLE, 0, 13, MPI_COMM_WORLD, &status);
							output[i][j] = 
							input[i][j] + (num * (halo + input[i-1][j] - 
							(4*input[i][j]) + input[i][j+1] + ghost));
						}
						else{
							output[i][j] = 
							input[i][j] + (num * (halo + input[i-1][j] - 
							(4*input[i][j]) + input[i][j+1] + input[i][j-1]));
						}
						MPI_Send(&input[i][j], 1, MPI_DOUBLE, 3, 34, MPI_COMM_WORLD);
					}
					else{
						if(j==0){
							MPI_Send(&input[i][j], 1, MPI_DOUBLE, 0, 31, MPI_COMM_WORLD);
							MPI_Recv(&ghost, 1, MPI_DOUBLE, 0, 13, MPI_COMM_WORLD, &status);
							output[i][j] = 
							input[i][j] + (num * (input[i+1][j] + input[i-1][j] - 
							(4*input[i][j]) + input[i][j+1] + ghost));
						}
						else{
							output[i][j] = 
							input[i][j] + (num * (input[i+1][j] + input[i-1][j] - 
							(4*input[i][j]) + input[i][j+1] + input[i][j-1]));
						}
					}
				}
			}
			swap(input, output);
		}

		/*for debugging and checking the results*/
		// printf("###rank2###\n");
		// for(i=0; i<(n+2)/2; i++){
		// 	for(j=0; j<(n+2)/2; j++){
		// 		printf("%0.2f	", output[i][j]);
		// 	}
		// 	printf("\n");
		// }
		for(i=0; i<(n+2)/2; i++){
			delete [] input[i];
			delete [] output[i];
		}
		delete [] input;
		delete [] output;
	}

	/*****
	rank 3
	*****/
	if(proc_id == 3){
		double **input = new double*[(n+2)/2];
		double **output = new double*[(n+2)/2];

		for(i=0; i<(n+2)/2; i++){
			input[i] = new double[(n+2)/2];
			output[i] = new double[(n+2)/2];
		}

		/*
		INITIALIZING
		*/
		for(i=0; i<(n+2)/2; i++){
			for(j=0; j<(n+2)/2; j++){
				if(i==((n+2)/2)-1 || j==((n+2)/2)-1){
					input[i][j] = 100;
					output[i][j] =100;
				}
				else{
					input[i][j] = -100;
					output[i][j] = 0;
				}
				
			}
		}

		/*
		following what 4th processor starts with:

		-------------------------------------------
		_input_temp:
		...		...		...		...		...		...
		...		-100		-100		-100		-100		100
		...		-100		-100		-100		-100		100
		...		-100		-100		-100		-100		100
		...		-100		-100		-100		-100		100
		...		100		100		100		100		100
		-------------------------------------------
		_output_temp:
		
		...		...		...		...		...		...
		...		0		0		0		0		100
		...		0		0		0		0		100
		...		0		0		0		0		100
		...		0		0		0		0		100
		...		100		100		100		100		100
		-------------------------------------------
		*/

		for(iter=0; iter<N_ITER; iter++){
			for(i=0; i<((n+2)/2)-1; i++){
				for(j=0; j<((n+2)/2)-1; j++){
					if(i==0){
						MPI_Send(&input[i][j], 1, MPI_DOUBLE, 2, 43, MPI_COMM_WORLD);
						MPI_Recv(&halo, 1, MPI_DOUBLE, 2, 34, MPI_COMM_WORLD, &status);
						if(j==0){
							MPI_Send(&input[i][j], 1, MPI_DOUBLE, 1, 42, MPI_COMM_WORLD);
							MPI_Recv(&ghost, 1, MPI_DOUBLE, 1, 24, MPI_COMM_WORLD, &status);
							output[i][j] = 
							input[i][j] + (num * (input[i+1][j] + halo - 
							(4*input[i][j]) + input[i][j+1] + ghost));
						}
						else{
							output[i][j] = 
							input[i][j] + (num * (input[i+1][j] + halo - 
							(4*input[i][j]) + input[i][j+1] + input[i][j-1]));
						}
					}
					else{
						if(j==0){
							MPI_Send(&input[i][j], 1, MPI_DOUBLE, 1, 42, MPI_COMM_WORLD);
							MPI_Recv(&ghost, 1, MPI_DOUBLE, 1, 24, MPI_COMM_WORLD, &status);
							output[i][j] = 
							input[i][j] + (num * (input[i+1][j] + input[i-1][j] - 
							(4*input[i][j]) + input[i][j+1] + ghost));
						}
						else{
							output[i][j] = 
							input[i][j] + (num * (input[i+1][j] + input[i-1][j] - 
							(4*input[i][j]) + input[i][j+1] + input[i][j-1]));
						}
					}
				}
			}
			swap(input, output);
		}


		/*for debugging and checking the results*/
		// printf("###rank3###\n");
		// for(i=0; i<(n+2)/2; i++){
		// 	for(j=0; j<(n+2)/2; j++){
		// 		printf("%0.2f	", output[i][j]);
		// 	}
		// 	printf("\n");
		// }
		for(i=0; i<(n+2)/2; i++){
			delete [] input[i];
			delete [] output[i];
		}
		delete [] input;
		delete [] output;
	}

	MPI_Barrier(MPI_COMM_WORLD);
	double finish = MPI_Wtime();

	MPI_Finalize();

	if(proc_id == 0){
		printf("total time taken: %f seconds\n", finish - start);
	}
}
