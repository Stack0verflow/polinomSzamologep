grammar_name="PolynomialCalculator"

sh generateLanguage.sh $grammar_name
sh translate.sh
sh gui.sh $grammar_name
sh clear.sh $grammar_name