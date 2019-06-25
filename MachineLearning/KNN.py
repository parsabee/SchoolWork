__author__ = 'Parsa Bagheri'

from statistics import stdev
from numpy import mean

data = [
['ID', 'Total Accounts', 'Utilization', 'Payment History', 'Age of History', 'Inquiries', 'Label'],
[1, 8, 15, 100, 1000, 5, 'GOOD'],
[2, 15, 19, 90, 2500, 8, 'BAD'],
[3, 10, 35, 100, 500, 10, 'BAD'],
[4, 11, 40, 95, 2000, 6, 'BAD'],
[5, 12, 10, 99, 3000, 6, 'GOOD'],
[6, 18, 15, 100, 2000, 5, 'GOOD']
]

development_data = [
['ID', 'Total Accounts', 'Utilization', 'Payment History', 'Age of History', 'Inquiries', 'Label'],
[7, 3, 21, 100, 1500, 7, 'BAD'],
[8, 14, 4, 100, 3500, 5, 'GOOD'],
[9, 13, 5, 100, 3000, 3, 'GOOD'],
[10, 6, 25, 94, 2800, 9, 'BAD']
]

test = [
['Total Accounts', 'Utilization', 'Payment History', 'Age of History', 'Inquiries'],
[20, 50, 90, 4500, 12],
[8, 10, 100, 550, 4],
[9, 13, 99, 3000, 6]
]

class NearestNeighbor (object):
	def __init__ (self, training_data, standard):
		self.__data = training_data
		self.__std_divs, self.__means = self.__get_stats ()
		self.__standard = standard
		self.__std_data = self.__standardize()
		
	def __euclidean_dist (self, vector_1, vector_2):
		'''
		takes euclidean distance ^ 2
		'''
		dist = 0
		for i in range (len (vector_1)):
			dist += abs(vector_2[i] - vector_1[i])
		return dist

	def __chng_mjr (self, data):
		'''
		takes a row major matirx and makes it column major or vice versa
		'''
		matrix = []
		for i in range(len(data[0])):
			col = []
			for j in range (len(data)):
				col.append (data[j][i])
			matrix.append(col)
		return matrix

	def __get_stats (self):
		'''
		returns the standatd diviation and mean of a data set
		'''
		data_cpy = []
		for i in range( 1, len(self.__data)): # getting rid of header
			data_cpy.append (self.__data[i])
		col_mjr = self.__chng_mjr (data_cpy)
		col_mjr.pop (0) # removing the ID column
		col_mjr.pop () # removing the label column
		stdevs = []
		means = []
		for i in col_mjr:
			stdevs.append (round(stdev(i), 2))
			means.append (round(mean(i), 2))
		return stdevs, means

	def __standardize (self):
		'''
		standardizes a data set
		'''
		data_cpy = []
		for i in range( 1, len(self.__data)): # getting rid of header
			data_cpy.append (self.__data[i])
		data_cpy = self.__chng_mjr (data_cpy)
		data_cpy.pop (0)
		data_cpy.pop ()
		if not self.__standard:
			for col in range( len(data_cpy)):
				for i in range (len (data_cpy [col])):
					data_cpy [col][i] = round (((data_cpy [col][i] - self.__means[col]) / self.__std_divs[col]), 2)
		return self.__chng_mjr (data_cpy)

	def __vec_standardize (self, vector, data):
		'''
		standardizes a vector based on data
		'''
		
		vec = []
		for i in range (len (vector)):
			elm = round (((vector [i] - self.__means [i]) / self.__std_divs [i]), 2)
			vec.append (elm)
		return vec

	def predict (self, K, test_data):
		'''
		standardizes the data and
		predicts the label of the vector based on the k nearest neighbor
		'''
		test_cpy = []
		labels = None

		for vector in test_data:
			test_cpy.append(vector)

		if isinstance(test_data [0][0], str):
			test_cpy.pop(0)
			if test_data [0][0] == 'ID':
				for vector in test_cpy:
					vector.pop(0)

		if (test_data [0][-1] == 'Label'): # if labels are provided, storing them for future comparison
			labels = []
			for vector in test_cpy:
				labels.append (vector [-1])
				vector.pop()

		predictions = []
		for vector in test_cpy:
			results = []
			length = len (self.__std_data)

			std_vector = vector
			if not self.__standard:
				std_vector = self.__vec_standardize (vector, self.__data)
			for i in range ( length):
				dist = self.__euclidean_dist (self.__std_data [i], std_vector)
				results.append ((dist, i+1))

			pos = 0
			neg = 0
			results.sort()
			if K > len(results):
				print 'Error: k too large'
				return

			for k in range (K):
				index = results [k][1]
				if self.__data [index][-1] == 'GOOD':
					pos += 1
				elif self.__data [index][-1] == 'BAD':
					neg += 1


			if pos > neg:
				predictions.append ('GOOD')

			# elif pos == neg: #if there is a tie, preference is given to the closes neighbor
			# 	index = results [0][1]
			# 	predictions.append (self.__data [index][-1])

			else: #if there is a tie, preference is given to bad
				predictions.append ('BAD')

		if labels != None:
			correct = 0
			total = len (labels)
			for i in range (total):
				if predictions [i] == labels [i]:
					correct += 1
			print 'Accuracy: {}%'.format(((float (correct) / float (total)) * 100.0))
		print predictions
		return predictions

if __name__ == '__main__':

	classifier = NearestNeighbor (training_data = data, standard = False)
	classifier.predict (2, development_data)