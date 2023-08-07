
public class nodeConditional {
	Token ifToken;
	nodeExpression expression;
	Token thenToken;
	nodeCommand commandIf;
	Token elseToken;
	nodeCommand commandElse;
	
	public void visit (Visitor v) {
		v.visitConditional(this);
	}
}
