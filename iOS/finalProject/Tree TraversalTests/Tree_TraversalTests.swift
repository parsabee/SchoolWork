//
//  Tree_TraversalTests.swift
//  Tree TraversalTests
//
//  Created by Parsa Bagheri on 11/16/17.
//  Copyright © 2017 Parsa Bagheri. All rights reserved.
//

import XCTest
@testable import Tree_Traversal

class Tree_TraversalTests: XCTestCase {
    
    func testAdd(){
        let bst = BST()
        bst.insert(data: 5)
        bst.insert(data: 6)
        bst.insert(data: 4)
        bst.insert(data: 7)
        bst.insert(data: 3)
        bst.insert(data: 8)
        bst.insert(data: 2)
        bst.insert(data: 1)
        bst.insert(data: 1)
        bst.insert(data: 1.5)
        
        let inorder:String = "1.0  1.5  2.0  3.0  4.0  5.0  6.0  7.0  8.0  "
        let preorder:String = "5.0  4.0  3.0  2.0  1.0  1.5  6.0  7.0  8.0  "
        let postorder:String = "1.5  1.0  2.0  3.0  4.0  8.0  7.0  6.0  5.0  "
        
        XCTAssertEqual(bst.traverse(type: "post-order", top: bst.getRoot()), postorder)
        XCTAssertEqual(bst.traverse(type: "pre-order", top: bst.getRoot()), preorder)
        XCTAssertEqual(bst.traverse(type: "in-order", top: bst.getRoot()), inorder)
    }
    func testDelete(){
        let bst = BST()
        bst.insert(data: 5)
        bst.insert(data: 6)
        bst.insert(data: 4)
        bst.insert(data: 7)
        bst.insert(data: 3)
        bst.insert(data: 8)
        bst.insert(data: 2)
        bst.insert(data: 1)
        bst.insert(data: 1)
        bst.insert(data: 1.5)
        bst.delete(data: 5)
        bst.delete(data: 6)
        bst.delete(data: 4)
        bst.delete(data: 7)
        bst.delete(data: 1.5)
        bst.delete(data: 1)
        
        let inorder:String = "2.0  3.0  8.0  "
        let preorder:String = "8.0  3.0  2.0  "
        let postorder:String = "2.0  3.0  8.0  "
        
        XCTAssertEqual(bst.traverse(type: "post-order", top: bst.getRoot()), postorder)
        XCTAssertEqual(bst.traverse(type: "pre-order", top: bst.getRoot()), preorder)
        XCTAssertEqual(bst.traverse(type: "in-order", top: bst.getRoot()), inorder)
    }
    func testSave(){
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        let context = appDelegate.persistentContainer.viewContext
        
        //saving
        context.perform {
            let traversal_type = TreeType(context: context)
            let tree_name = TreeName(context: context)
            let tree_traversal = TreeTraversal(context: context)
            traversal_type.tree_type = "in-order"
            tree_name.tree_name = "test"
            tree_traversal.tree_traversal = "2.0  3.0  8.0  "
            tree_name.name_tree = traversal_type
            tree_name.name_traversal = tree_traversal
            do{
                try context.save()
            }catch{
                fatalError("Failed to save context after inserting data")
            }
        }
        
        let treeService = TreeService.shared
        let fetchedTreeName = treeService.TreeNames()
        let fetcehdTreeType = treeService.TreeTypes(tree: fetchedTreeName.object(at: IndexPath(row: 0, section: 0)))
        let fetchedTreeTraversal = treeService.TreeTypes(tree: fetchedTreeName.object(at: IndexPath(row: 0, section: 0)))
        
        let count = 0
        
        XCTAssertGreaterThan((fetchedTreeName.sections?.count)!, count)
        XCTAssertGreaterThan((fetchedTreeName.sections?.first?.numberOfObjects)!, count)
        XCTAssertGreaterThan((fetchedTreeTraversal.sections?.count)!, count)
        XCTAssertGreaterThan((fetchedTreeTraversal.sections?.first?.numberOfObjects)!, count)
        XCTAssertGreaterThan((fetcehdTreeType.sections?.count)!, count)
        XCTAssertGreaterThan((fetcehdTreeType.sections?.first?.numberOfObjects)!, count)
    }
}
