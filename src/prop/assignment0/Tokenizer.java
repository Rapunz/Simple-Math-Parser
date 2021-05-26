package prop.assignment0;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Tokenizer implements ITokenizer {
	
	private static Map<Character, Token> symbols = null;
	
	private Scanner scanner = null;
	private Lexeme current = null;
	private Lexeme next = null;
	
	public Tokenizer() {

		symbols = new HashMap<Character, Token>();
		symbols.put('+', Token.ADD_OP);
		symbols.put('-', Token.SUB_OP);
		symbols.put('*', Token.MULT_OP);
		symbols.put('/', Token.DIV_OP);
		symbols.put('=', Token.ASSIGN_OP);
		symbols.put('(', Token.LEFT_PAREN);
		symbols.put(')', Token.RIGHT_PAREN);
		symbols.put(';', Token.SEMICOLON);
		symbols.put('{', Token.LEFT_CURLY);
		symbols.put('}', Token.RIGHT_CURLY);
		symbols.put(Scanner.EOF, Token.EOF);
		
	}

	/*The methods open, close, current, moveNext and consumeWhiteSpace 
	 * are more or less copied from code for PE1 as I see no reason to change them
	 */
	@Override
	public void open(String fileName) throws IOException, TokenizerException {
		scanner = new Scanner();
		scanner.open(fileName);
		scanner.moveNext();
		next = extractLexeme();
	}
	
	@Override
	public void close() throws IOException {
		if (scanner != null) {
			scanner.close();
		}
	}

	@Override
	public Lexeme current() {
		return current;
	}

	@Override
	public void moveNext() throws IOException, TokenizerException {
		if (scanner == null) {
			throw new IOException("No open file.");
		}
		current = next;
		if (next.token() != Token.EOF) {
			next = extractLexeme();
		}
	}
	
	private void consumeWhiteSpaces() throws IOException {
		while (Character.isWhitespace(scanner.current())) {
			scanner.moveNext();
		}
	}

	private Lexeme extractLexeme() throws IOException, TokenizerException {
		consumeWhiteSpaces();
		Character ch = scanner.current();

		if (ch == Scanner.EOF) {
			return new Lexeme(ch, Token.EOF);
		} else if ((ch >= 'a' && ch <= 'z')) {
			return extractId();
		} else if (ch >= '0' && ch <= '9') {
			return extractInt();
		} else if (symbols.containsKey(ch)) {
			scanner.moveNext();
			return new Lexeme(ch, symbols.get(ch));
		} else {
			throw new TokenizerException("Unknown character: " + String.valueOf(ch));
		}
	}

	private Lexeme extractInt() throws IOException {
		Character ch = scanner.current();
		StringBuilder strBuilder = new StringBuilder();
		while (ch >= '0' && ch <= '9') {
			strBuilder.append(scanner.current());
			scanner.moveNext();
			ch=scanner.current();
		}
		String intNumber = strBuilder.toString();
		return new Lexeme(Double.parseDouble(intNumber), Token.INT_LIT);
	}

	private Lexeme extractId() throws IOException {
		Character ch = scanner.current();
		StringBuilder strBuilder = new StringBuilder();
		while (ch >= 'a' && ch <= 'z') {
			strBuilder.append(ch);
			scanner.moveNext();
			ch=scanner.current();
		}
		String identifier = strBuilder.toString();
		return new Lexeme(identifier, Token.IDENT);
	}


	

}
