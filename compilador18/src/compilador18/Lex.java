package compilador18;

import java.io.BufferedReader;
import java.io.FileInputStream;

import java.io.FileReader;
import java.io.PushbackReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

public class Lex {
	
	private int[][] matrizEstados = {
			{8,2,9,4,9,5,1,8,8,6,7,0,0,9},
			{1,1,9,9,9,9,9,1,1,9,9,9,9,9},
			{9,2,9,9,9,9,3,9,9,9,9,9,9,9},
			{9,9,9,9,9,9,9,9,9,9,9,9,9,9},
			{9,9,9,9,9,9,9,9,9,9,9,9,9,9},
			{9,9,9,9,9,9,9,9,9,9,9,9,9,9},
			{6,6,6,6,6,6,6,6,6,9,6,6,9,9},
			{7,7,7,7,7,7,7,7,7,7,0,7,7,9},
			{8,9,9,9,9,9,8,8,8,9,9,9,9,9},
			};
	private int linea;
	private HashMap<String, Object> TS;
	private HashMap<String, Integer> referencia;
	private PushbackReader reader;
	private AbstractAS[][] matrizAccionesSemanticas;
	public String tokenActual;
	public ArrayList<Object> tokenDesdeAS;
	public char caracterActual;
	private int idIcrementTS = 12;
	private String listadoTokens;
	private String listadoErrores;
	
	public Lex(String path) throws FileNotFoundException {
		cargarPath(path);
		linea = 1;
		cargarTS();
		cargarReferenciasMatrizEstado();
		cargarAS();
		listadoTokens = "";
		listadoErrores = "";
	}	
	
	private void cargarPath(String path) throws FileNotFoundException {
		reader = new PushbackReader(new BufferedReader(new InputStreamReader(new FileInputStream(path), Charset.forName("UTF-8")))); 
	}
	
	private void cargarTS() {
		TS = new HashMap<String, Object>();
		
		ArrayList<Object> token_if = new ArrayList<Object>(); 
		token_if.add(1); 
		token_if.add("palabraReservada"); 
		TS.put("IF", token_if);
		
		ArrayList<Object> token_else = new ArrayList<>(); token_else.add(2); token_else.add("palabraReservada"); TS.put("ELSE", token_else);
		ArrayList<Object> token_end = new ArrayList<>(); token_end.add(3); token_end.add("palabraReservada"); TS.put("END_IF", token_end);
		ArrayList<Object> token_print = new ArrayList<>(); token_print.add(4); token_print.add("palabraReservada"); TS.put("PRINT", token_print);
		ArrayList<Object> token_case = new ArrayList<>(); token_case.add(5); token_case.add("palabraReservada"); TS.put("CASE", token_case);
		ArrayList<Object> token_do = new ArrayList<>(); token_do.add(6); token_do.add("palabraReservada"); TS.put("DO", token_do);
		ArrayList<Object> token_void = new ArrayList<>(); token_void.add(7); token_void.add("palabraReservada"); TS.put("VOID", token_void);
		ArrayList<Object> token_fun = new ArrayList<>(); token_fun.add(8); token_fun.add("palabraReservada"); TS.put("FUN", token_fun);
		ArrayList<Object> token_return = new ArrayList<>(); token_return.add(9); token_return.add("palabraReservada"); TS.put("RETURN", token_return);
		ArrayList<Object> token_integer = new ArrayList<>(); token_integer.add(10); token_integer.add("palabraReservada"); TS.put("INTEGER", token_integer);
		ArrayList<Object> token_linteger = new ArrayList<>(); token_linteger.add(11); token_linteger.add("palabraReservada"); TS.put("LINTEGER", token_linteger);
	}
	
