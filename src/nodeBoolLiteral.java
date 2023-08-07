
public class nodeBoolLiteral {
	Token boolLiteral;
	
	public void visit (Visitor v) {
		v.visitBoolLiteral(this);
	}
}
