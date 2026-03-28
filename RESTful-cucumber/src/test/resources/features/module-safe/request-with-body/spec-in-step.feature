Feature: Request With Spec and Body Steps

  Background:
    Given base url "http://www.a.com"
    Given the following declarations:
      """
      JFactory jfactory = new JFactory();
      """
    And the following class definition:
      """
      public class Request {
        public int intValue;
        public String strValue1, strValue2;

        public int getIntValue() { return intValue; }
        public String getStrValue1() { return strValue1; }
        public String getStrValue2() { return strValue2; }
      }
      """
    And the following class definition:
      """
      public class RequestSpec extends Spec<Request> {}
      """
    And register as follows:
      """
      jfactory.register(RequestSpec.class);
      """
    And use "jfactory" as JFactory

  Rule: no doc type

    Scenario Outline: guess content type from defautSpecRequestContentType(application/json)
      When <method> "RequestSpec" "/index":
        """
        {
          intValue: 1
          strValue1: hello
        }
        """
      Then got request:
        """
        : [{
          method: '<method>'
          path: '/index'
          headers: {
            ['Content-Type']: ['application/json']
          }
          body.json= {
            intValue= 1
            strValue1= hello
            strValue2= /^strValue2.*/
          }
        }]
        """
      Examples:
        | method |
        | POST   |
        | PUT    |
        | PATCH  |

#    Scenario Outline: guess content type from header

  Rule: application/json

    Scenario Outline: specify spec in step
      When <method> "RequestSpec" "/index":
        """ application/json
        {
          intValue: 1
          strValue1: hello
        }
        """
      Then got request:
        """
        : [{
          method: '<method>'
          path: '/index'
          headers: {
            ['Content-Type']: ['application/json']
          }
          body.json= {
            intValue= 1
            strValue1= hello
            strValue2= /^strValue2.*/
          }
        }]
        """
      Examples:
        | method |
        | POST   |
        | PUT    |
        | PATCH  |
