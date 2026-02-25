package org.testcharm.dal.ast.node.text;

import org.testcharm.dal.ast.node.DALNode;
import org.testcharm.dal.runtime.RuntimeContextBuilder;
import org.testcharm.dal.runtime.TextFormatter;

public class TextBlockAttributeNode extends DALNode {
    final String name;

    public TextBlockAttributeNode(String Name) {
        name = Name;
    }

    @Override
    public String inspect() {
        return name;
    }

    public TextFormatter extractTextFormatter(RuntimeContextBuilder.DALRuntimeContext context) {
        return context.fetchFormatter(name, getPositionBegin());
    }
}
