
public class nodeTerm {
	nodeFactor factor;
	nodeOpMul opMul;
	nodeTerm next;
	
	public void visit (Visitor v) {
		v.visitTerm(this);
	}
}
