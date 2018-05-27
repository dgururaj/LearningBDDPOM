@dynamics
Feature: This is a simple feature to validate New Account Creation and Quick Contact Creation in Dynamics CRM

  Scenario: TC_01_Create Account
    Given User Logs to Dynamics 365
    And Navigates to Sales Module
    And Selects Account Under Customers
    When An Account is created by the user
    Then Verify valid account information is captured in webpage


  Scenario: TC_02_Create_Quick_Contact
    Given User creates a Quick contact
#    And Navigates to Sales Module
#    And Selects Account Under Customers
#    When An Account is created by the user
#    Then Verify valid account information is captured in webpage
#
