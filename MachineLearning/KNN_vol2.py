__author__ = 'Parsa Bagheri'

import numpy as np
import matplotlib.pyplot as graph

def generate_data (num_dimensions, num_points):

	test = [[np.random.randint(0, 1 + 1) for i in range (num_dimensions)] for j in range (num_points)]

	i = 0
	half = num_points / 2
	while i < half:
		test [i][0] = 0
		i += 1
	while i < num_points:
		test [i][0] = 1
		i += 1

	return test

def get_euc_dist (vector_1, vector_2):
	dist = 0
	for i in range (len (vector_1)):
		dist += abs (vector_1 [i]- vector_2[i])
	return dist	

def get_label (data):
	labels = []
	for vec in data:
		labels.append (vec [0])
	return labels

def key (tupl):
	return tupl[0]

def KNN (K, train_data, test_data):
	train_labels = get_label (train_data)
	test_labels = get_label (test_data)

	predictions = []
	length = len (test_data)
	for vector in test_data:
		results = []
		for i in range (length):
			dist = get_euc_dist (vector, train_data[i])
			results.append((dist, i))

		results.sort (key = key)
		num_1 = 0
		num_0 = 0
		for k in range (K):
			if train_labels [results [k][1]] == 0:
				num_0 += 1
			else:
				num_1 += 1

		if num_1 > num_0:
			predictions.append (1)

		else: #preference is given to 0
			predictions.append (0)

	correct = 0
	total = len (test_data)
	for i in range (len (predictions)):
		if predictions[i] == test_labels[i]:
			correct += 1

	accuracy = round ((float (correct)/ float (total)) * 100 , 2)
	# print ('Accuracy: {}%'.format (accuracy))
	return accuracy

def avrg(num_dimensions, num_vectors):
	K = 1
	results = []
	for i in range (10):
		train_data = generate_data(num_dimensions, num_vectors)
		test_data = generate_data(num_dimensions, num_vectors)
		results.append (KNN (K, train_data, test_data))
	avg_accuracy = np.mean (results)
	print ('# dimensions: {}, average accuracy: {}%'.format (num_dimensions, avg_accuracy))
	return avg_accuracy

if __name__ == '__main__':
	dimensions = [5, 10, 20, 50, 100]
	accuracies = []
	for dim in dimensions:
		accuracies.append (avrg (dim, 100))

	graph.plot (dimensions, accuracies)
	graph.xlabel ('dimension')
	graph.ylabel ('accuracy')
	# graph.show ()
	graph.savefig ('dimXacc.png')



