//
//  BSTViewController.swift
//  Tree Traversal
//
//  Created by Parsa Bagheri on 11/16/17.
//  Copyright © 2017 Parsa Bagheri. All rights reserved.
//

import UIKit

class BSTViewController: UIViewController {
//################################################################################
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //dismiss keyboard when tapped on the screen
        self.hideKeyboardWhenTappedAround()
        navigationController?.interactivePopGestureRecognizer?.isEnabled = true
        
        //setting up swipe motions
        let leftSwipe = UISwipeGestureRecognizer(target: self, action: #selector(swipeAction(swipe:)))
        leftSwipe.direction = UISwipeGestureRecognizerDirection.left
        self.view.addGestureRecognizer(leftSwipe)
        
        let upSwipe = UISwipeGestureRecognizer(target: self, action: #selector(swipeAction(swipe:)))
        upSwipe.direction = UISwipeGestureRecognizerDirection.up
        self.view.addGestureRecognizer(upSwipe)
    }
    
    private var selected_traversal: String = "in-order" //default traversal is in-order
    private var binary_search_tree: BST = BST() //initialize the tree
//################################################################################
    //setting up IBOutlets and IBActions
    
    @IBOutlet weak var userInput: UITextField!

    @IBAction func displayResults(_ sender: Any) {
        //display the selected traversal of the tree
        performSegue(withIdentifier: "displayBST", sender: self)
    }

    @IBAction func add(_ sender: Any) {
        //add to the tree
        if userInput.text != nil{
            if let user_input = Float64(userInput.text!){
                binary_search_tree.insert(data: user_input)
            }
            else{
                alertPusher()//push an alert if invalid input is entered
            }
            userInput.text = nil
        }
    }

    @IBAction func remove(_ sender: Any) {
        //remove from the tree
        if userInput.text != nil{
            if let user_input = Float64(userInput.text!){
                binary_search_tree.delete(data: user_input)
            }
            else{
                alertPusher()//push an alert if invalid input is entered
            }
            userInput.text = nil
        }
    }

    @IBAction func reset(_ sender: Any) {
        //reset the tree
        binary_search_tree = BST()
        if userInput.text != nil{
            userInput.text = nil
        }
    }
//################################################################################

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "displayBST" {
            let displayViewController = segue.destination as! DisplayViewController
            displayViewController.title = selected_traversal
            displayViewController.stringTraversal = binary_search_tree.traverse(type: selected_traversal, top: binary_search_tree.getRoot())
        }
        else if segue.identifier == "options" {
            let navController = segue.destination as! UINavigationController
            let optionsController = navController.topViewController as! OptionsViewController
            optionsController.delegate = self
        }
        else {
            super.prepare(for: segue, sender: sender)
        }
    }
    
    @objc private func swipeAction(swipe: UISwipeGestureRecognizer){
        if swipe.direction == UISwipeGestureRecognizerDirection.up{
            performSegue(withIdentifier: "options", sender: self)
        }
        if swipe.direction == UISwipeGestureRecognizerDirection.left{
            performSegue(withIdentifier: "displayBST", sender: self)
        }
    }
    
    private func alertPusher(){
        //send an alert if user inputs invalid entery
        let alert = UIAlertController(title: "Invalid Input", message: "Please enter numerals", preferredStyle: .alert)
        let okAction = UIAlertAction(title: "OK", style: .cancel, handler: nil)
        alert.addAction(okAction)
        self.present(alert, animated: true)
    }
}

//################################################################################
extension UIViewController {
    func hideKeyboardWhenTappedAround() {
        //use this function to dismiss keyboard as the users taps anywhere on the screeen
        let tap: UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(UIViewController.dismissKeyboard))
        tap.cancelsTouchesInView = false
        view.addGestureRecognizer(tap)
    }
    
    @objc func dismissKeyboard() {
        view.endEditing(true)
    }
}
extension BSTViewController: OptionsDelegate{
    func selectedTraversal(traversal: String) {
            selected_traversal = traversal
    }
}
