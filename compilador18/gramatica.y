%{package analizadorSintactico;
  import analizadorLexico.*;
  import globales.*;
  import java.util.Stack;
%}

%token IF
%token ELSE
%token END_IF
%token PRINT
%token INTEGER
%token LINTEGER
%token CASE
%token DO
%token VOID
%token FUN
%token RETURN
%token ID
%token CADENA
%token CTE
%token CTE_LARGA
%token MAYOR_IGUAL
%token MENOR_IGUAL
%token DISTINTO
%token ASIGN


%left '+' '-'
%left '*' '/'

%%

programa              	: bloque_sentencias
;

bloque_sentencias		: bloque_sentencias sentencia_ejecutable
						| bloque_sentencias sentencia_declarativa
						| sentencia_ejecutable
						| sentencia_declarativa
;

bloque_control			: '{' bloque_control sentencia_ejecutable '}'
						| sentencia_ejecutable
;

sentencia_declarativa	: declaracion_variables
						| funcion
;


funcion 				: FUN funcion_fun {System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una funcion FUN.");}
						| VOID funcion_void {System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una funcion VOID.");}
						| VOID funcion_fun {System.out.println("Linea " + al.getNroLinea() + ": (AS) Tipo de la funcion incorrecto.");}
						| FUN funcion_void {System.out.println("Linea " + al.getNroLinea() + ": (AS) Tipo de la funcion incorrecto.");}
;

funcion_fun 			: comienzo_funcion bloque_sentencias RETURN '(' retorno ')' ',' fin_funcion
						| comienzo_funcion bloque_sentencias '(' retorno ')' ',' fin_funcion {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'return' de la funcion.");}
						| comienzo_funcion bloque_sentencias RETURN '(' error ')' ',' fin_funcion {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el retorno de la funcion.");}
						| comienzo_funcion bloque_sentencias RETURN '(' retorno ')' fin_funcion {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego del retorno de la funcion.");}
						| comienzo_funcion  RETURN '(' retorno ')' ',' fin_funcion {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el cuerpo de la funcion.");}
;

funcion_void 			: comienzo_funcion bloque_sentencias fin_funcion
						| comienzo_funcion  fin_funcion {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el cuerpo de la funcion.");}
;

comienzo_funcion      	: ID '(' ')' '{'
						| error '(' ')' '{' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el identificador de la funcion.");}
						| ID  ')' '{' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' luego del identificador de la funcion.");}
						| ID '(' '{' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' luego del identificador de la funcion.");}
						| ID '(' ')' error {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '{' al comienzo de la funcion.");}
						| ID '{' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '()' luego del identificador de la funcion.");}
;

retorno					: bloque_sentencias 
;
					  
fin_funcion           	: '}' 
;

llamado_funcion			: ID '('')'
;

declaracion_variables   : tipo lista_de_variables ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una declaracion de variables.");}
						| error lista_de_variables ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el tipo en la declaracion de variables.");}
						| tipo error ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el identificador o lista de identificadores en la declaracion de variables.");}
						| tipo lista_de_variables error {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la declaracion de variables.");}
;

tipo				  	: INTEGER
					  	| LINTEGER
;

lista_de_variables    	: lista_de_variables ';' ID 
					  	| ID  
;

sentencia_ejecutable  	: sentencia_seleccion
						| sentencia_control
						| sentencia_impresion
						| sentencia_asignacion
						| llamado_funcion
;


sentencia_seleccion	  	: IF condicion_IF bloque_control END_IF ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una sentencia IF.");}
					  	| IF condicion_IF bloque_control ELSE bloque_control END_IF ','
					  	| error condicion_IF bloque_control END_IF ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'if'.");}
					  	| IF condicion_IF bloque_control error ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'end_if'.");}
					  	| IF condicion_IF bloque_control END_IF error {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la sentencia IF.");}
					  	| error condicion_IF bloque_control ELSE bloque_control END_IF ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'if'.");}
					  	| IF condicion_IF bloque_control error bloque_control END_IF ','  {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'else'.");}
					  	| IF condicion_IF bloque_control ELSE bloque_control error ','  {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'end_if'.");}
					  	| IF condicion_IF bloque_control ELSE bloque_control END_IF error {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la sentencia IF.");}
;
					  
condicion_IF          	: '(' condicion ')'
						|  condicion ')' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' en la condicion del IF.");}
						| '(' condicion  {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' en la condicion del IF.");}
;

sentencia_control		: CASE '(' ID ')' '{' lista_opciones '}' ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una sentencia CASE.");}
						| error '(' ID ')' '{' lista_opciones '}' ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'case'.");}
						| CASE  ID ')' '{' lista_opciones '}' ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' en la sentencia CASE.");}
						| CASE '(' error ')' '{' lista_opciones '}' ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba un identificador en la sentencia CASE.");}
						| CASE '(' ID  '{' lista_opciones '}' ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' en la sentencia CASE.");}
						| CASE '(' ID ')'  lista_opciones '}' ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '{' en la sentencia CASE.");}
						| CASE '(' ID ')' '{' error '}' ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la lista de opciones en la sentencia CASE.");}
						| CASE '(' ID ')' '{' lista_opciones  ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '}' en la sentencia CASE.");}
						| CASE '(' ID ')' '{' lista_opciones '}' error {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la sentencia CASE.");}
						| CASE ID '{' lista_opciones '}' ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '()' en la sentencia CASE.");}
						| CASE '(' ID ')'  lista_opciones  ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '{}' en la sentencia CASE.");}
;

lista_opciones			: lista_opciones opcion
						| opcion
;

opcion					: cte ':' DO bloque_control
						| error ':' DO bloque_control {System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una constante en una opcion.");}
						| cte DO bloque_control {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ':' en una opcion.");}
						| cte ':' error bloque_control {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra resrevada 'DO'.");}
;

sentencia_impresion   	: PRINT '(' CADENA ')' ','{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una impresion por pantalla.");}
						| error '(' CADENA ')' ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'print'.");}
						| PRINT  CADENA ')' ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' en la salida por pantalla.");}
						| PRINT '(' error ')' ','	{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una cadena en una salidad por pantalla.");}
						| PRINT '(' CADENA  ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' en la salida por pantalla..");}
						| PRINT '(' CADENA ')' error {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la salida por pantalla.");}
						| PRINT  CADENA ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba '()' en la salida por pantalla..");}
;

sentencia_asignacion	: ID ASIGN expresiones ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una asignacion.");}
						| ID ASIGN ID '(' ')' ','  {System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una asignacion.");}
						| error ASIGN expresiones ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el identificador en la asignacion.");}
						| error ASIGN ID '(' ')' ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el identificador en la asignacion.");}
						| ID expresiones ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el operador de asignacion.");}
						| ID ID '(' ')' ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el operador de asignacion.");}
						| ID ASIGN expresiones error {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de una asignacion.");}
						| ID ASIGN ID '(' ')' error	{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de una asignacion.");}
						| ID ASIGN error ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una expresion o funcion.");}
;
					  
condicion             	: expresiones comparador expresiones
						| expresiones comparador error {System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una expresion en el lado derecho de la condicion.");}
                      	| error comparador expresiones {System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una expresion en el lado izquierdo de la condicion.");}
                      	| expresiones error expresiones {System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba un comparador entre las expresiones de la condicion.");}
                      	| error {System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una condicion.");}
;

comparador            	: '<'
					  	| '>'
					  	| '='
					  	| MENOR_IGUAL
					  	| MAYOR_IGUAL
					  	| DISTINTO
;

expresiones				: expresion 
						| conversion_explicita
;

conversion_explicita	: tipo '(' expresion ')' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una conversion explicita.");}
						| tipo '(' error ')' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una expresion en la conversion.");}
						| tipo  expresion ')' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' en la conversion.");}
						| tipo '(' expresion error {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' en la conversion.");}
;

expresion				: expresion '+' termino 
						| expresion '-' termino 
						| termino
;

termino					: termino '*' factor 
						| termino '/' factor 
						| factor
;

factor 					: ID
						| cte
;

cte 					: CTE {$$ = $1;} //verificar rango
						| CTE_LARGA {$$ = $1;} //verificar rango
						| '-' CTE // verIFicar rango
						| '-' CTE_LARGA // verificar rango
;

%%

private AnalizadorLexico al;

public int yylex() {
	if (al.notEOF()) {
		int valor = al.yylex();
		if (valor != -1) // error
			return valor;
		while (al.notEOF()) {
			valor = al.yylex();
			if (valor != -1)
				return valor;
		}		
	}
	return 0;	
}

public void yyerror(String s) {
	System.out.println("Linea " + al.getNroLinea() + ": (Parser) " + s);
}