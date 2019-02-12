//
//  Assignment6Tests.swift
//  Assignment6Tests
//

import UIKit
import XCTest
@testable import Assignment6

class Assignment6Tests: XCTestCase {
    
    override func setUp() {
        super.setUp()
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }
    
    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        super.tearDown()
    }
    
    func testExample() {
        // This is an example of a functional test case.
        XCTAssert(true, "Pass")
    }
    
    func testPerformanceExample() {
        // This is an example of a performance test case.
        self.measure() {
            // Put the code you want to measure the time of here.
        }
    }
    func testCatServiceCategories() {
        let catService = CatService.shared
        let fetchedResultsController = catService.catCategories()
        let count = 0
        
        XCTAssertGreaterThan((fetchedResultsController.sections?.count)!, count)
        XCTAssertGreaterThan((fetchedResultsController.sections?.first?.numberOfObjects)!, count)
    }
    
    func testCatServiceImage(){
        let catImageService = CatService.shared
        let count = 0
        for catPic in (catImageService.catCategories().fetchedObjects)!{
            let fetchedImageResultsController = catImageService.images(for: catPic)
            XCTAssertGreaterThan((fetchedImageResultsController.sections?.count)!, count)
            XCTAssertGreaterThan((fetchedImageResultsController.sections?.first?.numberOfObjects)!, count)
        }
    }
}
