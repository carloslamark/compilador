
public class nodeAssignment {
	Token identifier;
	Token becomes;
	nodeExpression expression;
	
	public void visit (Visitor v) {
		v.visitAssignment(this);
	}
}
