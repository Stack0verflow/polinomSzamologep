package ast;

public class MemoryAssign extends Node {
    String varName;
    Node value;

    public MemoryAssign(String varName, Node value) {
        super(NodeType.MEMORY_ASSIGN);
        this.varName = varName;
        this.value = value;
    }

    @Override
    public String toString() {
        return "MemoryAssign{" +
                "varName='" + varName + '\'' +
                ", value=" + value +
                '}';
    }

    @Override
    public Polynomial exec(RuntimeContext context) {
        Polynomial value = this.value.exec(context);
        context.variables.put(varName, value);
        return value;
    }
}
