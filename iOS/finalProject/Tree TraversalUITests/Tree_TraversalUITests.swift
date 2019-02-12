//
//  Tree_TraversalUITests.swift
//  Tree TraversalUITests
//
//  Created by Parsa Bagheri on 11/16/17.
//  Copyright © 2017 Parsa Bagheri. All rights reserved.
//

import XCTest

class Tree_TraversalUITests: XCTestCase {
        
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
        app.buttons["Binary Search Tree"].tap()
        
        let textField = app.otherElements.containing(.navigationBar, identifier:"Binary Search Tree").children(matching: .other).element.children(matching: .other).element.children(matching: .other).element.children(matching: .textField).element
        textField.tap()
        textField.typeText("6")
        app.buttons["Add Node"].tap()
        app.buttons["Display Result"].tap()
        
        app.textViews["traversalTextField"].tap()
        
        XCTAssertGreaterThan(app.textViews["traversalTextField"].accessibilityElementCount(), 0)
        
    }
}
