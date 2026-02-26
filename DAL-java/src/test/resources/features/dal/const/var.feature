Feature: introduce constant

  Scenario: given and use constant
    Given the following constants:
      """
      {
        "v1": 100
      }
      """
    And the following json:
      """
      {
        "a": 100,
        "b": 200
      }
      """
    Then the following verification should pass:
      """
      a= $v1
      """
    And the inspect should:
      """
      a= $v1
      """
    When evaluate by:
      """
      b= $v1
      """
    Then failed with the message:
      """
      Expected to be equal to: java.lang.Integer
      <100>
       ^
      Actual: java.lang.Integer
      <200>
       ^
      """
    And got the following notation:
      """
      b= $v1
         ^
      """

  Scenario: Treat as string if no input constants are provided
    And the following json:
      """
      {
        "a": "$v1",
        "b": 100
      }
      """
    Then the following verification should pass:
      """
      a= $v1
      """
    And the inspect should:
      """
      a= '$v1'
      """
    When evaluate by:
      """
      b= $v1
      """
    Then failed with the message:
      """
      Expected to be equal to: java.lang.String
                                         ^
      <$v1>
      Actual: java.lang.Integer
                        ^
      <100>
      """
    And got the following notation:
      """
      b= $v1
         ^
      """

  Scenario: Treat as string if constant not exist
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
    When evaluate by:
      """
      a= $notExist
      """
    Then failed with the message:
      """
      Expected to be equal to: java.lang.String
                                         ^
      <$notExist>
      Actual: java.lang.Integer
                        ^
      <100>
      """
    And got the following notation:
      """
      a= $notExist
         ^
      """
    And the inspect should:
      """
      a= '$notExist'
      """

  Scenario: Empty constant name
    Given the following constants:
      """
      {
        "": 100
      }
      """
    And the following json:
      """
      {
        "a": 100
      }
      """
    When evaluate by:
      """
      a= $
      """
    Then failed with the message:
      """
      Expected to be equal to: java.lang.String
                                         ^
      <$>
      Actual: java.lang.Integer
                        ^
      <100>
      """
    And got the following notation:
      """
      a= $
         ^
      """
    And the inspect should:
      """
      a= '$'
      """

  Scenario: number constants
    Given the following constants:
      """
      {
        "1": 100
      }
      """
    And the following json:
      """
      {
        "a": 100,
        "b": 200
      }
      """
    Then the following verification should pass:
      """
      a= $1
      """
    And the inspect should:
      """
      a= $1
      """
    When evaluate by:
      """
      b= $1
      """
    Then failed with the message:
      """
      Expected to be equal to: java.lang.Integer
      <100>
       ^
      Actual: java.lang.Integer
      <200>
       ^
      """
    And got the following notation:
      """
      b= $1
         ^
      """

  Scenario: precedence constant > user literal
    Given the following constants:
      """
      {
        "1": 100
      }
      """
    And the following json:
      """
      {
        "a": 100,
        "b": 200
      }
      """
    And defined US dollar money object with the following regex
      """
      ^\$\d+
      """
    Then the following verification should pass:
      """
      a= $1
      """
    And the inspect should:
      """
      a= $1
      """
    When evaluate by:
      """
      a= $2
      """
    Then failed with the message:
      """
      Expected to be equal to: org.testcharm.dal.compiler.CucumberContextBak$USDollar {
                               ^
          amount: java.lang.Integer <2>
      }
      Actual: java.lang.Integer
              ^
      <100>
      """
    And got the following notation:
      """
      a= $2
         ^
      """

  Scenario: check tail comma of constant
    Given the following constants:
      """
      {
        "1": 100
      }
      """
    And the following json:
      """
      {
        "a": "any"
      }
      """
    When evaluate by:
      """
      : {
        a= $1
        ::this: {...}
      }
      """
    Then failed with the message:
      """
      Missing a comma or remove whitespace.
      """
    And got the following notation:
      """
      : {
        a= $1
            ^
        ::this: {...}
        ^
      }
      """
    When evaluate by:
      """
      $1
      ::this
      """
    Then failed with the message:
      """
      Missing a comma or remove whitespace.
      """
    And got the following notation:
      """
      $1
       ^
      ::this
      ^
      """
