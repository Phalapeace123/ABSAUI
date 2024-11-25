Feature: Add users to the User List Table

  Scenario: Add multiple users with unique details
    Given I navigate to "http://www.way2automation.com/angularjs-protractor/webtables/"
    Then I validate that I am on the "User List Table" page
    And I add the following users:
      | First Name | Last Name | User Name  | Password | Customer     | Role      | Email             | Cell   |
      | FName1     | LName1    | User1      | Pass1    | Company AAA  | Admin     | admin@mail.com    | 082555 |
      | FName2     | LName2    | User2      | Pass2    | Company BBB  | Customer  | customer@mail.com | 083444 |
    Then I ensure the following users are added to the list:
      | User Name  |
      | User1      |
      | User2      |

