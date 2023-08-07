
public class nodeCompositeCommandList {
	nodeCommand command;
	Token semicolon;
	nodeCompositeCommandList next;
	
	public void visit (Visitor v) {
		v.visitCompositeCommandList(this);
	}
}
