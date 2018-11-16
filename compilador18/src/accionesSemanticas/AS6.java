package accionesSemanticas;

import analizadorLexico.BufferLectura;
import analizadorLexico.Token;
import globales.TablaSimbolos;

public class AS6 implements AccionSemantica {

	public Token ejecutar(BufferLectura pf, StringBuilder lexema, char ultimo_caracter) {
		// Retornar el operador
		lexema.append(ultimo_caracter);
		return new Token(TablaSimbolos.getID(lexema.toString()),lexema.toString(), null);
	}

}
