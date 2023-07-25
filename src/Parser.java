
public class Parser 
{
	private Token currentToken;
	private Scanner scanner;
	private int position = 0;
	private boolean programPassedWithoutErrors = true;
	
	public void parse(Scanner scanner) {
		this.scanner = scanner;
		currentToken = scanner.lexicToken.get(0);
		parseProgram();
		if (currentToken.getKind() != Token.EOT)
			System.out.println("\n\nERRO PARSER! Não chegou no final do arquivo!"
								+ "\nToken: " + this.scanner.lexicToken.get(position).getSpelling()
								+ "\nLinha:" + this.scanner.lexicLine.get(position)
								+ "\nColuna: " + this.scanner.lexicColumn.get(position)
								);
		else if (programPassedWithoutErrors)
			System.out.println("Passou no Parser!");
	}
	
	private void accept(byte expectedKind) {
		if (this.scanner.lexicToken.size() > position) {
			
			if (currentToken.getKind() == expectedKind) {
				position++;
				currentToken = this.scanner.lexicToken.get(position);
			} else {
				programPassedWithoutErrors = false;
				if (expectedKind == Token.TYPEERROR)
					System.out.println("\nERRO PARSER! Declaração de tipo inválida!"
										+ "\nToken: " + this.scanner.lexicToken.get(position).getSpelling()
										+ "\nLinha:" + this.scanner.lexicLine.get(position)
										+ "\nColuna: " + this.scanner.lexicColumn.get(position)
										);
				else
					System.out.println("\nERRO! \nToken: " + this.scanner.lexicToken.get(position).getSpelling()
											+ "\nLinha:" + this.scanner.lexicLine.get(position)
											+ "\nColuna: " + this.scanner.lexicColumn.get(position)
										);
				System.exit(1);
				position++;
				currentToken = this.scanner.lexicToken.get(position);
				accept(expectedKind);
			}
		}
	}
	
//	private void acceptIt() {
//		currentToken = this.scanner.scan();
//	}
	
	private void parseProgram() {
		accept(Token.PROGRAM);
		accept(Token.IDENTIFIER);
		accept(Token.SEMICOLON);
		parseBody();
	}
	
	private void parseBody() {
		parseDeclarations();
		parseCompositeCommand();
	}
	
	private void parseDeclarations () {
		while (currentToken.getKind() != Token.BEGIN) {
			accept(Token.VAR);
			parseIdList();
			accept(Token.COLON);
			parseType();
			accept(Token.SEMICOLON);
		}
	}
	
	private void parseIdList() {
		accept(Token.IDENTIFIER);
		while (currentToken.getKind() == Token.COMMA) {
			accept(Token.COMMA);
			accept(Token.IDENTIFIER);
		}
	}
	
	private void parseType() {
		if (currentToken.getKind() == Token.INTEGER_TYPE)
			accept(Token.INTEGER_TYPE);
		else if (currentToken.getKind() == Token.REAL_TYPE)
			accept(Token.REAL_TYPE);
		else if (currentToken.getKind() == Token.BOOLEAN_TYPE)
			accept(Token.BOOLEAN_TYPE);
		else 
			accept(Token.TYPEERROR);
	}
	
	private void parseCompositeCommand() {
		accept(Token.BEGIN);
		parseCompositeCommandList();
		accept(Token.END);
	}
	
	private void parseCompositeCommandList() {
		while (currentToken.getKind() != Token.END) {
			parseCommand();
			accept(Token.SEMICOLON);
		}
		
	}
	
	private void parseCommand() {
		if (currentToken.getKind() == Token.IF)
			parseConditional();
		else if (currentToken.getKind() == Token.WHILE)
			parseIterative();
		else if (currentToken.getKind() == Token.BEGIN)
			parseCompositeCommand();
		else
			parseAssignment();
	}
	
	private void parseConditional() {
		accept(Token.IF);
		parseExpression();
		accept(Token.THEN);
		parseCommand();
		if (currentToken.getKind() == Token.ELSE) {
			parseCommand();
		}
	}
	
	private void parseIterative() {
		accept(Token.WHILE);
		parseExpression();
		accept(Token.DO);
		parseCommand();
	}
	
	private void parseAssignment() {
		accept(Token.IDENTIFIER);
		accept(Token.BECOMES);
		parseExpression();
	}
	
	
	private void parseExpression() {
		parseSimpleExpression();
		while (currentToken.getKind() == Token.OP_REL) {
			parseOpRel();
			parseSimpleExpression();
		}
	}
	
	private void parseSimpleExpression() {
		parseTerm();
		while (currentToken.getKind() == Token.OP_AD) {
			parseOpAd();
			parseTerm();
		}
	}
	
	private void parseTerm() {
		parseFactor();
		while(currentToken.getKind() == Token.OP_MUL) {
			parseOpMul();
			parseFactor();
		}
	}
	
	private void parseFactor() {
		if (currentToken.getKind() == Token.INTLITERAL)
			parseIntLiteral();
		else if (currentToken.getKind() == Token.FLOATLITERAL)
			parseFloatLiteral();
		else if (currentToken.getKind() == Token.BOOLLITERAL)
			parseBoolLiteral();
		else if (currentToken.getKind() == Token.LPAREN) {
			accept(Token.LPAREN);
			parseExpression();
			accept(Token.RPAREN);
		} else
			accept(Token.IDENTIFIER);
	}
	
	private void parseOpRel() {
		accept(Token.OP_REL);
	}
	
	private void parseOpAd() {
		accept(Token.OP_AD);
	}
	
	private void parseOpMul() {
		accept(Token.OP_MUL);
	}
	
	private void parseIntLiteral() {
		accept(Token.INTLITERAL);
	}
	
	private void parseFloatLiteral() {
		accept(Token.FLOATLITERAL);
	}
	
	private void parseBoolLiteral() {
		accept(Token.BOOLLITERAL);
	}
}
