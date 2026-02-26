Feature: introduce constant

  Scenario: given and use const
    Given the following constants:
      """
      {
        "v1": 100
      }
      """
    And the following json:
      """
      {
        "a": 100
      }
      """
    Then the following verification should pass:
      """
      a= $v1
      """

#TODO not exist
#TODO not exist
#TODO precedence of user literal and const
#TODO valid char / single $
