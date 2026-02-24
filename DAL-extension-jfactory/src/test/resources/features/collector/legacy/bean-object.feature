Feature: Nested Object

  Background:
    Given the following declarations:
      """
      JFactory jFactory = new JFactory();
      """
    Given the following bean definition:
      """
      public class Bean {
        public Object sub;
        public String name;
      }
      """
    Given the following declarations:
      """
      Collector collector = jFactory.collector(Bean.class);
      """

  Rule: Sub is a List Map

    Scenario: Specify Grand Child Properties
      When "collector" collect and build with the following properties:
        """
        sub= [{
          value1= v1
          value2= v2
        }]
        """
      Then the result should be:
        """
        = {
          sub= [{
            value1= v1
            value2= v2
            ::object.class.name= java.util.LinkedHashMap
          }]

          sub::object.class.name= java.util.ArrayList
          name= /^name.*/
        }
        """

    Scenario: Specify Grand Child All Default
      When "collector" collect and build with the following properties:
        """
        sub= [{}]
        """
      Then the result should be:
        """
        = {
          sub= [{
            ::object.class.name= java.util.LinkedHashMap
          }]
          sub::object.class.name= java.util.ArrayList
          name= /^name.*/
        }
        """

    Scenario: Specify Empty List
      When "collector" collect and build with the following properties:
        """
        sub= []
        """
      Then the result should be:
        """
        = {
          sub= []
          sub::object.class.name= java.util.ArrayList
          name= /^name.*/
        }
        """

  Rule: Sub is a List Bean by List Spec

    Background:
      Given the following bean definition:
        """
        public class Sub {
          public String value1, value2;
        }
        """
      Given the following spec definition:
        """
        public class SubListSpec extends Spec<java.util.List<Sub>> {}
        """
      And register as follows:
        """
        jFactory.register(SubListSpec.class);
        """

    Scenario: Specify Grand Child Properties
      When "collector" collect and build with the following properties:
        """
        sub(SubListSpec): [{
          value1= v1
          value2= v2
        }]
        """
      Then the result should be:
        """
        = {
          sub= [{
            value1= v1
            value2= v2
            class.simpleName= Sub
          }]
          sub.class.simpleName= ArrayList
          name= /^name.*/
        }
        """

    Scenario: Specify Grand Child All Default
      When "collector" collect and build with the following properties:
        """
        sub(SubListSpec): [{...}]
        """
      Then the result should be:
        """
        = {
          sub= [{
            value1= /^value1.*/
            value2= /^value2.*/
          }]
          name= /^name.*/
        }
        """

  Rule: Sub is a List Bean by Element Spec

    Background:
      Given the following bean definition:
        """
        public class Sub {
          public String value1, value2;
        }
        """
      Given the following spec definition:
        """
        public class SubSpec extends Spec<Sub> {}
        """
      And register as follows:
        """
        jFactory.register(SubSpec.class);
        """

    Scenario: Specify Grand Child Properties
      When "collector" collect and build with the following properties:
        """
        sub= [(SubSpec): {
          value1= v1
          value2= v2
        }]
        """
      Then the result should be:
        """
        = {
          sub= [{
            value1= v1
            value2= v2
            class.simpleName= Sub
          }]
          sub.class.simpleName= ArrayList
          name= /^name.*/
        }
        """

    Scenario: Specify Grand Child All Default
      When "collector" collect and build with the following properties:
        """
        sub= [(SubSpec): {...}]
        """
      Then the result should be:
        """
        = {
          sub= [{
            value1= /^value1.*/
            value2= /^value2.*/
          }]
          name= /^name.*/
        }
        """

  Rule: Sub is Bean by Spec

    Background:
      Given the following bean definition:
        """
        public class Sub {
          public String value1, value2;
        }
        """
      And the following spec definition:
        """
        public class SubSpec extends Spec<Sub> {
          public void main() {
            property("value1").value("sub1");
          }
        }
        """
      And register as follows:
        """
        jFactory.register(SubSpec.class);
        """

    Scenario: Specify Child With Spec and Properties
      When "collector" collect and build with the following properties:
        """
        sub(SubSpec): {
          value2= sub2
        }
        """
      Then the result should be:
        """
        = {
          sub= {
            value1= sub1
            value2= sub2
          }
          name= /^name.*/
        }
        """

    Scenario: Specify Child with Spec and Default
      When "collector" collect and build with the following properties:
        """
        sub(SubSpec): {...}
        """
      Then the result should be:
        """
        = {
          sub= {
            value1= sub1
            value2= /^value2.*/
          }
          name= /^name.*/
        }
        """

    Scenario: Specify Child With Spec Intently Creation and Properties
      When "collector" collect with the following properties:
        """
        : {
          sub(SubSpec)!: {
              value2= sub2
          }
          name= hello
        }
        """
      Then the result should be:
        """
        : {
          ::properties= {
            name= hello
            'sub(SubSpec)!.value2'= sub2
          }
          ::build= {
            name= hello
            sub= {
              value1= sub1
              value2= sub2
            }
          }
        }
        """

    Scenario: Specify Child With Spec Intently Creation and Default
      When "collector" collect with the following properties:
        """
        : {
          sub(SubSpec)!: {...}
          name= hello
        }
        """
      Then the result should be:
        """
        : {
          ::properties= {
            name= hello
            'sub(SubSpec)!'= {}
          }
        ::build= {
          name= hello
          sub= {
            value1= sub1
            value2= /^value2.*/
            }
          }
        }
        """

  Rule: Complex

    Background:
      Given the following bean definition:
        """
        public class Sub {
          public String value1, value2;
        }
        """
      Given the following spec definition:
        """
        public class SubSpec extends Spec<Sub> {}
        """
      And register as follows:
        """
        jFactory.register(SubSpec.class);
        """

    Scenario: complex list

      When "collector" collect with the following properties:
        """
        sub= [
          (SubSpec): {value1= hello}
          100,
          (SubSpec): {value2= world}
        ]
        """
      Then the result should be:
        """
        : {
          ::properties= {
            sub= [
              {
                value1= hello
                value2= /^value2.*/
                class.simpleName= Sub
              }
              100
              {
                value1= /^value1.*/
                value2= world
                class.simpleName= Sub
              }
            ]
          }
          ::build: {
            sub= [
              {
                value1= hello
                value2= /^value2.*/
                class.simpleName= Sub
              }
              100
              {
                value1= /^value1.*/
                value2= world
                class.simpleName= Sub
              }
            ]
            sub.class.simpleName= ArrayList
          }
        }
        """
