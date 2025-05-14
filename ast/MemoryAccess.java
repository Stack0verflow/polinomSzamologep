package ast;

public class MemoryAccess extends Node {
    String varName;

    public MemoryAccess(String varName) {
        super(NodeType.MEMORY_ACCESS);
        this.varName = varName;
    }

    @Override
    public String toString() {
        return "MemoryAccess{" +
                "varName='" + varName + '\'' +
                '}';
    }

    @Override
    public Polynomial exec(RuntimeContext context) {
        return context.getVariable(varName);
    }
}
