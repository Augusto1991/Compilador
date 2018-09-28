package accionesSemanticas;

import analizadorLexico.BufferLectura;
import analizadorLexico.Token;
import globales.TablaSimbolos;

public class AS8 implements AccionSemantica {

	@Override
	public Token ejecutar(BufferLectura pf, StringBuilder lexema, char ultimo_caracter) {
		//chequear longitud de los identificadores.		
		lexema.append(ultimo_caracter);
		Token token = new Token(TablaSimbolos.getID("cadena"), lexema.toString(),"cadena");
		TablaSimbolos.addSimbolo(token);
		return token;
	}

}
