grammar_name="PolynomialCalculator"

sh generateLanguage.sh $grammar_name
sh translate.sh
sh run.sh $grammar_name
sh clear.sh $grammar_name