//
//  NamedTraversalViewController.swift
//  Tree Traversal
//
//  Created by Parsa Bagheri on 11/25/17.
//  Copyright © 2017 Parsa Bagheri. All rights reserved.
//

import UIKit
import CoreData
import MessageUI

class NamedTraversalViewController: UIViewController, MFMailComposeViewControllerDelegate, NSFetchedResultsControllerDelegate{
//################################################################################

    override func viewDidLoad() {
        super.viewDidLoad()
        traversalFetchedResultsController = TreeService.shared.TreeTraversals(tree: name)
        traversalFetchedResultsController.delegate = self
        Traversal.text = traversalFetchedResultsController.object(at: IndexPath(row: 0, section:0)).tree_traversal
        navigationController?.interactivePopGestureRecognizer?.isEnabled = true
    }
    private var recipients: [String] = []
    private var subject: String?
    private var recipientTextField: UITextField?
    private var subjectTextField: UITextField?
    var name: TreeName!
    private var traversalFetchedResultsController: NSFetchedResultsController<TreeTraversal>!

    @IBOutlet weak var Traversal: UITextView!
//################################################################################

    @IBAction func sendEmail(_ sender: Any) {
        let sendMailAddressAlert = UIAlertController(title: "Email Information", message: nil, preferredStyle: .alert)
        sendMailAddressAlert.addTextField(configurationHandler: recipientTextfieldHandler)
        sendMailAddressAlert.addTextField(configurationHandler: subjectTextfieldHandler)
        let send = UIAlertAction(title: "Send", style: .default, handler: self.sendAction)
        let cancel = UIAlertAction(title: "Cancel", style: .default, handler: nil)
        sendMailAddressAlert.addAction(cancel)
        sendMailAddressAlert.addAction(send)
        self.present(sendMailAddressAlert, animated: true)
    }
//################################################################################
// setting up emailing services
    
    func recipientTextfieldHandler(textField: UITextField){
        recipientTextField = textField
        recipientTextField?.placeholder = "Recipient's Email Address"
    }
    
    func subjectTextfieldHandler(textFiled: UITextField){
        subjectTextField = textFiled
        subjectTextField?.placeholder = "Subject (Optional)"
    }
    
    func sendAction(alert: UIAlertAction){
        if recipientTextField?.text != nil && recipientTextField?.text != "" {
            recipients.append(recipientTextField!.text!)
            
            if subjectTextField?.text != nil && subjectTextField?.text != "" {
                subject = subjectTextField?.text
            }
            else{
                subjectTextField?.text = ""
            }
            let mailCompeseViewController = configureMailController()
            if MFMailComposeViewController.canSendMail() {
                self.present(mailCompeseViewController, animated: true, completion: nil)
            }
            else{
                showMailError()
            }
        }
        else{
            let alertController = UIAlertController(title: "Please Enter Email Address",
                                                    message: nil,
                                                    preferredStyle: .alert)
            let okAction = UIAlertAction(title: "OK", style: .cancel, handler: nil)
            alertController.addAction(okAction)
            self.present(alertController, animated: true)
        }
    }
    
    func configureMailController() -> MFMailComposeViewController{
        let mailComposerVC = MFMailComposeViewController()
        mailComposerVC.mailComposeDelegate = self
        mailComposerVC.setToRecipients(recipients)
        mailComposerVC.setSubject(subject!)
        mailComposerVC.setMessageBody(Traversal.text, isHTML: false)
        return mailComposerVC
    }
    
    func showMailError() {
        let sendMailErrorAlert = UIAlertController(title: "Send Email Failed", message: "Your device could not send email", preferredStyle: .alert)
        let dismiss = UIAlertAction(title: "Dismiss", style: .default, handler: nil)
        sendMailErrorAlert.addAction(dismiss)
        self.present(sendMailErrorAlert, animated: true, completion: nil)
    }
    
    func mailComposeController(_ controller: MFMailComposeViewController, didFinishWith result: MFMailComposeResult, error: Error?) {
        controller.dismiss(animated: true, completion: nil)
    }
}
