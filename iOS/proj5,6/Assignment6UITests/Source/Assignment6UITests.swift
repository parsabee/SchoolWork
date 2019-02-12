//
//  Assignment6UITests.swift
//  Assignment6UITests
//


import XCTest


class Assignment6UITests: XCTestCase {
    override func setUp() {
        super.setUp()
        
        // Put setup code here. This method is called before the invocation of each test method in the class.
        
        // In UI tests it is usually best to stop immediately when a failure occurs.
        continueAfterFailure = false
        // UI tests must launch the application that they test. Doing this in setup will make sure it happens for each test method.
        XCUIApplication().launch()

        // In UI tests it’s important to set the initial state - such as interface orientation - required for your tests before they run. The setUp method is a good place to do this.
    }
    
    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        super.tearDown()
    }
    
    func testExample() {
        let app = XCUIApplication()
        
        XCTAssertEqual(app.tables.count, 1)
        XCTAssertGreaterThan(app.tables.cells.count, 0)
//        let navBar = app.navigationBars["Cat Images"]
//        let existsPredicate = NSPredicate(format: "exists == TRUE")
//        expectation(for: existsPredicate, evaluatedWith: navBar, handler: nil)
//        waitForExpectations(timeout: 5.0, handler: nil)
        let categoriesButton = app.navigationBars["Cat Images"].buttons["Categories"]
        let tablesQuery = app.tables
        tablesQuery/*@START_MENU_TOKEN@*/.staticTexts["Contains 2 images"]/*[[".cells.staticTexts[\"Contains 2 images\"]",".staticTexts[\"Contains 2 images\"]"],[[[-1,1],[-1,0]]],[0]]@END_MENU_TOKEN@*/.tap()
        categoriesButton.tap()
        tablesQuery/*@START_MENU_TOKEN@*/.staticTexts["Burmese"]/*[[".cells.staticTexts[\"Burmese\"]",".staticTexts[\"Burmese\"]"],[[[-1,1],[-1,0]]],[0]]@END_MENU_TOKEN@*/.tap()
        XCTAssertGreaterThan(tablesQuery.staticTexts["Burmese"].accessibilityElementCount(), 0)
    }
}
