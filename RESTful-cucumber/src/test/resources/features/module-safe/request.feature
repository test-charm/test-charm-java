@jfactory
Feature: RESTful new api steps

  Background:
    Given base url "http://www.a.com"

  Rule: GET/DELETE

    Scenario Outline: <method> with no params
      When <method> "/index"
      Then got request:
      """
      : [{
        method= '<method>'
        path= '/index'
        queryStringParameters= null
      }]
      """
      Examples:
        | method |
        | GET    |
        | DELETE |

    Scenario Outline: <method> with params in url
      When <method> "/index?中文参数=中文值&second=value2"
      Then got request:
      """
      : [{
        method= '<method>'
        path= '/index'
        queryStringParameters= {
          中文参数= [中文值]
          second= [value2]
        }
      }]
      """
      Examples:
        | method |
        | GET    |
        | DELETE |

    Scenario Outline: <method> with header step
      Given header by RESTful api:
        """
        {
          "key1": "value1",
          "key2": ["value2", "value3"]
        }
        """
      When <method> "/index"
      Then got request:
        """
        : [{
          method= '<method>'
          path= '/index'
          headers: {
            key1= ['value1']
            key2= ['value2', 'value3']
          }
        }]
        """
      Examples:
        | method |
        | GET    |
        | DELETE |

  Rule: GET/DELETE with docstring

    Scenario Outline: <method> with single value param
      When <method> "/index":
        """ application/json
        {
          "中文参数": "中文值",
          "second": "value1"
        }
        """
      Then got request:
        """
        : [{
          method= '<method>'
          path= '/index'
          queryStringParameters= {
            中文参数= [中文值]
            second= [value1]
          }
        }]
        """
      Examples:
        | method |
        | GET    |
        | DELETE |

    Scenario Outline: <method> with multiple values params
      When <method> "/index":
        """
        {
          "key": ["value1", "value2"]
        }
        """
      Then got request:
        """
        : [{
          method= '<method>'
          path= '/index'
          queryStringParameters.'key[]'= [value1, value2]
        }]
        """
      Examples:
        | method |
        | GET    |
        | DELETE |

    Scenario Outline: <method> with single value param need encoding
      When <method> "/index":
        """
        {
          ":url:": "http://www.a.com/index?中文参数=中文值",
        }
        """
      Then got request:
        """
        : [{
          method= '<method>'
          path= '/index'
          queryStringParameters= {
            ':url:'= ['http://www.a.com/index?中文参数=中文值']
          }
        }]
        """
      Examples:
        | method |
        | GET    |
        | DELETE |

    Scenario Outline: <method> with header
      When <method> "/index":
        """
        ::headers: {
          key1: value1
          key2: [value2 value3]
        }
        """
      Then got request:
        """
        : [{
          method: '<method>'
          path: '/index'
          headers: {
            key1: ['value1']
            key2: ['value2', 'value3']
          }
        }]
        """
      Examples:
        | method |
        | GET    |
        | DELETE |

    Scenario Outline: <method> with header and query params
      When <method> "/index":
        """
        {
          "key": ["value1", "value2"]
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
            key1: ['value1']
            key2: ['value2', 'value3']
          }
          queryStringParameters: {
              'key[]': [value1 value2]
          }
        }]
        """
      Examples:
        | method |
        | GET    |
        | DELETE |

  Rule: POST/PUT/PATCH

    Background:
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

    Scenario Outline: guess content type from defautRequestContentType(dal:application/json) when doc type is empty
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

    Scenario Outline: guess content type from header when doc type is empty
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

    Scenario Outline: specify spec in step
      When <method> "RequestSpec" "/index":
        """ dal:application/json
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

  Rule: POST/PUT/PATCH dal:application/json

    Background:
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

    Scenario Outline: <method> with body and header
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
