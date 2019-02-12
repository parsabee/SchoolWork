//
//  BST.swift
//  Tree Traversal
//
//  Created by Parsa Bagheri on 11/21/17.
//  Copyright © 2017 Parsa Bagheri. All rights reserved.
//

class BST{
    private var root: Node?;

//################################################################################

    init(){
        root = Node(data: nil)
    }
    
    func getRoot() -> Node?{
        return root;
    }
    
//################################################################################
   // Insert Method
    
    private func insertNode( current cur: Node?, to_be_added n: Node) -> Node?{
        var current = cur
        if n.getData() == nil{
            return current
        }
        if current?.getData() == nil{
            current = n;
        }
        else if (current?.getData())! < (n.getData())!{
            current?.setRightChild(right_child: (insertNode(current: current?.getRightChild(), to_be_added: n))!)
            current?.getRightChild()?.setParent(parent: current)
        }
        else if (current?.getData())! > (n.getData())!{
            current?.setLeftChild(left_child: (insertNode(current: current?.getLeftChild(), to_be_added: n))!)
            current?.getLeftChild()?.setParent(parent: current)
        }
        return current
    }
    
    func insert(data: Float64?){
        let node: Node = Node(data: data)
        root = insertNode(current: root, to_be_added: node)
    }

//################################################################################
    //Find Method
    
    private func find(data: Float64?) -> Node?{
        var done: Bool = false
        var temp: Node? = root
        
        while done != true{
            if temp == nil{
                return nil;
            }
            if (temp?.getData())! == data!{
                done = true
            }
            else if (temp?.getData())! > data!{
                temp = temp?.getLeftChild()
            }
            else if (temp?.getData())! < data!{
                temp = temp?.getRightChild()
            }
        }
        return temp
    }
    
//################################################################################
    //Delete Method
    
    private func findDeleteNode(current: Node?, data: Float64) -> Node?{
        if current == nil{
            return nil
        }
        else if current?.getData() == data{
            return deleteCurrentNode(current: current!)
        }
        else if (current?.getData())! > data{
            current?.setLeftChild(left_child: findDeleteNode(current: current?.getLeftChild(), data: data))
            if current?.getLeftChild() != nil{
                current?.getLeftChild()?.setParent(parent: current!)
            }
        }
        else if (current?.getData())! < data{
            current?.setRightChild(right_child: findDeleteNode(current: current?.getRightChild(), data: data))
            if current?.getRightChild() != nil{
                current?.getRightChild()?.setParent(parent: current!)
            }
        }
        return current
    }
    
    private func identifyCase(current: Node?) -> Float64{
        if (current?.getLeftChild() == nil && current?.getRightChild() == nil){
            return 1
        }
        else if(current?.getLeftChild() != nil && current?.getRightChild() != nil){
            return 3
        }
        return 2
    }
    
    private func rightSubtreeLowest(current: Node) -> Float64{
        var cur: Node? = current
        var data: Float64 = (cur?.getData())!
        while cur?.getLeftChild() != nil{
            cur = cur?.getLeftChild()
            data = (cur?.getData())!
        }
        return data
    }
    
    private func deleteCase2(currnet: Node?) -> Node?{
        if currnet?.getLeftChild() == nil {
            return currnet?.getRightChild()
        }
        return currnet?.getLeftChild()
    }
    
    private func deleteCase3(current: Node) -> Node?{
        let replace_data: Float64 = rightSubtreeLowest(current: current.getRightChild()!)
        current.setData(data: replace_data)
        current.setRightChild(right_child: findDeleteNode(current: current.getRightChild(), data: replace_data))
        if current.getRightChild() != nil{
            current.getRightChild()?.setParent(parent: current)
        }
        return current
    }
    
    private func deleteCurrentNode(current: Node) -> Node?{
        let CASE: Float64 = identifyCase(current: current)
        if CASE == 1{
            return nil;
        }
        else if CASE == 2{
            return deleteCase2(currnet: current)
        }
        else if CASE == 3{
            return deleteCase3(current: current)
        }
        return nil
    }
    
    func delete (data: Float64?){
        if data != nil{
            root = findDeleteNode(current: root, data: data!)
        }
    }

//################################################################################
    // Traverse Method
    
    private func inOrder(top t: Node?) -> String{
        var traversal: String = ""
        if t?.getData() != nil{
            var stack: [Node?] = []
            var node: Node? = t
            while node != nil {
                stack.append(node)
                node = node?.getLeftChild()
            }
            while stack.count > 0 {
                node = stack.popLast() as? Node
                traversal = traversal + String(describing: (node?.getData())!) + "  "
                if node?.getRightChild() != nil{
                    node = node?.getRightChild()
                    while(node != nil){
                        stack.append(node)
                        node = node?.getLeftChild()
                    }
                }
            }
        }
        return traversal
    }
    
    private func preOrder(top t: Node?) -> String {
        var traversal: String = ""
        if t?.getData() != nil {
            var stack: [Node?] = []
            stack.append(root)
            
            while (stack.count > 0){
                let node: Node = (stack.last as? Node)!
                traversal = traversal + String(describing: (node.getData())!) + "  "
                stack.removeLast()
                if node.getRightChild() != nil {
                    stack.append(node.getRightChild())
                }
                if node.getLeftChild() != nil {
                    stack.append(node.getLeftChild())
                }
            }
        }
        return traversal
    }
    
    private func postOrder(top t: Node?) -> String {
        var stack1: [Node?] = []
        var stack2: [Node?] = []
        var traversal: String = ""
        if t?.getData() != nil {
            stack1.append(t)
            while stack1.count > 0 {
                let temp: Node = (stack1.popLast() as? Node)!
                stack2.append(temp)
                if temp.getLeftChild() != nil {
                    stack1.append(temp.getLeftChild())
                }
                if temp.getRightChild() != nil {
                    stack1.append(temp.getRightChild())
                }
            }
            while stack2.count > 0 {
                let temp: Node = (stack2.popLast() as? Node)!
                traversal = traversal + String(describing: (temp.getData())!) + "  "
            }
        }
        return traversal
    }
    
    func traverse(type: String, top t: Node?) -> String? {
        if type == "post-order"{
            return postOrder(top: t)
        }
        else if type == "pre-order"{
            return preOrder(top: t)
        }
        else{
            return inOrder(top: t)
        }
    }
}
//################################################################################
