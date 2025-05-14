package ast;

public class Evaluation extends Node {
    Node polynomial;
    Node xValue;

    public Evaluation(Node polynomial, Node xValue) {
        super(NodeType.EVALUATION);
        this.polynomial = polynomial;
        this.xValue = xValue;
    }

    @Override
    public String toString() {
        return "Evaluation{" +
                "polynomial=" + polynomial +
                ", xValue=" + xValue +
                '}';
    }

    @Override
    public Polynomial exec(RuntimeContext context) {
        Polynomial poly = polynomial.exec(context);
        Polynomial xPoly = xValue.exec(context);

        double x = xPoly.evaluate(0);
        double result = poly.evaluate(x);

        return Polynomial.fromNumber(result);
    }
}
