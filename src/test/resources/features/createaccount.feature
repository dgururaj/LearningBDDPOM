@dynamics
Feature: This is a simple feature to validate Account Creation in Dynamics CRM

  Scenario: Create Account
    Given User Logs to Dynamics 365
    And Navigates to Sales Module
    And Selects Account Under Customers
   When An Account is created by the user
  #   Then Account Record must be listed in MyAccounts



