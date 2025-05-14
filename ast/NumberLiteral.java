package ast;

public class NumberLiteral extends Node {
    double value;

    public NumberLiteral(double value) {
        super(NodeType.NUMBER);
        this.value = value;
    }

    @Override
    public String toString() {
        return "NumberLiteral{" +
                "value=" + value +
                '}';
    }

    @Override
    public Polynomial exec(RuntimeContext context) {
        return Polynomial.fromNumber(value);
    }
}
