
public class Parser {
	private Token currentToken;
	private Scanner scanner;
	private int position = 0;
	public nodeProgram parse(Scanner scanner) {
		this.scanner = scanner;
		currentToken = scanner.lexicToken.get(0);
		System.out.println();
		System.out.println("---> Iniciando analise sintatica");
		return parseProgram();
	}

	private void accept(byte expectedKind) {
		if (this.scanner.lexicToken.size() > position) {

			if (currentToken.getKind() == expectedKind) {
				position++;
				currentToken = this.scanner.lexicToken.get(position);
			} else {
				if (expectedKind == Token.TYPEERROR)
					System.out.println("\nERRO PARSER! Declaração de tipo inválida!" + "\nToken: "
							+ this.scanner.lexicToken.get(position).getSpelling() + "\nLinha:"
							+ this.scanner.lexicLine.get(position) + "\nColuna: "
							+ this.scanner.lexicColumn.get(position));
				else
					System.out.println("\nERRO! \nToken: " + this.scanner.lexicToken.get(position).getSpelling()
							+ "\nLinha:" + this.scanner.lexicLine.get(position) + "\nColuna: "
							+ this.scanner.lexicColumn.get(position));
				System.exit(1);
			}
		}
	}

	nodeProgram parseProgram() {
		nodeProgram p = new nodeProgram();
		p.program = currentToken;
		accept(Token.PROGRAM);
		p.identifier = currentToken;
		accept(Token.IDENTIFIER);
		p.semicolon = currentToken;
		accept(Token.SEMICOLON);
		p.body = parseBody();
		return p;
	}

	nodeBody parseBody() {
		nodeBody b = new nodeBody();
		b.declarations = parseDeclarations();
		b.compositeCommand = parseCompositeCommand();
		b.dot = currentToken;
		accept(Token.DOT);
		return b;
	}

	nodeDeclarations parseDeclarations() {
		nodeDeclarations d;
		d = new nodeDeclarations();
		d.var = currentToken;
		accept(Token.VAR);
		d.idList = parseIdList();
		d.colon = currentToken;
		accept(Token.COLON);
		d.type = parseType();
		d.semicolon = currentToken;
		accept(Token.SEMICOLON);

		if (currentToken.getKind() != Token.BEGIN) {
			d.next = parseDeclarations();
		}
		
		return d;
	}

	nodeIdList parseIdList() {
		nodeIdList id;
		id = new nodeIdList();
		id.identifier = currentToken;
		accept(Token.IDENTIFIER);

		if (currentToken.getKind() == Token.COMMA) {
			accept(Token.COMMA);
			id.next = parseIdList();
		}
		return id;
	}

	nodeType parseType() {
		nodeType t = new nodeType();
		t.type = currentToken;
		if (currentToken.getKind() == Token.INTEGER_TYPE) 
			accept(Token.INTEGER_TYPE);
		else if (currentToken.getKind() == Token.REAL_TYPE)
			accept(Token.REAL_TYPE);
		else if (currentToken.getKind() == Token.BOOLEAN_TYPE)
			accept(Token.BOOLEAN_TYPE);
		else
			accept(Token.TYPEERROR);
		return t;
	}

	nodeCompositeCommand parseCompositeCommand() {
		nodeCompositeCommand cc =  new nodeCompositeCommand();
		cc.begin = currentToken;
		accept(Token.BEGIN);
		cc.compositeCommandList = parseCompositeCommandList();
		cc.end = currentToken;
		accept(Token.END);
		
		return cc;
	}

	nodeCompositeCommandList parseCompositeCommandList() {
		nodeCompositeCommandList ccl;
		ccl = new nodeCompositeCommandList();
		
		ccl.command = parseCommand();
		ccl.semicolon = currentToken;
		accept(Token.SEMICOLON);
		if (currentToken.getKind() != Token.END) {
			ccl.next = parseCompositeCommandList();
		}
		
		return ccl;

	}

	nodeCommand parseCommand() {
		nodeCommand c = new nodeCommand();
		
		if (currentToken.getKind() == Token.IF) 
			c.conditional = parseConditional();
		else if (currentToken.getKind() == Token.WHILE)
			c.iterative = parseIterative();
		else if (currentToken.getKind() == Token.BEGIN)
			c.compositeCommand = parseCompositeCommand();
		else
			c.assignment = parseAssignment();
		
		return c;
	}

