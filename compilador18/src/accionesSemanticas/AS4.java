package accionesSemanticas;

import analizadorLexico.BufferLectura;
import analizadorLexico.Token;
import globales.TablaSimbolos;

public class AS4 implements AccionSemantica {

	@Override
	public Token ejecutar(BufferLectura pf, StringBuilder lexema, char ultimo_caracter) {
		//Contaste entera larga, verificar limite y agregar.
		long cte = Long.parseLong(lexema.toString());
		if (cte > Math.pow(2, 31)) {
			System.out.println("Linea " + pf.getNroLinea() + ": (AL) WARNING: Constante fuera del rango permitido. (integer = "+ (int) cte +")");
			cte = (long)Math.pow(2, 31);
			System.out.println("Linea " + pf.getNroLinea() + ": (AL) WARNING: Se le asignara el mayor valor permitido. ("+ (int) cte +")");
		}
		Token token = new Token(TablaSimbolos.getID("cte_larga"), Long.toString(cte),"Constante entera larga");
		token.addAtributo("Tipo", "linteger");
		TablaSimbolos.addSimbolo(token);
		//Agregar atributo contador e incrementar si ya esta
		return token;
	}

}
