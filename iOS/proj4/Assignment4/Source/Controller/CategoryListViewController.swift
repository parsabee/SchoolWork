//
//  CategoryListViewController.swift
//  Assignment4
//


import UIKit


class CategoryListViewController : UIViewController, UISearchBarDelegate, UITableViewDataSource, UITableViewDelegate {
	// MARK: UISearchBarDeleate
	func searchBarCancelButtonClicked(_ searchBar: UISearchBar) {
		view.endEditing(true)
	}

	// MARK: UITableViewDataSource
	func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
		return CatService.shared.catCategories().count
	}

	func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
		let cell = tableView.dequeueReusableCell(withIdentifier: "CatCell", for: indexPath)

		let catValues = CatService.shared.catCategories()[indexPath.row]
		cell.textLabel?.text = catValues.title
		cell.detailTextLabel?.text = catValues.subtitle

		return cell
	}

	// MARK: View Management
	override func viewWillAppear(_ animated: Bool) {
		super.viewWillAppear(animated)

		observerTokens.append(NotificationCenter.default.addObserver(forName: .UIKeyboardWillShow, object: nil, queue: OperationQueue.main, using: { [unowned self] (notification) in
			self.catListTable.adjustInsets(forWillShowKeyboardNotification: notification)
		}))

		observerTokens.append(NotificationCenter.default.addObserver(forName: .UIKeyboardWillHide, object: nil, queue: OperationQueue.main, using: { [unowned self] (notification) in
			self.catListTable.adjustInsets(forWillHideKeyboardNotification: notification)
		}))
	}

	override func viewDidDisappear(_ animated: Bool) {
		super.viewDidDisappear(animated)

		for observerToken in observerTokens {
			NotificationCenter.default.removeObserver(observerToken)
		}
	}

	override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
		if segue.identifier == "CatImagesSegue" {
			let catImagesViewController = segue.destination as! CatImagesViewController

			let selectedIndexPath = catListTable.indexPathForSelectedRow!
			catImagesViewController.categoryIndex = selectedIndexPath.row
            print("\n\n\(selectedIndexPath.row)\n\n")

			catListTable.deselectRow(at: selectedIndexPath, animated: true)
		}
		else {
			super.prepare(for: segue, sender: sender)
		}
	}

	// MARK: Properties (Private)
	private var observerTokens = Array<Any>()

	// MARK: Properties (IBOutlet)
	@IBOutlet private weak var catListTable: UITableView!
}
