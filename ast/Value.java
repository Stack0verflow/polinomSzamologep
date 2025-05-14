package ast;

public class Value {
    public final Double number;
    public final Polynomial polynomial;

    public Value(Double number) {
        this.number = number;
        this.polynomial = null;
    }
    public Value(Polynomial poly) {
        this.number = null;
        this.polynomial = poly;
    }

    public boolean isNumber() {
        return number != null;
    }
    public boolean isPolynomial() {
        return polynomial != null;
    }
}
