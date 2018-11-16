package accionesSemanticas;

import analizadorLexico.BufferLectura;
import analizadorLexico.Token;

public class AS10 implements AccionSemantica {

	public Token ejecutar(BufferLectura pf, StringBuilder lexema, char ultimo_caracter) {
		//Salto de linea
		pf.sigLinea();
		return null;
	}

}
