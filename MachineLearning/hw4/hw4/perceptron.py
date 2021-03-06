#!/usr/bin/python
#
# CIS 472/572 - Perceptron Template Code
#
# Template: Daniel Lowd <lowd@cs.uoregon.edu>
# Implementation: Parsa Bagheri
#
import sys
import re
from math import log
from math import exp

MAX_ITERS = 100

# Load data from a file
def read_data(filename):
	f = open(filename, 'r')
	p = re.compile(',')
	data = []
	header = f.readline().strip()
	varnames = p.split(header)
	namehash = {}
	for l in f:
		example = [int(x) for x in p.split(l.strip())]
		x = example[0:-1]
		y = example[-1]
		# Each example is a tuple containing both x (vector) and y (int)
		data.append( (x,y) )
	return (data, varnames)


def parse_data (data):
	features = []
	labels = []
	for i in data:
		features.append(i[0])
		labels.append(i[1])
	return (features, labels)

def activation (W, b, X):
	a = 0.0
	for i in range(len (X)):
		a += W[i] * X[i]
	return a + b

# Learn weights using the perceptron algorithm
def train_perceptron(data):
	# Initialize weight vector and bias
	numvars = len(data[0][0])
	w = [0.0] * numvars
	b = 0.0
	features, labels = parse_data (data)
	numfeatures = len(features)
	for i in range (MAX_ITERS):
		for j in range (numfeatures):
			y = labels[j]
			a = activation (w, b, features[j])
			if y * a <= 0.0:
				for k in range(numvars):
					w[k] += y * features[j][k]
				b += y
	return (w,b)


# Compute the activation for input x.
# (NOTE: This should be a real-valued number, not simply +1/-1.)
def predict_perceptron(model, x):
	(w,b) = model
	a = activation (w, b, x)
	return a


# Load train and test data.	Learn model.	Report accuracy.
def main(argv):
	# Process command line arguments.
	# (You shouldn't need to change this.)
	if (len(argv) != 3):
		print('Usage: perceptron.py <train> <test> <model>')
		sys.exit(2)
	(train, varnames) = read_data(argv[0])
	(test, testvarnames) = read_data(argv[1])
	modelfile = argv[2]

	# Train model
	(w,b) = train_perceptron(train)

	# Write model file
	# (You shouldn't need to change this.)
	f = open(modelfile, "w+")
	f.write('%f\n' % b)
	for i in range(len(w)):
		f.write('%s %f\n' % (varnames[i], w[i]))

	# Make predictions, compute accuracy
	correct = 0
	for (x,y) in test:
		activation = predict_perceptron( (w,b), x )
		print(activation)
		if activation * y > 0:
			correct += 1
	acc = float(correct)/len(test)
	print("Accuracy: ",acc)

if __name__ == "__main__":
	main(sys.argv[1:])
