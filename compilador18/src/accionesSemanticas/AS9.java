package accionesSemanticas;

import analizadorLexico.BufferLectura;
import analizadorLexico.Token;

public class AS9 implements AccionSemantica {

	public Token ejecutar(BufferLectura pf, StringBuilder lexema, char ultimo_caracter) {
		// Caracter invalido
		pf.devolverCaracter(ultimo_caracter);
		System.out.println("Linea " + pf.getNroLinea() + ": (AL) Se esperaba otro caracter luego de: " + lexema);
		lexema.setLength(0);
		return null;
	}

}
