#!/usr/bin/python
#
# CIS 472/572 - Logistic Regression Template Code
#
# Author: Daniel Lowd <lowd@cs.uoregon.edu>
# Date: 2/9/2018
#
# Please use this code as the template for your solution.
#
import sys
import re
from math import log, exp, sqrt

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
    data.append( (x,y) )
  return (data, varnames)

def parse_data (data):
  features = []
  labels = []
  for i in data:
    features.append(i[0])
    labels.append(i[1])
  return (features, labels)

def sigmoid (z):
  try:
    ret = 1.0 / (1.0 + exp (-z))
  except:
    ret = 0.0
  return ret

def activation (W, b, X):
  a = 0.0
  for i in range(len (X)):
    a += W[i] * X[i]
  return a + b

# Train a logistic regression model using batch gradient descent
def train_lr(data, eta, l2_reg_weight):
  numvars = len(data[0][0])
  w = [0.0] * numvars
  b = 0.0
  features, labels = parse_data (data)
  numfeatures = len (features)
  for it in range (MAX_ITERS):
    w_grad = [0.0] * numvars
    b_grad = 0.0
    for i in range (numfeatures):
      y = labels[i]
      a = activation (w, b, features[i])
      sig = sigmoid ((-y) * a)
      sig *= y
      b_grad -= eta * sig
      for j in range (numvars):
        w_grad [j] -= eta * sig * features[i][j]
    b -= b_grad
    for i in range (numvars):
      w_grad [i] += eta * (l2_reg_weight * w[i])
    for i in range (numvars):
      w [i] -= w_grad [i]

  return (w, b)

# Predict the probability of the positive label (y=+1) given the
# attributes, x.
def predict_lr(model, x):
  (w,b) = model
  return activation (w, b, x)


# Load train and test data.   Learn model.  Report accuracy.
def main(argv):
  if (len(argv) != 5):
    print('Usage: lr.py <train> <test> <eta> <lambda> <model>')
    sys.exit(2)
  (train, varnames) = read_data(argv[0])
  (test, testvarnames) = read_data(argv[1])
  eta = float(argv[2])
  lam = float(argv[3])
  modelfile = argv[4]

  # Train model
  (w,b) = train_lr(train, eta, lam)

  # Write model file
  f = open(modelfile, "w+")
  f.write('%f\n' % b)
  for i in range(len(w)):
    f.write('%s %f\n' % (varnames[i], w[i]))

  # Make predictions, compute accuracy
  correct = 0
  for (x,y) in test:
    prob = predict_lr( (w,b), x )
    print(prob)
    if (prob - 0.5) * y > 0:
      correct += 1
  acc = float(correct)/len(test)
  print("Accuracy: ",acc)

if __name__ == "__main__":
  main(sys.argv[1:])
