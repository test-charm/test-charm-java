@jfactory
Feature: RESTful new api steps

  Background:
    Given base url "http://www.a.com"

  Rule: GET/DELETE

    Scenario Outline: <method> with no params
      When <method> "/index"
      Then "http://www.a.com" got a "<method>" request on "/index"
      Examples:
        | method |
        | GET    |
        | DELETE |

    Scenario Outline: <method> with params in url
      When <method> "/index?中文参数=中文值&second=value2"
      Then "http://www.a.com" got a "<method>" request on "/index" with params
        | 中文参数 | second |
        | 中文值  | value2 |
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
          method: '<method>'
          path: '/index'
          headers: {
            key1: ['value1']
            key2: ['value2', 'value3']
          }
        }]
        """
      And "http://www.a.com" got a "<method>" request on "/index"
      Examples:
        | method |
        | GET    |
        | DELETE |

  Rule: GET/DELETE with docstring

    Scenario Outline: <method> with single value param
      When <method> "/index":
        """
        {
          "中文参数": "中文值",
          "second": "value1"
        }
        """
      Then "http://www.a.com" got a "<method>" request on "/index" with params
        | 中文参数 | second |
        | 中文值  | value1 |
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
      Then "http://www.a.com" got a "<method>" request on "/index" with params from docstring
        """
        : [{
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
      Then "http://www.a.com" got a "<method>" request on "/index" with params
        | :url:                           |
        | http://www.a.com/index?中文参数=中文值 |
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
      And "http://www.a.com" got a "<method>" request on "/index"
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
      And "http://www.a.com" got a "<method>" request on "/index"
      Examples:
        | method |
        | GET    |
        | DELETE |

  Rule: POST/PUT/PATCH

    Scenario Outline: <method> with body and no params
      When <method> "/index":
        """
        { "text": "Hello world" }
        """
      Then "http://www.a.com" got a "<method>" request on "/index" with body
        """
        { "text": "Hello world" }
        """
      Examples:
        | method |
        | POST   |
        | PUT    |
        | PATCH  |

    Scenario Outline: <method> with body and params
      When <method> "/index?中文参数=中文值&second=value2":
        """
        { "text": "Hello world" }
        """
      Then "http://www.a.com" got a "<method>" request on "/index" with params "中文参数=中文值&second=value2" and body
        """
        { "text": "Hello world" }
        """
      Examples:
        | method |
        | POST   |
        | PUT    |
        | PATCH  |

    Scenario Outline: <method> with body and header and default content type is application/json
      Given header by RESTful api:
        """
        {
          "key1": "value1",
          "key2": ["value2", "value3"]
        }
        """
      When <method> "/index":
        """
        { "text": "Hello world" }
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
        }]
        """
      And "http://www.a.com" got a "<method>" request on "/index"
      Examples:
        | method |
        | POST   |
        | PUT    |
        | PATCH  |

    Scenario Outline: <method> with content type and not set in header
      When <method> "/index":
        """ text/html
        { "text": "Hello world" }
        """
      Then got request:
        """
        : [{
          headers['Content-Type']: ['text/html']
        }]
        """
      Examples:
        | method |
        | POST   |
        | PUT    |
        | PATCH  |

    Scenario Outline: <method> without content type but set in header already
      Given header by RESTful api:
        """
        {
          "<header>": "text/html"
        }
        """
      When <method> "/index":
        """
        { "text": "Hello world" }
        """
      Then got request:
        """
        : [{
          headers['<header>']: ['text/html']
        }]
        """
      Examples:
        | method | header       |
        | POST   | Content-Type |
        | PUT    | content-type |
        | PATCH  | content-type |

    Scenario Outline: <method> with content type and set in header
      Given header by RESTful api:
        """
        {
          "Content-Type": "anyContentType"
        }
        """
      When <method> "/index":
        """ text/html
        { "text": "Hello world" }
        """
      Then got request:
        """
        : [{
          headers['Content-Type']: ['text/html']
        }]
        """
      Examples:
        | method |
        | POST   |
        | PUT    |
        | PATCH  |

    Scenario Outline: <method> without content type and set in header
      Given header by RESTful api:
        """
        {
          "anyHeader": null
        }
        """
      When <method> "/index":
        """
        {}
        """
      Then got request:
        """
        : [{
          headers['anyHeader']: null
        }]
        """
      Examples:
        | method |
        | POST   |
        | PUT    |
        | PATCH  |
