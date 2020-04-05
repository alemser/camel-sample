Feature: Process products to the right frontend
  Process messages and direct them to the right place

  Scenario: Process lounge products
    Given a message is sent to the products queue
    When the message is processed
    Then the product is available in lounge