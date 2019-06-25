#!/usr/bin/python
#
# CIS 472/572 -- Programming Homework #1
#
# Starter code provided by Daniel Lowd
#
#
import sys
import re
# Node class for the decision tree
import node
import math


train=None
varnames=None
test=None
testvarnames=None
root=None

# Helper function computes entropy of Bernoulli distribution with
# parameter p
def entropy(p):
	'''
	calculate the entropy of a set
	Entropy (S) = - p(+) log2(p(+)) - p(-) log2(p(-))
	'''
	if p == 0.0 or p == 1.0:
		return 0
	n = 1.0 - p
	return (- (p * math.log(p, 2)) - (n * math.log(n, 2)))


# Compute information gain for a particular split, given the counts
# py_pxi : number of occurences of y=1 with x_i=1 for all i=1 to n
# pxi : number of occurrences of x_i=1
# py : number of ocurrences of y=1
# total : total length of the data
def infogain(py_pxi, pxi, py, total):
	py_pxi_n = py - py_pxi # number of occurences of x_i=0 with y=1
	pxi_n = total - pxi # number of occurences of x_i=0
	
	p = float (py) / total
	ent_s = entropy(p)
	
	try:
		pos = float (pxi) / total
		pv = float (py_pxi) / pxi
		ent_l = entropy(pv)
		left = pos * (ent_l)
	except ZeroDivisionError:
		pos = 0
		left = 0
		ent_l = 0

	try:
		neg = 1.0 - pos
		pn = float (py_pxi_n) / pxi_n
		ent_r = entropy(pn)
		right = neg * ent_r
	except ZeroDivisionError:
		neg = 0
		right = 0
		ent_r = 0
	
	return ent_s - left - right


# OTHER SUGGESTED HELPER FUNCTIONS:
# - collect counts for each variable value with each class label
# - find the best variable to split on, according to mutual information
# - partition data based on a given variable

def collect_count (data):
	total = len(data)
	py = 0
	target = len(data[0])-1
	for i in data:
		if i[target] == 1:
			py += 1
	return py, total

def find_split (data, varnames, py, total):
	best = -1;
	for i in range(len(varnames) - 1):
		py_pxi = 0
		pxi = 0
		for j in data:
			if j[i] == 1:
				pxi += 1
			if j[i] == 1 and j[-1] == 1:
				py_pxi += 1
		gain = infogain(py_pxi, pxi, py, total) 
		if best < gain:
			best = gain
			index = i
	return best, index

# Build tree in a top-down manner, selecting splits until we hit a
# pure leaf or all splits look bad.
def build_tree(data, varnames):
	
	py, total = collect_count (data)
	guess = float (py) / total
	if guess == 1 or (len(varnames) == 1 and guess > 0.5):
		return node.Leaf(varnames, 1)
	elif guess == 0 or (len(varnames) == 1 and guess <= 0.5):
		return node.Leaf(varnames, 0)

	gain, index = find_split(data, varnames, py, total)

	if gain == 0:
		if guess > 0.5:
			return node.Leaf(varnames, 1)
		else:
			return node.Leaf(varnames, 0)

	left = []
	right = []
	for i in range(total):
		if data[i][index] == 0:
			l = data[i]
			left.append(l)
		else:
			l = data[i]
			right.append(l)

	return node.Split(varnames, index, build_tree(left, varnames), build_tree(right, varnames))
	
# Load data from a file
def read_data(filename):
	f = open(filename, 'r')
	p = re.compile(',')
	data = []
	header = f.readline().strip()
	varnames = p.split(header)
	namehash = {}
	for l in f:
		data.append([int(x) for x in p.split(l.strip())])
	return (data, varnames)

# Saves the model to a file.  Most of the work here is done in the
# node class.  This should work as-is with no changes needed.
def print_model(root, modelfile):
	f = open(modelfile, 'w+')
	root.write(f, 0)

# "varnames" is a list of names, one for each variable
# "train" and "test" are lists of examples.
# Each example is a list of attribute values, where the last element in
# the list is the class value.
def loadAndTrain(trainS,testS,modelS):
	global train
	global varnames
	global test
	global testvarnames
	global root
	(train, varnames) = read_data(trainS)
	(test, testvarnames) = read_data(testS)
	modelfile = modelS

	# build_tree is the main function you'll have to implement, along with
	# any helper functions needed.  It should return the root node of the
	# decision tree.
	root = build_tree(train, varnames)
	print_model(root, modelfile)

def runTest():
	correct = 0
	# The position of the class label is the last element in the list.
	yi = len(test[0]) - 1
	for x in test:
		# Classification is done recursively by the node class.
		# This should work as-is.
		pred = root.classify(x)
		if pred == x[yi]:
			correct += 1
	acc = float(correct)/len(test)
	return acc


# Load train and test data.  Learn model.  Report accuracy.
def main(argv):
	if (len(argv) != 3):
		print 'Usage: id3.py <train> <test> <model>'
		sys.exit(2)
	loadAndTrain(argv[0],argv[1],argv[2])

	acc = runTest()
	print "Accuracy: ",acc

if __name__ == "__main__":
	main(sys.argv[1:])