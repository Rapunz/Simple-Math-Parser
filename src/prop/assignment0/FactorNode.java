package prop.assignment0;

import java.io.IOException;
import java.util.HashMap;

class FactorNode implements INode {
	Lexeme intNumber = null;
	Lexeme identifier = null;
	Lexeme leftParan = null;
	ExpressionNode expr = null;
	Lexeme rightParan = null;

	public FactorNode(Tokenizer tokenizer) throws IOException, TokenizerException, ParserException {
		if (tokenizer.current().token() == Token.INT_LIT) {
			intNumber = tokenizer.current();
		} else if (tokenizer.current().token() == Token.IDENT) {
			identifier = tokenizer.current();
		} else if (tokenizer.current().token() == Token.LEFT_PAREN) {
			leftParan = tokenizer.current();
			tokenizer.moveNext();
			expr = new ExpressionNode(tokenizer);
			if (tokenizer.current().token() != Token.RIGHT_PAREN) {
				throw new ParserException("Expected " + Token.RIGHT_PAREN +  " but was " + tokenizer.current().token());
			}
			rightParan = tokenizer.current();
		} else {
			throw new ParserException("Expected " + Token.INT_LIT +  " or " + Token.IDENT + " or " + Token.LEFT_PAREN + " but was " + tokenizer.current().token());
		}
		tokenizer.moveNext();
		
	}

	@Override
	public Object evaluate(Object[] args) throws Exception {
		if (intNumber != null) {
			return intNumber.value();
		} else if (identifier != null) {
			//Gets the value for the identifier from the hashmap that is sent down in args
			//Not really happy about this hashmap situation to store assigned variables, but it works
			Object value =  ((HashMap<String, Object>)args[0]).get((String)identifier.value());
			//All unassigned variables have default value 0
			value = value == null ? 0.0 : value;
			return value;
		} else if (expr != null){
			return expr.evaluate(args);
		}
		return null; 
	}

	@Override
	public void buildString(StringBuilder builder, int tabs) {
		builder.append("\t".repeat(tabs) + getClass().getSimpleName() + "\n");
		tabs++;
		if (intNumber != null) {
			builder.append("\t".repeat(tabs) + intNumber + "\n");
		} else if (identifier != null) {
			builder.append("\t".repeat(tabs) + identifier + "\n");
		} else {
			builder.append("\t".repeat(tabs) + leftParan + "\n");
			expr.buildString(builder, tabs);
			builder.append("\t".repeat(tabs) + rightParan + "\n");
		}

	}

}