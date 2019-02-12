#include <stdio.h>
// #define NULL 0;
typedef struct ELE *tree_ptr;	


struct ELE{
	long val;	
	tree_ptr left;
	tree_ptr right;
};
tree_ptr trace(tree_ptr tp){				
	tree_ptr temp= tp;			//stores the tree pointer tp to variable temp
	while(temp->right != NULL){	// it traverses the tree down the right child as long as it has a right child, and updates the temp to the rightmost child
		temp = temp->right;
	}
	return temp;				// returns the right child on the lowest level
}


int main()
{
	struct ELE tree1 = {0, NULL, NULL};			//constructing nodes with no childeren
	struct ELE tree2 = {1, NULL, NULL};
	struct ELE tree3 = {2, NULL, NULL};
	struct ELE tree4 = {3, NULL, NULL};
	struct ELE tree21 = {4, &tree2, &tree1};	//node with two children tree2 and tree1
	struct ELE tree43 = {5, &tree4, &tree3};	//node with two children tree3 and tree4
	struct ELE root = {6, &tree43, &tree21};	// root of the tree that has tree43 and tree21 as its childeren
	tree_ptr result = trace(&root);				//finds the rightmost child on the lowest level of the tree and stores it in result
	printf("%ld", (*result).val);				//prints the value of the result
}