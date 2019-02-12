//
//  CatImagesViewController.swift
//  Assignment4
//


import UIKit


class CatImagesViewController : UIViewController, UICollectionViewDataSource, UICollectionViewDelegate {
	// MARK: UICollectionViewDataSource
	func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
		return CatService.shared.imageNamesForCategory(atIndex: categoryIndex).count
	}

	func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
		let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "CatImageCell", for: indexPath) as! CatImageCell

		let imageName = CatService.shared.imageNamesForCategory(atIndex: categoryIndex)[indexPath.item]
		cell.update(forImageName: imageName)

		return cell
	}

	// MARK: Properties
	var categoryIndex: Int! = nil
}
