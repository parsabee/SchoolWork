#TBB parallelization  
In this program, I use tbb for loops (parallel_for) to process images.  
the program takes a textfile containing 20 paths to jpg files.  
tbb_myfilter1, sequentially read the textfile and parallelly processes images. to compile tbb_myfilter1, write command: make one  
tbb_myfilter2, parallelly reads the textfile and sequentially processes images. to compile tbb_myfilter2, write command: make two  
tbb_myfilter3, parallelly reads the textfile and parallelly processes images. to compile tbb_myfilter3, write command: make three  
