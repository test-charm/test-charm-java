Feature: meta annotation

  Scenario: meta property without parameter
    Given the following java class:
    """
    public class Bean {
      @org.testcharm.dal.type.MetaProperty
      public String meta() {
        return "hello";
      }
    }
    """
    Then the following verification for the instance of java class "Bean" should pass:
    """
    ::meta= hello
    """

# TODO inherit
# TODO with args
# TODO duplicated
# TODO priority