
public class nodeDeclarations {
	Token var;
	nodeIdList idList;
	Token colon;
	nodeType type;
	Token semicolon;
	nodeDeclarations next;
	
	public void visit (Visitor v) {
		v.visitDeclarations(this);
	}
}
