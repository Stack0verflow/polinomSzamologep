package ast;

public class BinaryOperation extends Node {
    String operation;
    Node lhs;
    Node rhs;

    public BinaryOperation(String operation, Node lhs, Node rhs) {
        super(NodeType.BINARY_OP);
        this.operation = operation;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public String toString() {
        return "BinaryOperation{" +
                "operation='" + operation + '\'' +
                ", lhs=" + lhs +
                ", rhs=" + rhs +
                '}';
    }

    @Override
    public Polynomial exec(RuntimeContext context) {
        Polynomial lhsValue = lhs.exec(context);
        Polynomial rhsValue = rhs.exec(context);
        switch (operation) {
            case "+": return lhsValue.add(rhsValue);
            case "-": return lhsValue.subtract(rhsValue);
            case "*": return lhsValue.multiply(rhsValue);
            case "/": return lhsValue.divide(rhsValue);
            case "%": return lhsValue.mod(rhsValue);
            default: return null;
        }
    }
}
