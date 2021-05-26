package prop.assignment0;

import java.io.IOException;

class TermNode implements INode {
	FactorNode factor = null;
	Lexeme operation = null;
	TermNode term = null;
	
	public TermNode(Tokenizer tokenizer) throws IOException, TokenizerException, ParserException {
		factor = new FactorNode(tokenizer);
		
		if (tokenizer.current().token() == Token.MULT_OP || tokenizer.current().token() == Token.DIV_OP) {
			operation = tokenizer.current();
			tokenizer.moveNext();
			term = new TermNode(tokenizer);
		}
	}

	@Override
	public Object evaluate(Object[] args) throws Exception {
		/*The factor is evaluated, it does need to now about other assigned variables(arg[0]) 
		 * but should not take part in the associativity order for the termnode (arg[1] and arg[2])
		 */
		double value = (double)factor.evaluate(new Object[] {args[0], null, null});
		
		if (args[1] != null) {
			/*If this termnode was preceded by a operator and a factor we 
			 * first have to calculate the preceding factor together with this factor
			 */
			if ((Token)args[2] == Token.MULT_OP) {
				value = (double)args[1] * value;
			} else {
				value = (double)args[1] / value;
			}
		}
		
		if (operation != null) {
			/*If the factor is succeeded by a operator and a term we should save the value and operator 
			 * to be calculated together with the next term
			 */
			args[1] = value;
			args[2] = operation.token();
			return term.evaluate(args);
		} else {
			//This is the last factor of this term-chain, 
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
		factor.buildString(builder, tabs);
		if (operation != null) {
			builder.append("\t".repeat(tabs) + operation + "\n");
			term.buildString(builder, tabs);
		}

	}

}
