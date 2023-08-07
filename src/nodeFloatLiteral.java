
public class nodeFloatLiteral {
	Token floatLiteral;
	
	public void visit (Visitor v) {
		v.visitFloatLiteral(this);
	}
}
