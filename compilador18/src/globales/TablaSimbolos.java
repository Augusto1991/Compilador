package globales;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import analizadorLexico.Token;

public final class TablaSimbolos {
	
	private static Hashtable<String, Integer> identificadores = new Hashtable<String,Integer>();
	private static Hashtable<String,Token> simbolos = new Hashtable<String,Token>();
	
	private static void inicializar() {
		identificadores.put("(", 40);
		identificadores.put(")", 41);
		identificadores.put("*", 42);
		identificadores.put("+", 43);
		identificadores.put(",", 44);
		identificadores.put("-", 45);
		identificadores.put("/", 47);
		identificadores.put(":", 58);
		identificadores.put(";", 59);	
		identificadores.put("<", 60);
		identificadores.put("=", 61);
		identificadores.put(">", 62);
		identificadores.put("{", 123);
		identificadores.put("}", 125);
		identificadores.put("if", 257);
		identificadores.put("else", 258);
		identificadores.put("end_if", 259);
		identificadores.put("print", 260);
		identificadores.put("integer", 261);
		identificadores.put("linteger", 262);
		identificadores.put("case", 263);
		identificadores.put("do", 264);
		identificadores.put("void", 265);
		identificadores.put("fun", 266);
		identificadores.put("return", 267);
		identificadores.put("id", 268);
		identificadores.put("cadena", 269);
		identificadores.put("cte", 270);
		identificadores.put("cte_larga", 271);
		identificadores.put(">=", 272);
		identificadores.put("<=", 273);
		identificadores.put("!=", 274);
		identificadores.put(":=", 275);
	}
	
	public static boolean contains(String id) {
		inicializar();
		return (identificadores.containsKey(id));
	}

	public static int getID(String id) {	
		inicializar();
		return (identificadores.get(id));
	}
	
	public static void addSimbolo(Token t) {
		if (!simbolos.containsKey(t.getLexema()))
			simbolos.put(t.getLexema(),t);
	}
	
	public static Token getSimbolo(String lexema) {
		return simbolos.get(lexema);
	}
	
	public static Set<String> getLexemas() {
		return simbolos.keySet();
	}

	public static String imprimir() {
		StringBuffer out = new StringBuffer();
		for (String s: simbolos.keySet()) {
			Token t = getSimbolo(s);
			out.append(t.imprimir() + "\n");
		}
		return out.toString();		
	}
	
	public static boolean contiene(String lexema) {
		return simbolos.containsKey(lexema);
	}
	
	public static void remove(String lexema) {
		simbolos.remove(lexema);
	}
	
	public static void modificarLexema(String viejo, String nuevo) {
		Token t = getSimbolo(viejo);
		if (t != null) {
			t.setLexema(nuevo);
			remove(viejo);
			addSimbolo(t);
		}
	}
	
	public static Set<String> iterator() {
		return new HashSet<String>(simbolos.keySet());
	}

}
