package analizadorLexico;

import java.util.Hashtable;

public class Token {
	
	private int id;
	private String lexema, descripcion;	
	private Hashtable<String,Object> atributos = new Hashtable<String,Object>();
	
	public Token(int id, String lexema, String descripcion) {
		this.id = id;
		this.lexema = lexema;
		this.descripcion = descripcion;
	}
	
	public void addAtributo(String caract, Object val) {
		atributos.put(caract, val);
	}
	
	public String getAtributo(String caract) {
		return (String)atributos.get(caract);
	}
	
	public int getIdentificador() {
		return id;
	}
	
	public void setLexema(String lexema) {
		this.lexema = lexema;
	}
	
	public String getLexema() {
		return lexema;
	}
	
	public String imprimir() {
		return ("Token: " + id + "  | Lexema: " + lexema + "  | Descripción: " + descripcion);
	}
	
	public String toString() {
		if (descripcion == null)
			return lexema;
		return descripcion + " " + lexema;
	}
}
