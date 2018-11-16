package accionesSemanticas;

import analizadorLexico.BufferLectura;
import analizadorLexico.Token;
import globales.TablaSimbolos;

public class AS2 implements AccionSemantica {

	public Token ejecutar(BufferLectura pf, StringBuilder lexema, char ultimo_caracter) {
		//chequear longitud de los identificadores.
		pf.devolverCaracter(ultimo_caracter);
		if (lexema.length() > 25) {
			lexema = new StringBuilder(lexema.substring(0, 25)); // se trunca
			System.out.println("Linea " + pf.getNroLinea() + ": (AL) WARNING: Identificador truncado");
		}
		Token token = new Token(TablaSimbolos.getID("id"), lexema.toString(),"Identificador");
		TablaSimbolos.addSimbolo(token);
		return token;
	}

}
