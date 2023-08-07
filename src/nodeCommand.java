
public class nodeCommand {
	nodeConditional conditional;
	nodeIterative iterative;
	nodeCompositeCommand compositeCommand;
	nodeAssignment assignment;
	
	public void visit (Visitor v) {
		v.visitCommand(this);
	}
}
