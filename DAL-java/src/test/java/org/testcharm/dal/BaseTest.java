package org.testcharm.dal;

import org.testcharm.interpreter.SourceCode;

import static org.testcharm.interpreter.Notation.notation;
import static java.util.Arrays.asList;

public class BaseTest {
    public static SourceCode createSourceCode(String code) {
        return new SourceCode(code, asList(notation("#"), notation("//")));
    }
}
