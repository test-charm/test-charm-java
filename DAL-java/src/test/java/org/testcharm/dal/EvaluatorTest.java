package org.testcharm.dal;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testcharm.dal.Assertions.expectRun;
import static org.testcharm.dal.Evaluator.*;

class EvaluatorTest {

    @Test
    void run() {
        assertEquals(2, (int) evaluate("+1").on(1));
    }

    @Test
    void with_object_scope() {
        assertEquals("hello", evaluate(":{length: 5, toUpperCase: HELLO}").on("hello"));
    }

    @Test
    void error_message() {
        expectRun(() -> evaluate(":{length: 4, toUpperCase: HELLO}").on("hello"))
                .should("::throw: {\n" +
                        "   message= ```\n" +
                        "\n" +
                        "            :{length: 4, toUpperCase: HELLO}\n" +
                        "                      ^\n" +
                        "\n" +
                        "            Expected to match: java.lang.Integer\n" +
                        "            <4>\n" +
                        "             ^\n" +
                        "            Actual: java.lang.Integer\n" +
                        "            <5>\n" +
                        "             ^\n" +
                        "            ```\n" +
                        "}"
                );
    }

    @Test
    void evaluate_object() {
        assertEquals("hello", evaluateObject(":{length: 5, toUpperCase: HELLO}").on("hello"));
    }

    @Test
    void evaluate_object_ignore_verification_opt() {
        assertEquals("hello", evaluateObject("length: 5 toUpperCase: HELLO").on("hello"));
    }

    @Test
    void evaluateAll_() {
        assertEquals(Arrays.asList(1, 2, 3), evaluateAll("1 2 3").on(null));
    }
}
