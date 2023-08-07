
public class nodeBody {
	nodeDeclarations declarations;
	nodeCompositeCommand compositeCommand;
	Token dot;
	
	public void visit (Visitor v) {
		v.visitBody(this);
	}
}
