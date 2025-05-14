package ast;

import java.util.ArrayList;

public class NodeList extends Node {
    public ArrayList<Node> nodes;

    public NodeList() {
        super(NodeType.NODE_LIST);
        this.nodes = new ArrayList<>();
    }

    public void add(Node node) {
        this.nodes.add(node);
    }

    @Override
    public String toString() {
        return "NodeList{" +
                "nodes=" + nodes +
                '}';
    }

    @Override
    public Polynomial exec(RuntimeContext context) {
        for (Node line : nodes) {
            Polynomial value = line.exec(context);
        }
        return null;
    }
}
