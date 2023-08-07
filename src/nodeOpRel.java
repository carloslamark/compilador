
public class nodeOpRel {
	Token opRel;
	
	public void visit (Visitor v) {
		v.visitOpRel(this);
	}
}
