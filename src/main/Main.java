package main;

import classes.AnalisadorLexico;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        AnalisadorLexico analisador = new AnalisadorLexico("/home/zscrollock/NetBeansProjects/AnalisadorLexicoLuis/src/main/analisador_lexico.txt");
        try {
            analisador.analisar();
            System.out.println("<Token, Lexema> \n"+ analisador.getTokens());

        } catch (IOException ex) {
            System.err.println("Falha ao ler o arquivo.");
        }
    }
}
