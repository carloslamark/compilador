
public class nodeIntLiteral {
	Token intLiteral;
	
	public void visit (Visitor v) {
		v.visitIntLiteral(this);
	}
}
