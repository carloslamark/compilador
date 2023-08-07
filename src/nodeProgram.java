
public class nodeProgram
{
	Token program;
	Token identifier;
	Token semicolon;
	nodeBody body;
	
	public void visit (Visitor v) {
		v.visitProgram(this);
	}
}
