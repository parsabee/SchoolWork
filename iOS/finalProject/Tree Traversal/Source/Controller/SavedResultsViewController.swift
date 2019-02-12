//
//  SavedResultsViewController.swift
//  Tree Traversal
//
//  Created by Parsa Bagheri on 11/25/17.
//  Copyright © 2017 Parsa Bagheri. All rights reserved.
//

import UIKit
import CoreData

class SavedResultsViewController: UIViewController, UITableViewDataSource, UITableViewDelegate, NSFetchedResultsControllerDelegate{
//################################################################################
    override func viewDidLoad() {
        super.viewDidLoad()
        // make back swipe available
        navigationController?.interactivePopGestureRecognizer?.isEnabled = true
        // pull saved data from core data
        namesFetchedResultsController = TreeService.shared.TreeNames()
        namesFetchedResultsController.delegate = self
    }
    private var namesFetchedResultsController: NSFetchedResultsController<TreeName>!
    private var typesFetchedResultsController: NSFetchedResultsController<TreeType>!
    private let appDelegate = UIApplication.shared.delegate as! AppDelegate
//################################################################################
//tabel view set up
//################################################################################

    internal func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return namesFetchedResultsController.sections?.first?.numberOfObjects ?? 0
    }
    
    internal func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    internal func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Tree Cell", for: indexPath) as! TreeCell
        let treeName = namesFetchedResultsController.object(at: indexPath)
        typesFetchedResultsController = TreeService.shared.TreeTypes(tree: treeName)
        cell.treeName.text = treeName.tree_name
        cell.treeOrder.text = typesFetchedResultsController.object(at: IndexPath(row: 0, section: 0)).tree_type
        return cell
    }
    
    internal func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == UITableViewCellEditingStyle.delete{
            let context = appDelegate.persistentContainer.viewContext
            let request: NSFetchRequest<TreeName> = TreeName.fetchRequest()
            request.sortDescriptors = [NSSortDescriptor(key: "tree_name", ascending: true)]
            request.returnsObjectsAsFaults = false
            do{
                //deleting from coreData
                let results = try context.fetch(request)
                if (results.count) > 0{
                    context.delete(results[indexPath.row])
                    do{
                        try context.save()
                    }catch{
                        fatalError("Failed to Save State After Delete")
                    }
                }
            }
            catch {
                fatalError("Failed to Delete Data")
            }
        }
    }
    
    func controllerDidChangeContent(_ controller: NSFetchedResultsController<NSFetchRequestResult>) {
        treeTable.reloadData()
    }
    
//################################################################################

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "show_named_traversal"{
            let namedTraversalViewController = segue.destination as! NamedTraversalViewController
            let selected_index_path = treeTable.indexPathForSelectedRow!
            namedTraversalViewController.name = namesFetchedResultsController.object(at: selected_index_path)
            treeTable.deselectRow(at: selected_index_path, animated: true)
        }
    }
//################################################################################

    @IBOutlet weak var treeTable: UITableView!
}
