
public class nodeCompositeCommand {
	Token begin;
	nodeCompositeCommandList compositeCommandList;
	Token end;
	
	public void visit (Visitor v) {
		v.visitCompositeCommand(this);
	}
}
