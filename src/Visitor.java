
public interface Visitor
{
	public void visitProgram (nodeProgram p);
	public void visitBody (nodeBody b);
	public void visitDeclarations (nodeDeclarations d);
	public void visitCompositeCommand (nodeCompositeCommand cc);
	public void visitIdList (nodeIdList id);
	public void visitType (nodeType t);
	public void visitCompositeCommandList (nodeCompositeCommandList cc);
	public void visitCommand (nodeCommand c);
	public void visitConditional (nodeConditional c);
	public void visitIterative (nodeIterative i);
	public void visitAssignment (nodeAssignment a);
	public void visitExpression (nodeExpression e);
	public void visitSimpleExpression (nodeSimpleExpression se);
	public void visitTerm (nodeTerm t);
	public void visitFactor (nodeFactor f);
	public void visitOpRel (nodeOpRel or);
	public void visitOpAd (nodeOpAd oa);
	public void visitOpMul (nodeOpMul om);
	public void visitIntLiteral (nodeIntLiteral il);
	public void visitFloatLiteral (nodeFloatLiteral fl);
	public void visitBoolLiteral (nodeBoolLiteral bl);
}