	private void cargarAS() {
		matrizAccionesSemanticas =  new AbstractAS[9][14];
        AbstractAS AS1 = new AS1(this);
        AbstractAS AS2 = new AS2(this);
        AbstractAS AS3 = new AS3(this);
        AbstractAS AS4 = new AS4(this);
        AbstractAS AS5 = new AS5(this);
        AbstractAS AS6 = new AS6(this);
        AbstractAS AS7 = new AS7(this);
        AbstractAS AS8 = new AS8(this);
        AbstractAS AS10 = new AS10(this);
        
        matrizAccionesSemanticas[0][0] = AS1;
        matrizAccionesSemanticas[0][1] = AS1;
        matrizAccionesSemanticas[0][2] = AS8;
        matrizAccionesSemanticas[0][3] = AS1;
        matrizAccionesSemanticas[0][4] = AS8;
        matrizAccionesSemanticas[0][5] = AS1;
        matrizAccionesSemanticas[0][6] = AS1;
        matrizAccionesSemanticas[0][7] = AS1;
        matrizAccionesSemanticas[0][8] = AS1;
        matrizAccionesSemanticas[0][9] = AS1;
        
        matrizAccionesSemanticas[1][0] = AS4;
        matrizAccionesSemanticas[1][1] = AS4;
        matrizAccionesSemanticas[1][2] = AS5;
        matrizAccionesSemanticas[1][3] = AS5;
        matrizAccionesSemanticas[1][4] = AS5;
        matrizAccionesSemanticas[1][5] = AS5;
        matrizAccionesSemanticas[1][6] = AS5;
        matrizAccionesSemanticas[1][7] = AS4;
        matrizAccionesSemanticas[1][8] = AS4;
        matrizAccionesSemanticas[1][9] = AS5;
        matrizAccionesSemanticas[1][10] = AS5;
        matrizAccionesSemanticas[1][11] = AS5;
        matrizAccionesSemanticas[1][12] = AS5;
        matrizAccionesSemanticas[1][13] = AS5;
        
        matrizAccionesSemanticas[2][1] = AS2;
        
        matrizAccionesSemanticas[3][7] = AS6;
        matrizAccionesSemanticas[3][8] = AS7;
        
        matrizAccionesSemanticas[4][4] = AS8;
        
        matrizAccionesSemanticas[5][0] = AS3;
        matrizAccionesSemanticas[5][1] = AS3;
        matrizAccionesSemanticas[5][2] = AS3;
        matrizAccionesSemanticas[5][3] = AS3;
        matrizAccionesSemanticas[5][4] = AS8;
        matrizAccionesSemanticas[5][5] = AS3;
        matrizAccionesSemanticas[5][6] = AS3;
        matrizAccionesSemanticas[5][7] = AS3;
        matrizAccionesSemanticas[5][8] = AS3;
        matrizAccionesSemanticas[5][9] = AS3;
        matrizAccionesSemanticas[5][10] = AS3;
        matrizAccionesSemanticas[5][11] = AS3;
        matrizAccionesSemanticas[5][12] = AS3;
        matrizAccionesSemanticas[5][13] = AS3;  
        
        matrizAccionesSemanticas[6][0] = AS2;
        matrizAccionesSemanticas[6][1] = AS2;
        matrizAccionesSemanticas[6][2] = AS2;
        matrizAccionesSemanticas[6][3] = AS2;
        matrizAccionesSemanticas[6][4] = AS2;
        matrizAccionesSemanticas[6][5] = AS2;
        matrizAccionesSemanticas[6][6] = AS2;
        matrizAccionesSemanticas[6][7] = AS2;
        matrizAccionesSemanticas[6][8] = AS2;
        matrizAccionesSemanticas[6][9] = AS10;
        matrizAccionesSemanticas[6][10] = AS2;
        matrizAccionesSemanticas[6][11] = AS2;
        
        matrizAccionesSemanticas[8][0] = AS2;
        matrizAccionesSemanticas[8][1] = AS3;
        matrizAccionesSemanticas[8][2] = AS3;
        matrizAccionesSemanticas[8][3] = AS3;
        matrizAccionesSemanticas[8][4] = AS3;
        matrizAccionesSemanticas[8][5] = AS3;
        matrizAccionesSemanticas[8][6] = AS2;
        matrizAccionesSemanticas[8][7] = AS2;
        matrizAccionesSemanticas[8][8] = AS2;
        matrizAccionesSemanticas[8][9] = AS3;
        matrizAccionesSemanticas[8][10] = AS3;
        matrizAccionesSemanticas[8][11] = AS3;
        matrizAccionesSemanticas[8][12] = AS3;
        matrizAccionesSemanticas[8][13] = AS3;  
       
	}
	
	private void cargarReferenciasMatrizEstado() {
		referencia = new HashMap<String, Integer>();
		
        referencia.put("L", 0);
        referencia.put("D", 1);
        referencia.put("+", 2);
        referencia.put("-", 2);
        referencia.put("*", 2);
        referencia.put("/", 2);
        referencia.put("(", 2);
        referencia.put(")", 2);
        referencia.put("{", 2);
        referencia.put("}", 2);
        referencia.put(",", 2);
        referencia.put(";", 2);
        referencia.put(":", 3);
        referencia.put("!", 3);
        referencia.put("=", 4);
        referencia.put("<", 5);
        referencia.put(">", 5);
        referencia.put("_", 6);
        referencia.put("i", 7);
        referencia.put("l", 8);
        referencia.put("'", 9);
        referencia.put("#", 10);
		referencia.put(" ", 11);
        referencia.put("\t", 11);
        referencia.put("\n", 12);
        referencia.put("\r", 12);
        referencia.put("EOF", 13);
	}
	
