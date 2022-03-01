package classes;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AnalisadorLexico {
    private BufferedReader codigoFonte;
    private String linha;
    private String caractere;
    private String palavra = "";
    private final List delimitadores = new ArrayList();
    private boolean inComment = false;
    private String inteiro;
    private String real;
    private String identificador;
    private final List operAritimetico = new ArrayList();
    private final List operRelacionais = new ArrayList();
    private final List reservadas = new ArrayList();
    private final List<Token> tokens = new ArrayList();
    private String nomeArquivo;
    
    public AnalisadorLexico(String pathFile){
        try {
            int i = 10; 
            this.nomeArquivo = pathFile;
            real = ("[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?");
            inteiro = ("[+-]?[0-9][0-9]*");
            identificador = ("^\\D\\w+|^\\D\\w?$");
            delimitadores.add(" ");
            delimitadores.add(",");
            delimitadores.add(";");
            delimitadores.add(":");
            delimitadores.add("(");
            delimitadores.add(")");
            delimitadores.add("\n");
            delimitadores.add("\\n");
            delimitadores.add("\r");
            delimitadores.add("{");
            delimitadores.add("}");
            
            operAritimetico.add("*");
            operAritimetico.add("/");
            operAritimetico.add("+");
            operAritimetico.add("-");
            
            operRelacionais.add("<");
            operRelacionais.add(">");
            operRelacionais.add("<=");
            operRelacionais.add(">=");
            operRelacionais.add("=");
            operRelacionais.add("<>");
            operRelacionais.add("E");
            operRelacionais.add("OU");
            
            reservadas.add("DECLARACOES");
            reservadas.add("ALGORITMO");
            reservadas.add("INTEIRO");
            reservadas.add("REAL");
            reservadas.add("ATRIBUIR");
            reservadas.add("A");
            reservadas.add("LER");
            reservadas.add("IMPRIMIR");
            reservadas.add(":");
            reservadas.add("SE");
            reservadas.add("ENTAO");
            reservadas.add("ENQUANTO");
            reservadas.add("INICIO");
            reservadas.add("FIM");
                 
            codigoFonte = new BufferedReader(new FileReader(pathFile));
        } catch (FileNotFoundException ex) {
            System.err.println("Arquivo não encontrado");
        }
    }
    
    public void analisar() throws IOException{
        int nLine = 0;
        while (true){
            nLine++;

            linha = codigoFonte.readLine();
            if (linha == null)
                break;
            
            int size = linha.length();
            linha = linha.split("\n")[0];
            caractere = "";

            for (int i = 0; i < size;i++){
                caractere = linha.substring(i,i+1);
                if (delimitadores.contains(caractere)){
                     if ((!inComment) && (palavra.length() >= 2) && (palavra.substring(0,2).equals(
                        "%"))){
                        palavra = "";
                        inComment = true;
                        break;
                     }
                    
                     
                    if (!inComment){ 
                        if ((!palavra.equals("")) && (!palavra.contains("/")))
                            this.addToken(palavra,nLine);
                    }
                        palavra = "";
                    
                }
                else
                    palavra = palavra + caractere;
            }
            
        }
    }

    private void addToken(String palavra,int nLine) {

        if (palavra.matches(inteiro)){
            Token elemento = new Token();
            elemento.setToken(TipoToken.NumInt.toString());
            elemento.setLexema(palavra);
            tokens.add(elemento);
            return;
        }
        
        if (palavra.matches(real)){
            Token elemento = new Token();
            elemento.setToken(TipoToken.NumReal.toString());
            elemento.setLexema(palavra);
            tokens.add(elemento);
            return;
        }
        
        if (operAritimetico.contains(palavra)){
            Token elemento = new Token();
            if(palavra.equals("*")){
                elemento.setToken(TipoToken.OpAritMult.toString());
            } else if(palavra.equals("/")){
                elemento.setToken(TipoToken.OpAritDiv.toString());
            } else if(palavra.equals("+")){
                elemento.setToken(TipoToken.OpAritSoma.toString());
            } else if(palavra.equals("-")){
                elemento.setToken(TipoToken.OpAritSub.toString());
            }
            elemento.setLexema(palavra);
            tokens.add(elemento);
            return;
        }
                   
        if (operRelacionais.contains(palavra)){
            Token elemento = new Token();
            if(palavra.equals(">")){
                elemento.setToken(TipoToken.OpRelMaior.toString());
            } else if(palavra.equals("<")){
                elemento.setToken(TipoToken.OpRelMenor.toString());
            } else if(palavra.equals("=")){
                elemento.setToken(TipoToken.OpRelIgual.toString());
            } else if(palavra.equals("<=")){
                elemento.setToken(TipoToken.OpRelMenorIgual.toString());
            } else if(palavra.equals(">=")){
                elemento.setToken(TipoToken.OpRelMaiorIgual.toString());
            } else if(palavra.equals("<>")){
                elemento.setToken(TipoToken.OpRelDif.toString());
            } else if(palavra.equals("E")){
                elemento.setToken(TipoToken.OpBoolE.toString());
            } else if(palavra.equals("OU")){
                elemento.setToken(TipoToken.OpBoolOu.toString());
            }
 
            elemento.setLexema(palavra);
            tokens.add(elemento);
            return;
        }
        
        if (reservadas.contains(palavra)){
            Token elemento = new Token();
            if(palavra.equals("DECLARACOES")){
                elemento.setToken(TipoToken.PCDeclaracoes.toString());
            } else if(palavra.equals("ALGORITMO")){
                elemento.setToken(TipoToken.PCAlgoritmo.toString());
            } else if(palavra.equals("INTEIRO")){
                elemento.setToken(TipoToken.PCInteiro.toString());
            } else if(palavra.equals("REAL")){
                elemento.setToken(TipoToken.PCReal.toString());
            } else if(palavra.equals("ATRIBUIR")){
                elemento.setToken(TipoToken.PCAtribuir.toString());
            } else if(palavra.equals("A")){
                elemento.setToken(TipoToken.PCA.toString());
            } else if(palavra.equals("LER")){
                elemento.setToken(TipoToken.PCLer.toString());
            } else if(palavra.equals("IMPRIMIR")){
                elemento.setToken(TipoToken.PCImprimir.toString());
            } else if(palavra.equals(":")){
                elemento.setToken(TipoToken.Delim.toString());
            } else if(palavra.equals("SE")){
                elemento.setToken(TipoToken.PCSe.toString());
            } else if(palavra.equals("ENTAO")){
                elemento.setToken(TipoToken.PCEntao.toString());
            } else if(palavra.equals("ENQUANTO")){
                elemento.setToken(TipoToken.PCEnquanto.toString());
            } else if(palavra.equals("INICIO")){
                elemento.setToken(TipoToken.PCInicio.toString());
            } else if(palavra.equals("FIM")){
                elemento.setToken(TipoToken.PCFim.toString());
            } 
            
            if(palavra.length() == 2){
                elemento.setLexema(palavra.substring(0, 2));
            } else if(palavra.length() == 1){
                elemento.setLexema(palavra.substring(0, 1));
            } else{
                elemento.setLexema(palavra.substring(0, 3));
            }
            
            tokens.add(elemento);
            return;
        }
        
        if(!palavra.equals(reservadas)){
            if (palavra.matches(identificador)){
                Token elemento = new Token();
                if(palavra.equals("INT")){
                    elemento.setToken(TipoToken.PCInteiro.toString());
                } else if(palavra.equals("FLOAT")){
                    elemento.setToken(TipoToken.PCReal.toString());
                } else{
                    elemento.setToken(TipoToken.Var.toString());
                }

                elemento.setLexema(palavra);
                tokens.add(elemento);
                return;
            }
            
        }
        
        System.err.println("Desconhecido: " + palavra);
    }
    
    
    
    public List<Token> getTokens(){
        return tokens;
    }
}
