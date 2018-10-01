package main;

import java.io.FileInputStream;

import analizadorLexico.AnalizadorLexico;
import analizadorSintactico.Parser;
import globales.TablaSimbolos;

public class Compilador {

	public static void main(String[] args) {
		try {
			String path = args[0];
			//String path = "pruebas/case.txt";
			FileInputStream archivo = new FileInputStream(path);
			StringBuffer sb = new StringBuffer();
			while (archivo.available() != 0)
				sb.append((char) archivo.read());
			AnalizadorLexico al = new AnalizadorLexico(sb);
			archivo.close();
			Parser parser = new Parser(al);
			parser.run();
			System.out.println("Tabla de Simbolos: \n" +TablaSimbolos.imprimir());
		 } catch (Exception e) {}

	}

}
