package prop.assignment0;

import java.io.IOException;
import java.util.HashMap;

class AssignmentNode implements INode {
	Lexeme identifier = null;
	Lexeme assignmentOp = null;
	ExpressionNode expr = null;
	Lexeme semiColon = null;

	public AssignmentNode(Tokenizer tokenizer) throws IOException, TokenizerException, ParserException {
		
		identifier = tokenizer.current();
		if (identifier.token() != Token.IDENT) {
			throw new ParserException("Expected " + Token.IDENT +  " but was " + identifier.token());
		}
		tokenizer.moveNext();
		
		assignmentOp = tokenizer.current();			
		if (assignmentOp.token() != Token.ASSIGN_OP) {
			throw new ParserException("Expected " + Token.ASSIGN_OP +  " but was " + assignmentOp.token());
		}
		tokenizer.moveNext();
		
		expr = new ExpressionNode(tokenizer);
		
		semiColon = tokenizer.current();
		if (tokenizer.current().token() != Token.SEMICOLON) {
			throw new ParserException("Expected " + Token.SEMICOLON +  " but was " + semiColon.token());
		}
		tokenizer.moveNext();
	}

	@Override
	public Object evaluate(Object[] args) throws Exception {
		Object exprValue = expr.evaluate(args);
		//Stores the identifier together with it's assigned value in hashmap that is then used by other assignments
		//Not really happy about this hashmap situation to store assigned variables, but it works
		((HashMap<String, Object>)args[0]).put((String)identifier.value(), exprValue);
		return String.format("%s %s %.1f", identifier.value(), assignmentOp.value(), exprValue);
	}

	@Override
	public void buildString(StringBuilder builder, int tabs) {
		builder.append("\t".repeat(tabs) + getClass().getSimpleName() + "\n");
		tabs++;
		builder.append("\t".repeat(tabs) + identifier + "\n");
		builder.append("\t".repeat(tabs) + assignmentOp + "\n");
		expr.buildString(builder, tabs);
		builder.append("\t".repeat(tabs) + semiColon + "\n");
	}
}
