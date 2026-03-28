Feature: Basic Request With Body Steps

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

    Scenario Outline: guess content type from defautRequestContentType(dal:application/json)
      When <method> "/index":
        """
        text: hello
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
            text= hello
          }
        }]
        """
      Examples:
        | method |
        | POST   |
        | PUT    |
        | PATCH  |

    Scenario Outline: guess content type from header
      Given header by RESTful api:
        """
        {
          "Content-Type": "text/plain"
        }
        """
      When <method> "/index":
        """
        text: hello
        """
      Then got request:
        """
        : [{
          method: '<method>'
          path: '/index'
          headers: {
            ['Content-Type']: ['text/plain']
          }
          body.string= 'text: hello'
        }]
        """
      Examples:
        | method |
        | POST   |
        | PUT    |
        | PATCH  |

  Rule: dal:application/json

    Scenario Outline: <method> doc type overrides header content type
      Given header by RESTful api:
        """
        {
          "Content-Type": "text/plain"
        }
        """
      When <method> "/index":
        """ dal:application/json
        {...}
        """
      Then got request:
        """
        : [{
          method: '<method>'
          path: '/index'
          headers: {
            ['Content-Type']: ['application/json']
          }
          body.json= {}
        }]
        """
      Examples:
        | method |
        | POST   |
        | PUT    |
        | PATCH  |

    Scenario Outline: only body
      When <method> "/index":
        """ dal:application/json
        text: hello
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
            text= hello
          }
        }]
        """
      Examples:
        | method |
        | POST   |
        | PUT    |
        | PATCH  |

    Scenario Outline: <method> with body and params
      When <method> "/index?中文参数=中文值&second=value2":
        """ dal:application/json
        text: 'Hello world'
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
            text= 'Hello world'
          }
          queryStringParameters: {
           中文参数= [中文值]
           second= [value2]
          }
        }]
        """
      Examples:
        | method |
        | POST   |
        | PUT    |
        | PATCH  |

    Scenario Outline: <method> with body and ::header
      When <method> "/index":
        """ dal:application/json
        {
          text: 'Hello world',
          ::headers: {
            key1: value1
            key2: [value2 value3]
          }
        }
        """
      Then got request:
        """
        : [{
          method: '<method>'
          path: '/index'
          headers: {
            ['Content-Type']: ['application/json']
            key1: ['value1']
            key2: ['value2', 'value3']
          }
          body.json= {
            text= 'Hello world'
          }
        }]
        """
      Examples:
        | method |
        | POST   |
        | PUT    |
        | PATCH  |

    Scenario Outline: <method> with body and header step and ::header
      Given header by RESTful api:
        """
        {
          "key1": "value0"
        }
        """
      When <method> "/index":
        """ dal:application/json
        {
          text: 'Hello world',
          ::headers: {
            key1: value1
            key2: [value2 value3]
          }
        }
        """
      Then got request:
        """
        : [{
          method: '<method>'
          path: '/index'
          headers: {
            ['Content-Type']: ['application/json']
            key1: ['value1']
            key2: ['value2', 'value3']
          }
          body.json= {
            text= 'Hello world'
          }
        }]
        """
      Examples:
        | method |
        | POST   |
        | PUT    |
        | PATCH  |

    Scenario Outline: with body and spec
      When <method> "/index":
        """ dal:application/json
        ::this(RequestSpec): {
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
