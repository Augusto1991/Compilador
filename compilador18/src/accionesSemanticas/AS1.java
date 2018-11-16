package accionesSemanticas;

import analizadorLexico.BufferLectura;
import analizadorLexico.Token;

public class AS1 implements AccionSemantica {

	public Token ejecutar(BufferLectura pf, StringBuilder lexema, char ultimo_caracter) {
		//agregar caracter al lexema.
		lexema.append(ultimo_caracter);
		return null;
	}

}
