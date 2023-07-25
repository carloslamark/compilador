
public class Visitor
{
	public void visitProgram (nodeProgram p);
	public void visitBody (nodeBody b);
	public void visitDeclarations (nodeDeclarations d);
	public void visitCompositeCommand (nodeCompositeCommand cc);
}
