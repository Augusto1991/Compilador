package accionesSemanticas;

import analizadorLexico.BufferLectura;
import analizadorLexico.Token;
import globales.TablaSimbolos;

public class AS8 implements AccionSemantica {

	public Token ejecutar(BufferLectura pf, StringBuilder lexema, char ultimo_caracter) {
		//devolver cadena.
		Token token = new Token(TablaSimbolos.getID("cadena"), lexema.toString(),"cadena");
		TablaSimbolos.addSimbolo(token);
		return token;
	}

}
