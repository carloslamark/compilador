
public class nodeFactor {
	nodeIntLiteral intLiteral;
	nodeFloatLiteral floatLiteral;
	nodeBoolLiteral boolLiteral;
	Token lparen;
	nodeExpression expression;
	Token rparen;
	Token identifier;
	
	
	public void visit (Visitor v) {
		v.visitFactor(this);
	}
}
