//
//  ViewController.swift
//  Tree Traversal
//
//  Created by Parsa Bagheri on 11/15/17.
//  Copyright © 2017 PB. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
//################################################################################

    override func viewDidLoad() {
        super.viewDidLoad()
        
        //setting up swipe motions
        let downSwipe = UISwipeGestureRecognizer(target: self, action: #selector(swipeAction(swipe:)))
        downSwipe.direction = UISwipeGestureRecognizerDirection.down
        self.view.addGestureRecognizer(downSwipe)
        
        let leftSwipe = UISwipeGestureRecognizer(target: self, action: #selector(swipeAction(swipe:)))
        leftSwipe.direction = UISwipeGestureRecognizerDirection.left
        self.view.addGestureRecognizer(leftSwipe)
        
        let upSwipe = UISwipeGestureRecognizer(target: self, action: #selector(swipeAction(swipe:)))
        upSwipe.direction = UISwipeGestureRecognizerDirection.up
        self.view.addGestureRecognizer(upSwipe)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
//################################################################################

    @objc private func swipeAction(swipe: UISwipeGestureRecognizer){
        //handles swipes
        
        if swipe.direction == UISwipeGestureRecognizerDirection.down{
            aboutDone(self)
        }
        if swipe.direction == UISwipeGestureRecognizerDirection.up{
            performSegue(withIdentifier: "goToAbout", sender: self)
        }
        if swipe.direction == UISwipeGestureRecognizerDirection.left{
            performSegue(withIdentifier: "goToTableView", sender: self)
        }
    }
//################################################################################

    @IBAction func aboutDone(_ sender: Any) {
        //dismisses about modalView
        self.dismiss(animated: true, completion: nil)
    }
}

