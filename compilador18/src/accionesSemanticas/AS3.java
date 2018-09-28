package accionesSemanticas;

import analizadorLexico.BufferLectura;
import analizadorLexico.Token;
import globales.TablaSimbolos;

public class AS3 implements AccionSemantica {

	@Override
	public Token ejecutar(BufferLectura pf, StringBuilder lexema, char ultimo_caracter) {
		//Contaste entera, verificar limite y agregar.
		long cte = Long.parseLong(lexema.toString());
		if (cte > Math.pow(2, 15)) {
			System.out.println("Linea " + pf.getNroLinea() + ": (AL) WARNING: Constante fuera del rango permitido. (integer = "+ (int) cte +")");
			cte = (long)Math.pow(2, 15);
			System.out.println("Linea " + pf.getNroLinea() + ": (AL) WARNING: Se le asignara el mayor valor permitido. ("+ (int) cte +")");
		}
		Token token = new Token(TablaSimbolos.getID("cte"), Long.toString(cte),"Constante entera");
		token.addAtributo("Tipo", "integer");
		TablaSimbolos.addSimbolo(token);
		//Agregar atributo contador e incrementar si ya esta
		return token;
	}

}
