//
//  TreeCell.swift
//  Tree Traversal
//
//  Created by Parsa Bagheri on 11/25/17.
//  Copyright © 2017 Parsa Bagheri. All rights reserved.
//

import UIKit

class TreeCell: UITableViewCell {
    @IBOutlet weak var treeOrder: UILabel!
    @IBOutlet weak var treeName: UILabel!
    static let defaultReuseIdentifier = "Tree Cell"
}
