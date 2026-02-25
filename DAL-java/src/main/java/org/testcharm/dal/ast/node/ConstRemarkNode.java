package org.testcharm.dal.ast.node;

public class ConstRemarkNode extends ParenthesesNode {
    public ConstRemarkNode(DALNode node) {
        super(node);
    }

    @Override
    public boolean needPrefixBlankWarningCheck() {
        return true;
    }
}
