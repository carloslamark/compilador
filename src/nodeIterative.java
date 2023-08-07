
public class nodeIterative {
	Token whileToken;
	nodeExpression expression;
	Token doToken;
	nodeCommand command;
	
	public void visit (Visitor v) {
		v.visitIterative(this);
	}
}
