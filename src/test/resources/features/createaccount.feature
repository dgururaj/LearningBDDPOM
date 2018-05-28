@dynamics
Feature: This is a simple feature to validate New Account Creation and Quick Contact Creation in Dynamics CRM

  Scenario: TC_01_Create Account
    Given User Logs to Dynamics 365
    And Navigates to Sales Module
    And Selects Account Under Customers
    When An Account is created by the user
    Then Verify valid account information is captured in webpage


  Scenario: Create Lead with Existing Account and Contact Information and covert to opportunity
    Given User creates a Quick contact
    And Creates a Lead with mandatory information
    When Lead is associated to Existing Account and Contact
#    And Qualify Dialog is clicked
#    Then Verify an opportunity is created with valid details



