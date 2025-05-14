package ast;

public class UnaryOperation extends Node {
    String operation;
    Node argument;

    public UnaryOperation(String operation, Node argument) {
        super(NodeType.UNARY_OP);
        this.operation = operation;
        this.argument = argument;
    }

    @Override
    public String toString() {
        return "UnaryOperation{" +
                "operation='" + operation + '\'' +
                ", argument=" + argument +
                '}';
    }

    @Override
    public Polynomial exec(RuntimeContext context) {
        Polynomial value = argument.exec(context);
        return switch (operation) {
            case "+" -> value;
            case "-" -> value.negate();
            default -> null;
        };
    }
}
