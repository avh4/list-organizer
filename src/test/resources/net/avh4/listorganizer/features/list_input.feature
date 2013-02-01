Feature: Reading input files

Scenario: GoodReads CSV file

    When I choose a GoodReads CSV file to sort
    Then I see items with the author and title