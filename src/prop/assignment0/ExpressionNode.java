package prop.assignment0;

import java.io.IOException;

class ExpressionNode implements INode {
	TermNode term = null;
	Lexeme operation = null;
	ExpressionNode expr = null;

	public ExpressionNode(Tokenizer tokenizer) throws IOException, TokenizerException, ParserException {
		term = new TermNode(tokenizer);
		
		if (tokenizer.current().token() == Token.ADD_OP || tokenizer.current().token() == Token.SUB_OP) {
			operation = tokenizer.current();
			tokenizer.moveNext();
			expr = new ExpressionNode(tokenizer);
		}
	}

	@Override
	public Object evaluate(Object[] args) throws Exception {
		/*The term is evaluated, it does need to now about other assigned variables(arg[0]) 
		 * but should not take part in the associativity order for the expressionnode (arg[1] and arg[2])
		 */
		double value = (double)term.evaluate(new Object[] {args[0], null, null});
		
		if (args[1] != null) {
			/*If this expressionnode was preceded by a operator and a term we 
			 * first have to calculate the preceding term together with this term
			 */
			if ((Token)args[2] == Token.ADD_OP) {
				value = (double)args[1] + value;
			} else {
				value = (double)args[1] - value;
			}
		}
		
		if (operation != null) {
			/*If the term is succeeded by a operator and a expression we should save the value and operator 
			 * to be calculated together with the next expression
			 */
			args[1] = value;
			args[2] = operation.token();
			return expr.evaluate(args);
		} else {
			//This is the last term of this expression-chain, 
			//reset the preceding value and operator and return the value
			args[1] = null;
			args[2] = null;
			return value;
		}
		

	}

	@Override
	public void buildString(StringBuilder builder, int tabs) {
		builder.append("\t".repeat(tabs) + getClass().getSimpleName() + "\n");
		tabs++;
		term.buildString(builder, tabs);
		if (operation != null) {
			builder.append("\t".repeat(tabs) + operation + "\n");
			expr.buildString(builder, tabs);
		}
	}

}
