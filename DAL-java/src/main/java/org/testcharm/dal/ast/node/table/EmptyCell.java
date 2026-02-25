package org.testcharm.dal.ast.node.table;

import org.testcharm.dal.ast.node.DALNode;

public class EmptyCell extends DALNode {
    @Override
    public String inspect() {
        return "";
    }
}
