//
//  TreeService.swift
//  Tree Traversal
//
//  Created by Parsa Bagheri on 12/6/17.
//  Copyright © 2017 Parsa Bagheri. All rights reserved.
//

import CoreData
import UIKit

class TreeService{
    
    static let shared = TreeService()
    private let persistentContainer: NSPersistentContainer
    private let appDelegate = UIApplication.shared.delegate as! AppDelegate
    
    private init(){
        persistentContainer = appDelegate.persistentContainer
    }
    func TreeNames()-> NSFetchedResultsController<TreeName> {
        let fetchRequest: NSFetchRequest<TreeName> = TreeName.fetchRequest()
        fetchRequest.sortDescriptors = [NSSortDescriptor(key: "tree_name", ascending: true)]
        
        return createFetchedResultsController(for: fetchRequest)
    }
    func TreeTraversals(tree: TreeName)-> NSFetchedResultsController<TreeTraversal> {
        let fetchRequest: NSFetchRequest<TreeTraversal> = TreeTraversal.fetchRequest()
        fetchRequest.predicate = NSPredicate(format: "traversal_name == %@", tree)
        fetchRequest.sortDescriptors = [NSSortDescriptor(key: "traversal_name", ascending: true)]
        
        return createFetchedResultsController(for: fetchRequest)
    }
    func TreeTypes(tree: TreeName)-> NSFetchedResultsController<TreeType> {
        let fetchRequest: NSFetchRequest<TreeType> = TreeType.fetchRequest()
        fetchRequest.predicate = NSPredicate(format: "tree_name == %@", tree)
        fetchRequest.sortDescriptors = [NSSortDescriptor(key: "tree_type", ascending: true)]
        
        return createFetchedResultsController(for: fetchRequest)
    }
    
    private func createFetchedResultsController<T>(for fetchRequest: NSFetchRequest<T>) -> NSFetchedResultsController<T> {
        let fetchedResultsController = NSFetchedResultsController(fetchRequest: fetchRequest, managedObjectContext: persistentContainer.viewContext, sectionNameKeyPath: nil, cacheName: nil)
        do {
            try fetchedResultsController.performFetch()
        }
        catch let error {
            fatalError("Could not perform fetch for fetched results controller: \(error)")
        }
        
        return fetchedResultsController
    }
}
