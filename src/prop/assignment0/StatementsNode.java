package prop.assignment0;

import java.io.IOException;

class StatementsNode implements INode {
	AssignmentNode assign = null;
	StatementsNode stmts = null;

	public StatementsNode(Tokenizer tokenizer) throws IOException, TokenizerException, ParserException {
		
		if (tokenizer.current().token() != Token.RIGHT_CURLY) {
			assign = new AssignmentNode(tokenizer);
			stmts = new StatementsNode(tokenizer);
		}
		
	}
	
	@Override
	public Object evaluate(Object[] args) throws Exception {
		String str = "";
		if (assign != null) {
			str += assign.evaluate(args) +"\n";
			str += stmts.evaluate(args);
		}
		return str;
	}

	@Override
	public void buildString(StringBuilder builder, int tabs) {
		builder.append("\t".repeat(tabs) + getClass().getSimpleName() + "\n");
		
		if (assign != null) {
			tabs++;
			assign.buildString(builder, tabs);
			stmts.buildString(builder, tabs);
		}
	}
	
}
