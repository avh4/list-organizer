Feature: Quickly sort items into groups

Scenario: normal list

    Given a list of items
    And a set of groups
    When I sort all the items using the keyboard
    Then I can see the list of items for each groups
