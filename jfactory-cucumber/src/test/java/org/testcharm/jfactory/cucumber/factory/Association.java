package org.testcharm.jfactory.cucumber.factory;

import org.testcharm.jfactory.Spec;

public class Association {
    public static class Company extends Spec<org.testcharm.jfactory.cucumber.entity.association.Company> {
        @Override
        public void main() {
            property("departments[]").is(Department.class);
            property("departments").reverseAssociation("company");
        }
    }

    public static class Department extends Spec<org.testcharm.jfactory.cucumber.entity.association.Department> {
        @Override
        public void main() {
            property("company").is(Company.class);

            property("employees[]").is(Employee.class);
            property("employees").reverseAssociation("department");
        }
    }

    public static class Employee extends Spec<org.testcharm.jfactory.cucumber.entity.association.Employee> {

        @Override
        public void main() {
            property("department").is(Department.class);
        }
    }
}
