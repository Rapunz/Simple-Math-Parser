package prop.assignment0;

import java.io.IOException;
import java.util.HashMap;


class BlockNode implements INode {

	HashMap<String, Object> identifierValues = new HashMap<>();
	Lexeme leftCurly = null;
	StatementsNode stmts = null;
	Lexeme rightCurly = null;

	public BlockNode(Tokenizer tokenizer) throws IOException, TokenizerException, ParserException {
		
		leftCurly = tokenizer.current();
		if (leftCurly.token() != Token.LEFT_CURLY) {
			throw new ParserException("Expected " + Token.LEFT_CURLY +  " but was " + leftCurly.token());
		}
		tokenizer.moveNext();
		
		stmts = new StatementsNode(tokenizer);		
		
		rightCurly = tokenizer.current();
		if (rightCurly.token() != Token.RIGHT_CURLY) {
			throw new ParserException("Expected " + Token.RIGHT_CURLY +  " but was " + rightCurly.token());
		}
		tokenizer.moveNext();
		
		if (tokenizer.current().token() != Token.EOF) {
			throw new ParserException("Expected end of file but was " + tokenizer.current().token());
		}
	}
	
	@Override
	public Object evaluate(Object[] args) throws Exception {
		/*I'm not too happy about my handling of Object[] args that 
		 * I use both for identifier-values and to help with associativity later, 
		 * it's not an elegant solution, but it kind of works. 
		 */
		return stmts.evaluate(new Object[] {identifierValues, null, null});
	}
	
	@Override
	public void buildString(StringBuilder builder, int tabs) {
		builder.append("\t".repeat(tabs) + getClass().getSimpleName() + "\n");
		builder.append("\t".repeat(tabs) + leftCurly + "\n");	
		stmts.buildString(builder, tabs+1);
		builder.append("\t".repeat(tabs) + rightCurly + "\n");
	}
}
