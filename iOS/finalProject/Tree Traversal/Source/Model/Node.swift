//
//  Node.swift
//  Tree Traversal
//
//  Created by Parsa Bagheri on 11/21/17.
//  Copyright © 2017 Parsa Bagheri. All rights reserved.
//

class Node{
    //Initiates the nodes that will be added to the tree
    //Class global variables
    
    private var data: Float64?;
    private var left_child: Node?;
    private var right_child: Node?;
    private var parent: Node?;
    
//################################################################################
    //Class constructor
    
    init(data: Float64?){
        self.data = data;
        left_child = nil;
        right_child = nil;
        parent = nil;
    }
    
//################################################################################
    //Class setters
    
    func setData(data: Float64?){
        self.data = data;
    }
    func setLeftChild(left_child: Node?){
        self.left_child = left_child;
    }
    func setRightChild(right_child: Node?){
        self.right_child = right_child;
    }
    func setParent(parent: Node?){
        self.parent = parent;
    }
    
//################################################################################
    //Class getters
    
    func getData() -> Float64?{
        return data;
    }
    func getLeftChild() -> Node?{
        return left_child;
    }
    func getRightChild() -> Node?{
        return right_child;
    }
    func getParent() -> Node?{
        return parent;
    }
}
//################################################################################

