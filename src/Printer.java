
public class Printer implements Visitor {
	int levelBar = 0;
	
	public void ident() {
		int stop = 0;
		System.out.println("");
		while(stop < levelBar) {
			System.out.print("|");
			stop++;
		}
	}

	public void print (nodeProgram program) {
		System.out.println("---> Iniciando impressao da arvore");
		program.visit(this);
	}

	public void visitProgram(nodeProgram p) {
		if(p != null) {
			if(p.body != null) p.body.visit(this);
		}
	}

	
	public void visitBody(nodeBody b) {
		if(b.declarations != null) {
			System.out.print("\nDeclaracoes:");
			b.declarations.visit(this);
		}
		if(b.compositeCommand != null) {
			System.out.print("\n\nComandos:");
			b.compositeCommand.visit(this);
		}
	}

	
	public void visitDeclarations(nodeDeclarations d) {
		if(d.type != null) {
			if(d.type != null) {
				d.type.visit(this);
			}
			if(d.idList != null) {
				d.idList.visit(this);
				levelBar--;
			}
			if(d.next != null) {
				d.next.visit(this);
			}
		}
	}

	
	public void visitCompositeCommand(nodeCompositeCommand cc) {
		if(cc.compositeCommandList != null) {
			cc.compositeCommandList.visit(this);
		}
	}

	
	public void visitIdList(nodeIdList id) {
		if(id.identifier != null) {
			levelBar++;
			ident();
			System.out.print(id.identifier.getName());
			if(id.next != null)
				id.next.visit(this);
			levelBar--;
		}
	}

	
	public void visitType(nodeType t) {
		levelBar++;
		ident();
		System.out.print(t.type.getName());
	}

	
	public void visitCompositeCommandList(nodeCompositeCommandList ccl) {
		int levelSave;
		levelSave = levelBar;
		if(ccl.command != null) {
			ccl.command.visit(this);
		}
		levelBar = levelSave;
		if(ccl.next != null) {
			ccl.next.visit(this);
		}
	}

	
	public void visitCommand(nodeCommand c) {
		if(c.conditional != null) {
			c.conditional.visit(this);
		}
		else if(c.iterative != null) {
			c.iterative.visit(this);
		}
		else if(c.assignment != null) {
			c.assignment.visit(this);
		}
		else if(c.compositeCommand != null) {
			c.compositeCommand.visit(this);
		}
	}

	
	public void visitConditional(nodeConditional c) {
		int levelSave;
		levelSave = levelBar;
		if(c.ifToken != null) {
			levelBar++;
			ident();
			System.out.print(c.ifToken.getName());
			c.expression.visit(this);
		}
		levelBar = levelSave;
		if(c.thenToken != null) {
			levelBar++;
			ident();
			System.out.print(c.thenToken.getName());
			c.commandIf.visit(this);
		}
		levelBar = levelSave;
		if(c.elseToken != null) {
			levelBar++;
			ident();
			System.out.print(c.elseToken.getName());
			c.commandElse.visit(this);
		}
		levelBar = levelSave;
	}

	
	public void visitIterative(nodeIterative i) {
		if(i.whileToken != null) {
			levelBar++;
			ident();
			System.out.print(i.whileToken.getName());
			i.expression.visit(this);
		}
		if(i.doToken != null) {
			ident();
			System.out.print(i.doToken.getName());
			i.command.visit(this);
		}
	}

	
	public void visitAssignment(nodeAssignment a) {
		if(a.identifier != null) {
			levelBar++;
			ident();
			System.out.print(a.identifier.getName());
			if(a.expression != null) {
				levelBar++;
				a.expression.visit(this);
			}
			levelBar--;
		}
	}

	
	public void visitExpression(nodeExpression e) {
		if(e.simpleExpression != null) {
			if(e.opRel != null) {
				levelBar++;
				e.opRel.visit(this);
				levelBar++;
			}
			e.simpleExpression.visit(this);
			if(e.next != null) {
				e.next.visit(this);
				levelBar-=2;
			}
		}
	}

	
	public void visitSimpleExpression(nodeSimpleExpression se) {
		if(se.term != null) {
			if(se.opAd != null) {
				se.opAd.visit(this);
				levelBar++;
			}
			se.term.visit(this);
			if(se.next != null) {
				se.next.visit(this);
			}
		}
	}

	
	public void visitTerm(nodeTerm t) {
		if(t.factor != null) {
			if(t.opMul != null) {
				t.opMul.visit(this);
				levelBar++;
			}
			t.factor.visit(this);
			if(t.next != null) {
				t.next.visit(this);
			}
		}
	}

	
	public void visitFactor(nodeFactor f) {
		int levelSave;
		if(f.intLiteral != null) {
			f.intLiteral.visit(this);
		}
		else if(f.floatLiteral != null) {
			f.floatLiteral.visit(this);
		}
		else if(f.boolLiteral != null) {
			f.boolLiteral.visit(this);
		}
		else if(f.lparen != null && f.rparen!= null) {
			levelSave = levelBar;
			f.expression.visit(this);
			levelBar = levelSave;
		}
		else if(f.identifier != null) {
			ident();
			System.out.print(f.identifier.getName());
		}
	}

	
	public void visitOpRel(nodeOpRel or) {
		if(or.opRel != null) {
			ident();
			System.out.print(or.opRel.getName());
		}
	}

	
	public void visitOpAd(nodeOpAd oa) {
		if(oa.opAd != null) {
			ident();
			System.out.print(oa.opAd.getName());
		}
	}

	
	public void visitOpMul(nodeOpMul om) {
		if(om.opMul != null) {
			ident();
			System.out.print(om.opMul.getName());
		}
	}

	
	public void visitIntLiteral(nodeIntLiteral il) {
		if(il.intLiteral != null) {
			ident();
			System.out.print(il.intLiteral.getName());
		}
	}

	
	public void visitFloatLiteral(nodeFloatLiteral fl) {
		if(fl.floatLiteral != null) {
			ident();
			System.out.print(fl.floatLiteral.getName());
		}
	}

	
	public void visitBoolLiteral(nodeBoolLiteral bl) {
		if(bl.boolLiteral != null) {
			ident();
			System.out.print(bl.boolLiteral.getName());
		}
	}
}
