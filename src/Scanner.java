import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Scanner 
{
    // StringBuffer é uma classe do Java que permite deixar a string mutável, sem ter muitos problemas
    private static String fileContent = "";
    private static String completeLexicFile = "";
    private static char currentChar;
    private byte currentKind;
    private StringBuffer currentSpelling;
    private int charPosition = 0;
    private static int line = 0;
    private static int column = -1;
    public ArrayList<Token> lexicToken = new ArrayList<>();
    public ArrayList<Integer> lexicColumn = new ArrayList<>();
    public ArrayList<Integer> lexicLine = new ArrayList<>();

    public Scanner initScan() {
        Scanner scanner = new Scanner();
        

        System.out.println("---> Iniciando analise lexica");
        scanner.readFileContent("arquivo-com-o-texto.txt");
        Token token;
        do {
            token = scanner.scan();
            scanner.saveListPositions(token);
            completeLexicFile += token.getSpelling() + '\n' + "Coluna: " + column + '\n' + "Linha: " + line +"\n\n";
        } while (token.getKind() != Token.EOT || token.getKind() == Token.LEXICALERROR);
        if (token.getKind() == Token.LEXICALERROR) {
        	System.out.println("\n\nERRO LEXICO!\n" + token.getSpelling()
					        	+ '\n' + "Coluna: " + column 
					        	+ '\n' + "Linha: " + line +"\n\n"
					        	);
        	System.exit(3);
        }
        System.out.println("Tudo certo!");
        scanner.saveContentInFile("saida-do-lexico.txt");
        
        return scanner;
    }

    private void readFileContent(String fileName) {
        try {
            File txtFile = new File(fileName);
            FileReader fileReader = new FileReader(txtFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContent += line + '\n';
            }
            currentChar = fileContent.charAt(0);
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void saveContentInFile(String fileName) {
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
    		writer.write(completeLexicFile);
    		System.out.println("Conteúdo salvo no arquivo com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void takeIt() {
        currentSpelling.append(currentChar);
        charPosition++;
        if (charPosition < fileContent.length()) {
            currentChar = fileContent.charAt(charPosition);
            column++;
        } else {
            currentChar = '\000'; // Fim do arquivo
        }
    }
    
    private void take() {
        charPosition++;
        if (charPosition < fileContent.length()) {
            currentChar = fileContent.charAt(charPosition);
            column++;
        } else {
            currentChar = '\000'; // Fim do arquivo
        }
    }

    private byte scanToken() {
        if (isLetter(currentChar)) {
            // Regra do (letter(letter|digit)*) com operador booleano
        	if (
        			isBoolFalse() 
        			|| isBoolTrue() 
        			&& !isLetter(currentChar) 
        			&& !isDigit(currentChar)
        		) return Token.BOOLLITERAL;
        	
            while (isLetter(currentChar) || isDigit(currentChar)) {
                takeIt();
            }
            
            return Token.IDENTIFIER;
        } else if (isDigit(currentChar)) {
            // Regra do (digit(digit)*)
            while (isDigit(currentChar)) {
                takeIt();
            }
            if (currentChar == '.') {
            	takeIt();
            	
            	while (isDigit(currentChar)) {
                    takeIt();
                }
            	return Token.FLOATLITERAL;
            	
            } else
            	return Token.INTLITERAL;
        } else {
            switch (currentChar) {
                case '+':
                case '-': {
                    // Regra dos operadores de adição
                    takeIt();
                    return Token.OP_AD;
                }
                case '*':
                case '/': {
                	// regra dos operadores de multiplicação
                	takeIt();
                	return Token.OP_MUL;
                }
                case '<': {
                	// regra dos operadores booleanos
                	takeIt();
                	if (currentChar == '>' || currentChar == '=')
                		takeIt();
                	return Token.OP_REL;
                }
                case '>': {
                	// regra dos operadores booleanos
                	takeIt();
                	if (currentChar == '=')
                		takeIt();
                	return Token.OP_REL;
                }
                case '=': {
                	// regra dos operadores booleanos
                	takeIt();
                	return Token.OP_REL;
                }
                case ';': {
                    // Regra do ponto e vírgula
                    takeIt();
                    return Token.SEMICOLON;
                }
                case '.': {
                    // Regra do float e do ponto
                    takeIt();
                    
                    if(isDigit(currentChar)) {
                    	while (isDigit(currentChar)) {
                            takeIt();
                        }
                    	return Token.FLOATLITERAL;
                    }
                    
                    return Token.DOT;
                }
                case ':': {
                    // Regra dos dois pontos ou da atribuição
                    takeIt();
                    
                    if (currentChar == '=') {
                        takeIt();
                        return Token.BECOMES;
                    }
                    return Token.COLON;
                }
                case '(': {
                    takeIt();
                    return Token.LPAREN;
                }
                case ')': {
                    takeIt();
                    return Token.RPAREN;
                }
                case ',': {
                	takeIt();
                	return Token.COMMA;
                }
                case '\000': {
                    return Token.EOT;
                }
                default: {
                    System.out.println("Erro: caractere desconhecido encontrado -> " + currentChar);
                    System.exit(0);
                    return Token.LEXICALERROR;
                }
            }
        }
    }

    private void scanSeparator() {
        // Casos para o compilador ignorar
        switch (currentChar) {
            case '#': {
                // Regra dos comentários
                take();
                while (isGraphic(currentChar)) {
                	take();
                }
                take();
                column = 0;
                line++;
                break;
            }
            case ' ': {
                // Regra do espaço e da quebra de linha
                take();
                column++;
                break;
            }
            case '\n': {
                // Regra do espaço e da quebra de linha
            	take();
                line++;
                column = 0;
                break;
            }
            default:
            	break;
        }
    }

    public Token scan() {
        while (currentChar == '#' || currentChar == ' ' || currentChar == '\n') {
            scanSeparator();
        }
        currentSpelling = new StringBuffer("");
        currentKind = scanToken();
        
        return new Token(currentKind, currentSpelling.toString());
    }
    
    private void saveListPositions(Token token) {
    	lexicToken.add(token);
        lexicColumn.add(column);
        lexicLine.add(line);
    }

    private boolean isLetter(char c) {
        // Retorna verdadeiro se o caractere atual for uma letra minúscula
        return (c > 96 && c < 123);
    }

    private boolean isDigit(char c) {
        // Retorna verdadeiro se o caractere atual for um dígito entre 0 e 9
        return (c > 47 && c < 58);
    }
    
    private boolean isBoolFalse() {
    	// Retorna verdadeiro se a palavra é um boleano
    	if (currentChar == 'f') {
    		takeIt();
    		if(currentChar == 'a') {
    			takeIt();
    			if (currentChar == 'l') {
    				takeIt();
    				if (currentChar == 's') {
    					takeIt();
    					if (currentChar == 'e') {
    						takeIt();
    						return true;
    					}
    				}
    			}
    		}
    	}
    	return false;
    }
    
    private boolean isBoolTrue() {
    	// Retorna verdadeiro se a palavra é um boleano
    	if (currentChar == 't') {
    		takeIt();
    		if(currentChar == 'r') {
    			takeIt();
    			if (currentChar == 'u') {
    				takeIt();
    				if (currentChar == 'e') {
    					takeIt();
    					return true;
    				}
    			}
    		}
    	}
    	return false;
    }

    private boolean isGraphic(char c) {
        // Retorna verdadeiro se o caractere atual for gráfico, ou seja, estiver entre 32 e 126 na tabela ascii
        return (c > 31 && c < 127);
    }
}