	nodeConditional parseConditional() {
		nodeConditional c = new nodeConditional();
		c.ifToken = currentToken;
		accept(Token.IF);
		c.expression = parseExpression();
		c.thenToken = currentToken;
		accept(Token.THEN);
		c.commandIf = parseCommand();
		if (currentToken.getKind() == Token.ELSE) {
			c.elseToken = currentToken;
			accept(Token.ELSE);
			c.commandElse = parseCommand();
		}		
		
		return c;
	}

	nodeIterative parseIterative() {
		nodeIterative i = new nodeIterative();
		
		i.whileToken = currentToken;
		accept(Token.WHILE);
		i.expression = parseExpression();
		i.doToken = currentToken;
		accept(Token.DO);
		i.command = parseCommand();
		
		return i;
	}

	nodeAssignment parseAssignment() {
		nodeAssignment a = new nodeAssignment();
		
		a.identifier = currentToken;
		accept(Token.IDENTIFIER);
		a.becomes = currentToken;
		accept(Token.BECOMES);
		a.expression = parseExpression();
		return a;
	}

	nodeExpression parseExpression() {
		nodeExpression e;
		e = new nodeExpression();
		
		e.simpleExpression = parseSimpleExpression();
		if (currentToken.getKind() == Token.OP_REL) {
			e.opRel = parseOpRel();
			e.next = parseExpression();
		}
		return e;
	}

	nodeSimpleExpression parseSimpleExpression() {
		nodeSimpleExpression se;
		se = new nodeSimpleExpression();
		
		se.term = parseTerm();
		if (currentToken.getKind() == Token.OP_AD) {
			se.opAd = parseOpAd();
			se.next = parseSimpleExpression();
		}
		return se;
	}

	nodeTerm parseTerm() {
		nodeTerm t;
		t = new nodeTerm();
		
		t.factor = parseFactor();
		if (currentToken.getKind() == Token.OP_MUL) {
			t.opMul = parseOpMul();
			t.next = parseTerm();
		}
		return t;
	}

	nodeFactor parseFactor() {
		nodeFactor f = new nodeFactor();
		
		if (currentToken.getKind() == Token.INTLITERAL)
			f.intLiteral = parseIntLiteral();
		else if (currentToken.getKind() == Token.FLOATLITERAL)
			f.floatLiteral = parseFloatLiteral();
		else if (currentToken.getKind() == Token.BOOLLITERAL)
			f.boolLiteral = parseBoolLiteral();
		else if (currentToken.getKind() == Token.LPAREN) {
			f.lparen = currentToken;
			accept(Token.LPAREN);
			f.expression = parseExpression();
			f.rparen = currentToken;
			accept(Token.RPAREN);
		} else {
			f.identifier = currentToken;
			accept(Token.IDENTIFIER);
		}
		return f;
	}

	nodeOpRel parseOpRel() {
		nodeOpRel or = new nodeOpRel();
		or.opRel = currentToken;
		accept(Token.OP_REL);
		return or;
	}

	nodeOpAd parseOpAd() {
		nodeOpAd oa = new nodeOpAd();
		oa.opAd = currentToken;
		accept(Token.OP_AD);
		return oa;
	}

	nodeOpMul parseOpMul() {
		nodeOpMul om = new nodeOpMul();
		om.opMul = currentToken;
		accept(Token.OP_MUL);
		return om;
	}

	nodeIntLiteral parseIntLiteral() {
		nodeIntLiteral il= new nodeIntLiteral();
		il.intLiteral = currentToken;
		accept(Token.INTLITERAL);
		return il;
	}

	nodeFloatLiteral parseFloatLiteral() {
		nodeFloatLiteral fl = new nodeFloatLiteral();
		fl.floatLiteral = currentToken;
		accept(Token.FLOATLITERAL);
		return fl;
	}

	nodeBoolLiteral parseBoolLiteral() {
		nodeBoolLiteral bl = new nodeBoolLiteral();
		bl.boolLiteral = currentToken;
		accept(Token.BOOLLITERAL);
		return bl;
	}
}
