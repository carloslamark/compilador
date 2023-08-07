
public class nodeSimpleExpression {
	nodeTerm term;
	nodeOpAd opAd;
	nodeSimpleExpression next;
	
	public void visit (Visitor v) {
		v.visitSimpleExpression(this);
	}
}
