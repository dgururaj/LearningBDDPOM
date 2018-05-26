@excelScenario
Feature: This is a smoke test to verify tests can be executed or not

  Scenario: Simple search test
    Given I have navigated to a website
    When I search for the following "searchTerm" from excel
    Then The correct "searchTerm" appears on the screen from excel