	public Object leerToken() throws IOException {
		
		tokenActual = "";
		int estadoActual = 0;
		AbstractAS accionSemantica;
		
		try {
			int c = 0;
			String entradaIndice;
			char caracter;
			
			while(estadoActual != 9) {
				c = reader.read();
				caracterActual = (char) c;
				entradaIndice =  Character.toString((char) c);
				
				//Si estado inicial y caracter es un espacio, salto de linea, tab => consumir
				if ( estadoActual == 0 && (c == 8 || c == 9 || c == 10 || c == 13)) {
					//Si es salto de linea, sumar a cant de lineas
					if(c == 10) {
						linea++;
					}
					continue;
				}
				
				//Consideracion de corte para EOF
				if ( estadoActual == 0 && c == -1) {
					return null;
				}
				
				//Identificar que se esta leyendo para poder matchear con la matriz de estados
				//Hacer consideraciones para L,D,i,l,\t,\n,EOF
	            if (((c >= 65) && (c <= 90)) || ((c >= 97) && (c <= 122)))
	            	entradaIndice = "L";
	            if ((c >= 48) && (c <= 57))
	            	entradaIndice = "D";
	            if (c == 105)
	            	entradaIndice = "i";
	            if (c == 108)
	            	entradaIndice = "l";
	            if (c == 10)
	            	entradaIndice = "\n";
	            if (c == 13)
	            	entradaIndice = "\r";
	            if (c == 9)
	            	entradaIndice = "\t";
	            if (c == -1)
	            	entradaIndice = "EOF";
	          
	            
	            if(referencia.containsKey(entradaIndice)==false){
	            	notificarError("No se encontro en la tabla de referencia "+c);
	            } else {
	            	accionSemantica = matrizAccionesSemanticas[estadoActual][referencia.get(entradaIndice)];
	            	if(accionSemantica != null) {
		            	accionSemantica.ejecutar(TS);	
	            	}
	            	estadoActual = matrizEstados[estadoActual][referencia.get(entradaIndice)];
	            }
			}
			notificarToken(tokenDesdeAS.get(1)+" "+tokenActual);
			return tokenDesdeAS;
			
		} catch (FileNotFoundException ex) {
			notificarError("Archivo no encontrado");
			return null;
        }
	}
	
	public void agregarCaracterAlToken(char c) {
		tokenActual += c;
	}
	
	public char getCaracterActual() {
		return caracterActual;
	}
	
	public void devolverBuffer(char c) throws IOException {
		//Tambien le resta uno al token actual
		//tokenActual = tokenActual.substring(0, tokenActual.length() - 1);
		reader.unread(c);
	}
	
	public String getTokenActual() {
		return tokenActual;
	}
	
	public boolean estaEnTS(String s) {
		Object o = TS.get(s);
		if(o != null)
			return true;
		return false;
	}
	
	public void setTokenDesdeAS(String s) {
		tokenDesdeAS = (ArrayList<Object>) TS.get(s);
	}
	
	public void setSimbolo(String s) {
		ArrayList<Object> simbolo = new ArrayList<>(); 
		simbolo.add(s); 
		simbolo.add("simbolo"); 
		
		tokenDesdeAS = simbolo;
	}
	
	public String altaEnTS(String s, String tipo) {
		ArrayList<Object> nuevaEntrada = new ArrayList<>(); 
		nuevaEntrada.add(idIcrementTS); 
		idIcrementTS++;
		nuevaEntrada.add(tipo); 
		TS.put(s, nuevaEntrada);
		return s;
	}
	
	public void notificarToken(String token) {
		listadoTokens += "Linea "+linea+": "+token+"\n\r";
	}
	
	public void notificarError(String error) {
		listadoErrores += "Linea "+linea+": "+error+"\n\r";
	}
	
	public String getListadoTokens() {
		return listadoTokens;
	}
	
	public String getListadoErrores() {
		return listadoErrores;
	}
	
	public static void main(String[] args) throws IOException {
		//
		String path = "ejemplo.txt";//;args[0]; TODO completar con el path para el ejecutable
		//
		Lex L = new Lex(path);
		Object token = L.leerToken();
		
		while(token!=null) {
			token = L.leerToken();
		}
		try (PrintWriter out = new PrintWriter("tokens.txt")) {
		    out.println(L.getListadoTokens());
		}
		try (PrintWriter out = new PrintWriter("errores.txt")) {
		    out.println(L.getListadoErrores());
		}
		
	}
}