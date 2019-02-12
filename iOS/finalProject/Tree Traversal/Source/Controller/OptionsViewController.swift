//
//  OptionsViewController.swift
//  Tree Traversal
//
//  Created by Parsa Bagheri on 11/16/17.
//  Copyright © 2017 Parsa Bagheri. All rights reserved.
//

import UIKit

class OptionsViewController: UIViewController ,UIPickerViewDataSource, UIPickerViewDelegate{
//################################################################################

    override func viewDidLoad() {
        super.viewDidLoad()
        //setting up swipe motions
        let swipeDown = UISwipeGestureRecognizer(target: self, action: #selector(swipeAction(swipe:)))
        swipeDown.direction = UISwipeGestureRecognizerDirection.down
        self.view.addGestureRecognizer(swipeDown)
    }
    
    let traversals = ["in-order", "pre-order", "post-order"]//traversals to be displayed in the picker view
    weak var delegate: OptionsDelegate!
    var selected_traversal: String! = "in-order"//default traversal
//################################################################################
//Setting up IBOutlets and IBActions
    @IBAction private func optionsDone(_ sender: AnyObject) {
        delegate?.selectedTraversal(traversal: selected_traversal)
        dismiss(animated: true, completion: nil)
    }
    @IBOutlet weak var traversalPickerView: UIPickerView!
//################################################################################
//Setting up pickerView
    internal func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    internal func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return traversals[row]
    }
    
    internal func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return traversals.count
    }
    
    internal func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        selected_traversal = traversals[row]
    }
//################################################################################
//handle swipes
    @objc private func swipeAction(swipe: UISwipeGestureRecognizer){
        if swipe.direction == UISwipeGestureRecognizerDirection.down{
            optionsDone(self)
        }
    }
}
