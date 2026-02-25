package com.github.leeonky.dal.ast.node;

public class ConstRemarkNode extends ParenthesesNode {
    public ConstRemarkNode(DALNode node) {
        super(node);
    }

    @Override
    public boolean needPrefixBlankWarningCheck() {
        return true;
    }
}
