package accionesSemanticas;

import analizadorLexico.BufferLectura;
import analizadorLexico.Token;
import globales.TablaSimbolos;

public class AS7 implements AccionSemantica {

	public Token ejecutar(BufferLectura pf, StringBuilder lexema, char ultimo_caracter) {
		// reotrna operador y devuelve el ultimo caracter.
		pf.devolverCaracter(ultimo_caracter);
		return new Token(TablaSimbolos.getID(lexema.toString()),lexema.toString(),null);
	}

}
