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

  Scenario: inherit from super
    Given the following java class:
    """
    public class Bean {
      @org.testcharm.dal.type.MetaProperty
      public String meta() {
        return "hello";
      }
    }
    """
    Given the following java class:
    """
    public class SubBean extends Bean{
    }
    """
    Then the following verification for the instance of java class "SubBean" should pass:
    """
    ::meta= hello
    """

  Scenario: custom a new name in annotation
    Given the following java class:
    """
    public class Bean {
      @org.testcharm.dal.type.MetaProperty("meta")
      public String fun() {
        return "hello";
      }
    }
    """
    Then the following verification for the instance of java class "Bean" should pass:
    """
    ::meta= hello
    """

  Scenario: raise error when duplicated name
    Given the following java class:
    """
    public class Bean {
      @org.testcharm.dal.type.MetaProperty("meta")
      public String fun() {
        return "hello";
      }

      @org.testcharm.dal.type.MetaProperty("meta")
      public String fun_() {
        return "hello";
      }
    }
    """
    When use a instance of java class "Bean" to evaluate:
    """
    ::meta= hello
    """
    Then failed with the message:
    """
    java.lang.IllegalStateException: Duplicate meta property `meta` in #package#Bean
    """
    And got the following notation:
    """
    ::meta= hello
      ^
    """

  Scenario: annotation > annotation pattern
    Given the following java class:
    """
    public class Bean {
      @org.testcharm.dal.type.MetaProperty(".*")
      public String fun1() {
        return "world";
      }

      @org.testcharm.dal.type.MetaProperty("meta")
      public String fun2() {
        return "hello";
      }
    }
    """
    Then the following verification for the instance of java class "Bean" should pass:
    """
    ::meta= hello
    """

  Scenario: annotation > lambda
    Given the following java class:
    """
    public class Bean {
      @org.testcharm.dal.type.MetaProperty
      public String meta() {
        return "hello";
      }
    }
    """
    And register DAL:
    """
    dal.getRuntimeContextBuilder().registerMetaProperty(Bean.class, "meta", meta-> "world");
    """
    Then the following verification for the instance of java class "Bean" should pass:
    """
    ::meta= hello
    """

  Scenario: annotation pattern > lambda
    Given the following java class:
    """
    public class Bean {
      @org.testcharm.dal.type.MetaProperty(".*")
      public String fun1() {
        return "hello";
      }
    }
    """
    And register DAL:
    """
    dal.getRuntimeContextBuilder().registerMetaProperty(Bean.class, "meta", meta-> "world");
    """
    Then the following verification for the instance of java class "Bean" should pass:
    """
    ::meta= world
    """

  Rule: MetaData as first parameter

    Scenario: no args return object
      Given the following java class:
      """
      public class Bean {
        @org.testcharm.dal.type.MetaProperty(".*")
        public String fun(MetaData metaData) {
          return (String)metaData.name();
        }
      }
      """
      Then the following verification for the instance of java class "Bean" should pass:
      """
      ::meta= meta
      """

    Scenario: with additional one args
      Given the following java class:
      """
      public class Bean {
        @org.testcharm.dal.type.MetaProperty(".*")
        public String fun(MetaData metaData, String message) {
          return (String)metaData.name() + "-" + message;
        }
      }
      """
      Then the following verification for the instance of java class "Bean" should pass:
      """
      ::meta.hello= meta-hello
      """

    Scenario: with additional two args
      Given the following java class:
      """
      public class Bean {
        @org.testcharm.dal.type.MetaProperty(".*")
        public String fun(MetaData metaData, String message1, String message2) {
          return (String)metaData.name() + "-" + message1 + "-" + message2;
        }
      }
      """
      Then the following verification for the instance of java class "Bean" should pass:
      """
      ::meta.hello.world= meta-hello-world
      """

    Scenario: with additional three args
      Given the following java class:
      """
      public class Bean {
        @org.testcharm.dal.type.MetaProperty(".*")
        public String fun(MetaData metaData, String message1, String message2, String message3) {
          return (String)metaData.name() + "-" + message1 + "-" + message2 + "-" + message3;
        }
      }
      """
      Then the following verification for the instance of java class "Bean" should pass:
      """
      ::meta.hello.world.bye= meta-hello-world-bye
      """

    Scenario: convert to proper type
      Given the following java class:
      """
      public class Bean {
          @org.testcharm.dal.type.MetaProperty(".*")
          public Object fun(MetaData metaData, int value) {
            return new HashMap<String, Object>() {{
                put("name", metaData.name());
                put("value", value);
            }};
          }
      }
      """
      Then the following verification for the instance of java class "Bean" should pass:
      """
      ::meta['9']= {
        name= meta
        value= 9
      }
      """

    Scenario: return type is Data<?>
      Given the following java class:
      """
      public class Bean {
          @org.testcharm.dal.type.MetaProperty(".*")
          public Data<?> fun(MetaData metaData) {
            return metaData.data().map(v -> v.getClass().getSimpleName()+ "-" + metaData.name());
          }
      }
      """
      Then the following verification for the instance of java class "Bean" should pass:
      """
      ::meta= Bean-meta
      """

    Scenario: return type is Data<?> and arg
      Given the following java class:
      """
      public class Bean {
          @org.testcharm.dal.type.MetaProperty(".*")
          public Data<?> fun(MetaData metaData, String message) {
            return metaData.data().map(v -> v.getClass().getSimpleName()+ "-" + metaData.name() + "-" + message);
          }
      }
      """
      Then the following verification for the instance of java class "Bean" should pass:
      """
      ::meta.hello= Bean-meta-hello
      """

    Scenario: return type is Data<?> and more args
      Given the following java class:
      """
      public class Bean {
          @org.testcharm.dal.type.MetaProperty(".*")
          public Data<?> fun(MetaData metaData, String message1, String message2, String message3) {
            return metaData.data().map(v -> v.getClass().getSimpleName()+ "-" + metaData.name() + "-" + message1 + "-" + message2 + "-" + message3);
          }
      }
      """
      Then the following verification for the instance of java class "Bean" should pass:
      """
      ::meta.hello.world.bye= Bean-meta-hello-world-bye
      """

  Rule: No MetaData and with Args

    Scenario: with additional one args
      Given the following java class:
        """
        public class Bean {
          @org.testcharm.dal.type.MetaProperty
          public String meta(String message) {
            return message;
          }
        }
        """
      Then the following verification for the instance of java class "Bean" should pass:
        """
        ::meta.hello= hello
        """

    Scenario: with additional two args
      Given the following java class:
        """
        public class Bean {
          @org.testcharm.dal.type.MetaProperty
          public String meta(String message1, String message2) {
            return message1 + "-" + message2;
          }
        }
        """
      Then the following verification for the instance of java class "Bean" should pass:
        """
        ::meta.hello.world= hello-world
        """

    Scenario: with additional three args
      Given the following java class:
        """
        public class Bean {
          @org.testcharm.dal.type.MetaProperty
          public String meta(String message1, String message2, String message3) {
            return message1 + "-" + message2 + "-" + message3;
          }
        }
        """
      Then the following verification for the instance of java class "Bean" should pass:
        """
        ::meta.hello.world.bye= hello-world-bye
        """

    Scenario: convert to proper type
      Given the following java class:
        """
        public class Bean {
            @org.testcharm.dal.type.MetaProperty
            public Object meta(int value) {
              return value;
            }
        }
        """
      Then the following verification for the instance of java class "Bean" should pass:
        """
        ::meta['9']= 9
        """

    Scenario: missing args
      Given the following java class:
        """
        public class Bean {
          @org.testcharm.dal.type.MetaProperty
          public String meta(String message1, String message2) {
            return message1 + "-" + message2;
          }
        }
        """
      When use a instance of java class "Bean" to evaluate:
        """
        ::meta.hello= hello
        """
      Then failed with the message:
        """
        Missing required argument
        """
      And got the following notation:
        """
        ::meta.hello= hello
                      ^
        """
      When use a instance of java class "Bean" to evaluate:
        """
        ::meta.hello: hello
        """
      Then failed with the message:
        """
        Missing required argument
        """
      And got the following notation:
        """
        ::meta.hello: hello
                      ^
        """
