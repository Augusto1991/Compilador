package accionesSemanticas;

import analizadorLexico.BufferLectura;
import analizadorLexico.Token;
import globales.TablaSimbolos;

public class AS5 implements AccionSemantica {

	public Token ejecutar(BufferLectura pf, StringBuilder lexema, char ultimo_caracter) {
		// Verificar palabra reservada
		pf.devolverCaracter(ultimo_caracter);
		if (TablaSimbolos.contains(lexema.toString()))
			return new Token(TablaSimbolos.getID(lexema.toString()),lexema.toString(),"Palabra Reservada");
		System.out.println("Linea " + pf.getNroLinea() + ": (AL) Palabra Reservada no válida");
		lexema.setLength(0);
		return null;
	}

}
