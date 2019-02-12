//
//  DisplayViewController.swift
//  Tree Traversal
//
//  Created by Parsa Bagheri on 11/16/17.
//  Copyright © 2017 Parsa Bagheri. All rights reserved.
//

import UIKit
import CoreData

class DisplayViewController: UIViewController {
//################################################################################

    override func viewDidLoad() {
        super.viewDidLoad()
        traversal_text_field.text = stringTraversal
        navigationController?.interactivePopGestureRecognizer?.isEnabled = true
    }

    var stringTraversal : String?
    private var nameTextField: UITextField?
    let appDelegate = UIApplication.shared.delegate as! AppDelegate
//################################################################################

    @IBOutlet weak var traversal_text_field: UITextView!

    @IBAction func save(_ sender: Any) {
        //alert box to take input for saving to core data
        let saveAlertController = UIAlertController(title: "Save Traversal",
                                                    message: nil,
                                                    preferredStyle: .alert)
        saveAlertController.addTextField(configurationHandler: nameTextField)
        let saveAction = UIAlertAction(title: "Save", style: .default, handler: self.saveHandler)
        let cancelAction = UIAlertAction(title: "Cancel", style: .cancel, handler: nil)
        saveAlertController.addAction(saveAction)
        saveAlertController.addAction(cancelAction)
        self.present(saveAlertController, animated: true)
    }
//################################################################################
//functions and textfields used in the AlertBox
    
    func nameTextField(textField: UITextField){
        nameTextField = textField
        nameTextField?.placeholder = "Name"
    }
    
    func saveHandler(alert: UIAlertAction!){
        //save handler to save to core data
        if (nameTextField?.text != nil && nameTextField?.text != ""){
            let context = appDelegate.persistentContainer.viewContext
            context.perform {
                let traversal_type = TreeType(context: context)
                let tree_name = TreeName(context: context)
                let tree_traversal = TreeTraversal(context: context)
                traversal_type.tree_type = self.title!
                tree_name.tree_name = self.nameTextField!.text
                tree_traversal.tree_traversal = self.stringTraversal
                tree_name.name_tree = traversal_type
                tree_name.name_traversal = tree_traversal
                do{
                    try context.save()
                }catch{
                    fatalError("Failed to save context after inserting data")
                }
                print("\nsaved\n")
            }
        }
        else{
            //alert if name not entered
            let alertController = UIAlertController(title: "Please Enter Name",
                                                        message: nil,
                                                        preferredStyle: .alert)
            let okAction = UIAlertAction(title: "OK", style: .cancel, handler: nil)
            alertController.addAction(okAction)
            self.present(alertController, animated: true)
        }
    }
}
