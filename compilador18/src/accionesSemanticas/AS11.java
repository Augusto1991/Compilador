package accionesSemanticas;

import analizadorLexico.BufferLectura;
import analizadorLexico.Token;

public class AS11 implements AccionSemantica {

	@Override
	public Token ejecutar(BufferLectura pf, StringBuilder lexema, char ultimo_caracter) {
		// Accion Nula
		return null;
	}

}
