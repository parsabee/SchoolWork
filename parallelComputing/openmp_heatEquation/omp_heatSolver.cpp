#include <stdlib.h>
#include <stdio.h>
#include <iostream>
#include <chrono>
#include <omp.h>

#define NITER 100000 /*number of iterations*/
#define NUM_THREADS 4 /*number of threads*/
using namespace std;

static int n = 100;/*number of rows and columns*/


void initializeInput(double **&temp){
	int i, j;
	for(i=0; i<n+2; i++){
		for(j=0; j<n+2; j++){
			if(i==0 || i==n+1 || j==0 || j==n+1)
				temp[i][j] = 100.0;
			else
				temp[i][j] = -100.0;
		}
	}
}

void initializeOutput(double **&temp){
	int i, j;
	for(i=0, j=0; i<n+2, j<n+2; i++, j++){
		temp[i][0] = 100.0;
		temp[i][n+1] = 100.0;
		temp[0][j] = 100.0;
		temp[n+1][j] = 100.0;
	}
}

void swap(double **&input, double **&output){
	double **temp = input;
	input = output;
	output = temp; 
}

int main(int argc, char *argv[]){

	int i, j, iter;
	double **_input_temp = new double*[n+2];
	double **_output_temp = new double*[n+2];

	for(i=0; i<(n+2); ++i)
		_input_temp[i] = new double[n+2];

	for(i=0; i<(n+2); ++i)
		_output_temp[i] = new double[n+2];

	initializeInput(_input_temp);
	initializeOutput(_output_temp);

	/*constants*/
	double c = 0.1;
	double ds = 1.0/(n+1);
	double dt = (ds*ds)/(4*c);

	double num = c*(dt/(ds*ds));

	/*timing the execution of forloops*/
	chrono::system_clock::time_point t1 = chrono::system_clock::now();

	for(iter=0; iter<NITER; iter++){

		omp_set_num_threads(NUM_THREADS);
		#pragma omp parallel for collapse(2)
		for(i=1; i<(n+1); i++){
			for(j=1; j<(n+1); j++){
				_output_temp[i][j] = 
				_input_temp[i][j] + (num * (_input_temp[i+1][j] + _input_temp[i-1][j] - 
				(4*_input_temp[i][j]) + _input_temp[i][j+1] + _input_temp[i][j-1]));
			}
		}

		swap(_input_temp, _output_temp);
	}

	chrono::system_clock::time_point t2 = chrono::system_clock::now();

	chrono::steady_clock::duration duration = t2-t1;
	cout << "\nopenmp writing time : " << chrono:: duration_cast<chrono::milliseconds>(duration).count() << " milliseconds\n\n";
	

	/*
	outputting a file containing the content of the output array,
	this file can be used to compare the results of serial and parallel versions of the code
	*/
	FILE *outputArray = fopen("omp_outputArray.txt", "w");

	for(i=0; i<(n+2); ++i){
		for(j=0; j<(n+2); ++j)
			fprintf(outputArray,"%lf	", _output_temp[i][j]);
		fprintf(outputArray, "\n");
	}
	
	fclose(outputArray);

	for(i=0; i<(n+2); ++i){
		delete [] _input_temp[i];
		delete [] _output_temp[i];
	}

	delete [] _input_temp;
	delete [] _output_temp;


}