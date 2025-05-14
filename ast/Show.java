package ast;

public class Show extends Node {
    Node expression;

    public Show(Node expression) {
        super(NodeType.SHOW);
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Show{" +
                "expression=" + expression +
                '}';
    }

    @Override
    public Polynomial exec(RuntimeContext context) {
        Polynomial result = expression.exec(context);
        System.out.println(result.toString());
        return null;
    }
}
