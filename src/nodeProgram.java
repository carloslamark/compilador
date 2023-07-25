
public class nodeProgram
{
	char program;
	char name;
	char semicolon;
	nodeBody body;
	
	public void visit (Visitor v) {
		v.visitProgram(this);
	}
}
