
public class nodeType {
	Token type;
	
	public void visit (Visitor v) {
		v.visitType(this);
	}
}
