package ast;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Polynomial {
    public TreeMap<Integer, Double> terms;
    private final double epsilon = 1e-10;

    public Polynomial() {
        this.terms = new TreeMap<>(Collections.reverseOrder());
    }

    public static class DivisionResult {
        public final Polynomial quotient;
        public final Polynomial remainder;

        public DivisionResult(Polynomial quotient, Polynomial remainder) {
            this.quotient = quotient;
            this.remainder = remainder;
        }
    }

    public static Polynomial fromNumber(double value) {
        Polynomial p = new Polynomial();
        if (Math.abs(value) > p.epsilon) {
            p.addTerm(0, value);
        }
        return p;
    }

    public void addTerm(int exponent, double coefficient) {
        if (Math.abs(coefficient) < epsilon) return;
        terms.merge(exponent, coefficient, Double::sum);
        if (Math.abs(terms.get(exponent)) < epsilon) {
            terms.remove(exponent);
        }
    }

    public Polynomial add(Polynomial other) {
        Polynomial result = new Polynomial();
        for (var e : this.terms.entrySet()) {
            result.addTerm(e.getKey(), e.getValue());
        }
        for (var e : other.terms.entrySet()) {
            result.addTerm(e.getKey(), e.getValue());
        }
        return result;
    }

    public Polynomial subtract(Polynomial other) {
        Polynomial result = new Polynomial();
        for (var e : this.terms.entrySet()) {
            result.addTerm(e.getKey(), e.getValue());
        }
        for (var e : other.terms.entrySet()) {
            result.addTerm(e.getKey(), -e.getValue());
        }
        return result;
    }

    public Polynomial multiply(Polynomial other) {
        Polynomial result = new Polynomial();
        for (var a : this.terms.entrySet()) {
            for (var b : other.terms.entrySet()) {
                int exponent = a.getKey() + b.getKey();
                double coefficient = a.getValue() * b.getValue();
                result.addTerm(exponent, coefficient);
            }
        }
        return result;
    }

    public Polynomial divide(Polynomial other) {
        return divideWithRemainder(other).quotient;
    }

    public Polynomial mod(Polynomial other) {
        return divideWithRemainder(other).remainder;
    }

    private DivisionResult divideWithRemainder(Polynomial divisor) {
        if (divisor.terms.isEmpty()) {
            throw new ArithmeticException("Cannot divide by zero polynomial");
        }

        Polynomial quotient = new Polynomial();
        Polynomial remainder = new Polynomial();
        for (Map.Entry<Integer, Double> entry : this.terms.entrySet()) {
            remainder.addTerm(entry.getKey(), entry.getValue());
        }

        int divisorDegree = divisor.terms.firstKey();
        double divisorLeadingCoefficient = divisor.terms.get(divisorDegree);

        while (!remainder.terms.isEmpty() && remainder.terms.firstKey() >= divisorDegree) {
            int leadDegree = remainder.terms.firstKey();
            double leadCoefficient = remainder.terms.get(leadDegree);

            int degreeDiff = leadDegree - divisorDegree;
            double coefficientQuotient = leadCoefficient / divisorLeadingCoefficient;

            Polynomial term = new Polynomial();
            term.addTerm(degreeDiff, coefficientQuotient);
            quotient = quotient.add(term);

            Polynomial subtractTerm = divisor.multiply(term);
            remainder = remainder.subtract(subtractTerm);
        }

        return new DivisionResult(quotient, remainder);
    }

    public Polynomial negate() {
        Polynomial result = new Polynomial();
        for (Map.Entry<Integer, Double> entry : this.terms.entrySet()) {
            result.addTerm(entry.getKey(), -entry.getValue());
        }
        return result;
    }

    public double evaluate(double x) {
        double result = 0.0;
        for (var e : terms.entrySet()) {
            result += e.getValue() * Math.pow(x, e.getKey());
        }
        return result;
    }

    @Override
    public String toString() {
        if (terms.isEmpty()) return "0";
        StringBuilder sb = new StringBuilder();
        for (var e : terms.entrySet()) {
            double coefficient = e.getValue();
            int exponent = e.getKey();

            if (sb.length() > 0) {
                sb.append(coefficient >= 0 ? " + " : " - ");
            } else if (coefficient < 0) {
                sb.append("-");
            }

            double absCoefficient = Math.abs(coefficient);
            if (exponent == 0 || absCoefficient != 1.0) {
                sb.append(absCoefficient);
            }
            if (exponent != 0) {
                sb.append("x");
                if (exponent != 1) {
                    sb.append("^").append(exponent);
                }
            }
        }
        return sb.toString();
    }
}
