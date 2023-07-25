
public class Token {
    public static final byte IDENTIFIER = 0, INTLITERAL = 1, FLOATLITERAL = 2, BOOLLITERAL = 3,
    		OP_AD = 4, OP_MUL = 5, OP_REL = 6, BEGIN = 7, ELSE = 8, END = 9, IF = 10, 
    		THEN = 11, VAR = 12, INTEGER_TYPE = 13, REAL_TYPE = 14, BOOLEAN_TYPE = 15, PROGRAM = 16,
            DO = 17, WHILE = 18, COMMA = 19, SEMICOLON = 20, COLON = 21, BECOMES = 22, LPAREN = 23,
            RPAREN = 24, EOT = 25;
    public static final byte LEXICALERROR = 26;
    public static final byte TYPEERROR = 27;

    private final static String[] spellings = {
            "<identifier>", "<integer-literal>","<float-literal>", "<bool-literal>",
            "<op-ad>", "<op-mul>", "<op-rel>", "begin", "else", "end", "if", 
            "then", "var", "integer", "real", "boolean", "program", "do", "while",
            ",", ";", ":", ":=", "(", ")", "<eot>", "<lexical-error>", "<type-error>"
    };

    private byte kind;
    private String spelling;

    public Token(byte kind, String spelling) {
        this.kind = kind;
        this.spelling = spelling;

		

        if (kind == IDENTIFIER) {
            for (byte k = BEGIN; k <= WHILE; k++) {
                if (spelling.equals(spellings[k])) {
                    this.kind = k;
                    break;
                }
            }
        }

        this.spelling = spellings[this.kind];
    }

    public byte getKind() {
        return kind;
    }

    public String getSpelling() {
        return spelling;
    }
}
