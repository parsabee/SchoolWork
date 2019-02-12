//
//  CatImageCell.swift
//  Assignment4
//


import UIKit


class CatImageCell : UICollectionViewCell {
	// MARK: Configuration
	func update(forImageName imageName: String) {
		catImageView.image = UIImage(named: imageName)
	}

	// MARK: Properties (IBOutlet)
	@IBOutlet private weak var catImageView: UIImageView!
}
