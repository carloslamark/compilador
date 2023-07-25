
public class nodeBody {
	nodeDeclarations declarations;
	nodeCompositeCommand compositeComand;
	
	public void visit (Visitor v) {
		v.visitBody(this);
	}
}
