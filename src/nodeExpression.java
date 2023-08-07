
public class nodeExpression {
	nodeSimpleExpression simpleExpression;
	nodeOpRel opRel;
	nodeExpression next;
	
	public void visit (Visitor v) {
		v.visitExpression(this);
	}
}
