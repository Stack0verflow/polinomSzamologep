import ast.Node;
import ast.RuntimeContext;
import org.antlr.v4.runtime.*;

public class PolynomialCalculator {
    public static void main(String[] args) throws Exception {
        PolynomialCalculatorLexer lexer = new PolynomialCalculatorLexer(new ANTLRFileStream(args[0]));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PolynomialCalculatorParser parser = new PolynomialCalculatorParser(tokens);

        Node node = parser.start().node;

        RuntimeContext runtimeContext = new RuntimeContext();
        node.exec(runtimeContext);
    }
}
