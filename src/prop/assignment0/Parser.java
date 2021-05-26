package prop.assignment0;

import java.io.IOException;


public class Parser implements IParser {
	
	/*This class is more or less copied from PE1 because the basic behaviour 
	 * of this parser is more or less the same
	 */
	
	private Tokenizer tokenizer = null;

	@Override
	public void open(String fileName) throws IOException, TokenizerException {
		tokenizer = new Tokenizer();
		tokenizer.open(fileName);
		tokenizer.moveNext();
	}

	@Override
	public INode parse() throws IOException, TokenizerException, ParserException {
		if (tokenizer == null) {
			throw new IOException("No open file.");
		}	
		return new BlockNode(tokenizer);
	}

	@Override
	public void close() throws IOException {
		if (tokenizer != null) {
			tokenizer.close();
		}
	}
	
}
