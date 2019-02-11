// OSX compilation:
//    g++ -std=c++11 -I/opt/X11/include -o myfilter3 tbb_myfilter3.cpp -L/opt/X11/lib -lX11 -ljpeg -ltbb


#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <string.h>
#include <fstream>
#include <iostream>
#include <dirent.h>
#include <sys/stat.h>
#include <chrono>
#include <tbb/tbb.h>
#define cimg_use_jpeg
#include "CImg.h"

using namespace std;
using namespace cimg_library;

int 
main(int argv, char *argc[])
{
	if(argv != 2){
		cerr << "Insufficient arguments" << endl;
		return 0;
	}
	ifstream inFile;
	inFile.open(argc[1]);
	if(!inFile){
		cerr << "Unable to open file"<< endl;
		return 0;
	}

	
	string inputfile[20];
	int numLines = 0;
	for(int index=0; index<20; index++){
		getline(inFile, inputfile[index]);
		numLines++;
	}
	if(numLines !=20){
			cerr << "textfile has to contain exactly 20 paths to jpg images! right now it contans: "<<numLines << endl;
			return 0;
		}

	cout << "\nreading the list in parallel, processing every image in parallel\n" << endl;

	/*take the overall time of execution*/
	chrono::system_clock::time_point overall_t1 = chrono::system_clock::now();

	/*read 20 lines of the input textfile*/
	tbb::parallel_for(size_t(0), size_t(20), [&](size_t a){
		CImg<unsigned char> input_img(inputfile[a].c_str());

		unsigned int width = input_img.width();
		unsigned int height = input_img.height();

		/*create a jpg image to be outputted (width, height, depth, channels (RGB))*/
		CImg<unsigned char> output_img(width, height, 1, 3);

		/*timing the execution time*/
		chrono::system_clock::time_point t1 = chrono::system_clock::now();

		/*using the number of threads that TBB thinks we should use*/
		tbb::task_scheduler_init init(tbb::task_scheduler_init::automatic);
		// tbb::task_scheduler_init init(1);
		tbb::parallel_for(size_t(0), size_t(width), [&](size_t i) {
			tbb::parallel_for(size_t(0), size_t(height), [&](size_t j){
				int R=0, G=0, B=0;
				int x, y;
				if(i>=5 &&  j>=5 && i<=(input_img.width()-5) && j<=(input_img.height()-5)){

					/*making every pixel blury by taking the average of its 80 neighboring pixels RGB*/
					for(x=0; x<9; x++){
						for(y=0; y<9; y++){
							R+=(input_img(i-4+x, j-4+y, 0))/81;
							G+=(input_img(i-4+x, j-4+y, 1))/81;
							B+=(input_img(i-4+x, j-4+y, 2))/81;
						}
					}
					output_img(i, j, 0) = R; 
					output_img(i, j, 1) = G;
					output_img(i, j, 2) = B;
				}else{
					output_img(i, j, 0) = R; 
					output_img(i, j, 1) = G;
					output_img(i, j, 2) = B;
				}
			});
		});
		chrono::system_clock::time_point t2 = chrono::system_clock::now();
		chrono::steady_clock::duration duration = t2-t1;
		cout << "\nparallel writing time for "<< inputfile[a] <<": "<< chrono:: duration_cast<chrono::microseconds>(duration).count()<<" microseconds\n\n";
		
		string outputfile = inputfile[a];
		outputfile.insert(outputfile.end()-4, '2');
		output_img.save_jpeg(outputfile.c_str());
	});
	chrono::system_clock::time_point overall_t2 = chrono::system_clock::now();
	chrono::steady_clock::duration overall_duration = overall_t2-overall_t1;
	cout << "\noverall writing time: "<< chrono:: duration_cast<chrono::microseconds>(overall_duration).count()<<" microseconds\n\n";

	inFile.close();
	return 1;
}

