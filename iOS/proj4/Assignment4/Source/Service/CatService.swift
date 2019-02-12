//
//  CatService.swift
//  Assignment4
//


import Foundation


class CatService {
	// MARK: Service
	func catCategories() -> Array<(title: String, subtitle: String)> {
		return catData.map({ ($0["CategoryTitle"] as! String, "Contains \(($0["ImageNames"] as! Array<String>).count) images") })

//		// The code below is equivalent to the above map statement
//		var categories =  Array<(String, String)>()
//		for catValues in catData {
//			let title = catValues["CategoryTitle"] as! String
//
//			let imageNames = catValues["ImageNames"] as! Array<String>
//			let subtitle = "Contains \(imageNames.count) images"
//
//			categories.append((title, subtitle))
//		}
//
//		return categories
	}

	func imageNamesForCategory(atIndex index: NSInteger) -> Array<String> {
		return catData[index]["ImageNames"] as! Array<String>
	}

	// MARK: Initialization
	private init() {
		let catDataPath = Bundle.main.path(forResource: "CatData", ofType: "plist")!
		catData = NSArray(contentsOfFile: catDataPath) as! Array<Dictionary<String, AnyObject>>
	}

	// MARK: Properties (Private)
	private let catData: Array<Dictionary<String, AnyObject>>

	// MARK: Properties (Static)
	static let shared = CatService()
}
