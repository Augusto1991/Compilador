package accionesSemanticas;

import analizadorLexico.BufferLectura;
import analizadorLexico.Token;

public interface AccionSemantica {
	
	public Token ejecutar(BufferLectura pf, StringBuilder lexema, char ultimo_caracter);
}