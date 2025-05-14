package ast;

public class PolynomialLiteral extends Node {
    Polynomial polynomial;

    public PolynomialLiteral(Polynomial polynomial) {
        super(NodeType.POLYNOMIAL);
        this.polynomial = polynomial;
    }

    @Override
    public String toString() {
        return polynomial.toString();
    }

    @Override
    public Polynomial exec(RuntimeContext context) {
        return polynomial;
    }
}
