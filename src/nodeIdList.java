
public class nodeIdList {
	Token identifier;
	nodeIdList next;
	
	public void visit (Visitor v) {
		v.visitIdList(this);
	}
}
