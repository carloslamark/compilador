
public class Compiler 
{
	public static void main(String[] args) {
		nodeProgram program;
		Scanner scanner = new Scanner();
		Parser parser = new Parser();
		Printer printer = new Printer();
//		Checker checker = new Checker();
//		Coder coder = new Coder();
		program = parser.parse(scanner.initScan());
        System.out.println("Tudo certo!");
		printer.print(program);
//		checker.check(program);
//		coder.code(program);
	}
}
 