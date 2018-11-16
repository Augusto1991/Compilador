package accionesSemanticas;

import analizadorLexico.BufferLectura;
import analizadorLexico.Token;

public class AS12 implements AccionSemantica {

	public Token ejecutar(BufferLectura pf, StringBuilder lexema, char ultimo_caracter) {
		//descartar cadena
		lexema.setLength(0);
		return null;
	}

}
