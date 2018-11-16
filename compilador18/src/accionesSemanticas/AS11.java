package accionesSemanticas;

import analizadorLexico.BufferLectura;
import analizadorLexico.Token;

public class AS11 implements AccionSemantica {

	public Token ejecutar(BufferLectura pf, StringBuilder lexema, char ultimo_caracter) {
		// Accion Nula
		if(ultimo_caracter == 0)
			System.out.println("Fin de archivo ");
		return null;
	}

}
