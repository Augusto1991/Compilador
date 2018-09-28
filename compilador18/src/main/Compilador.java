package main;

import java.io.FileInputStream;

import analizadorLexico.AnalizadorLexico;
import globales.TablaSimbolos;

public class Compilador {

	public static void main(String[] args) {
		try {
			String path = "prueba.txt";
			 FileInputStream archivo = new FileInputStream(path);
			 StringBuffer sb = new StringBuffer();
			 while (archivo.available() != 0)
				 sb.append((char) archivo.read());
			 AnalizadorLexico al = new AnalizadorLexico(sb);
			 archivo.close();
			 run(al);
		 } catch (Exception e) {}

	}

	private static void run(AnalizadorLexico al) {
		while(al.notEOF()) {
			int aux = al.yylex();
		}
		System.out.println("Tabla de Simbolos: \n" +TablaSimbolos.imprimir());
	}

}
