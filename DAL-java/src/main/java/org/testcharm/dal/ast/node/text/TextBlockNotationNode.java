package org.testcharm.dal.ast.node.text;

import org.testcharm.dal.ast.node.DALNode;

import java.util.List;

public class TextBlockNotationNode extends DALNode {
    private final String notation;

    public TextBlockNotationNode(List<String> ls) {
        notation = String.join("", ls);
    }

    @Override
    public String inspect() {
        return notation;
    }
}
