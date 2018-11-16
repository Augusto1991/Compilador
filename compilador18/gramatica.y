%{	package analizadorSintactico;
	import analizadorLexico.*;
	import nodo.*;
	import globales.*;
	import java.util.Stack;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.io.PrintWriter;
	import java.util.logging.Level;
	import java.util.logging.Logger;
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

programa              	: bloque_sentencias	{
												$$ = $1;
												cargarArbol((Nodo)$$.obj);
											}
;

bloque_sentencias		: bloque_sentencias sentencia_ejecutable	{
																		$$ = new ParserVal(new Nodo((Nodo)$1.obj, (Nodo)$2.obj, "ejecutable",null));
																	}
						| bloque_sentencias sentencia_declarativa	{
																		$$ = new ParserVal(new Nodo((Nodo)$1.obj, (Nodo)$2.obj, "declarativa",null));
																	}
						| sentencia_ejecutable	{
													$$ = $1;
												}
						| sentencia_declarativa	{
													$$ = $1;
												}
;

bloque_control			: '{' bloque_control sentencia_ejecutable '}'	{
																			$$ = new ParserVal(new Nodo((Nodo)$2.obj, (Nodo)$3.obj, "BLOQUE",null));
																		}
						| '{' sentencia_ejecutable '}'	{	
															$$ = $2;
														}
						| sentencia_ejecutable	{
													$$ = $1;
												}
;

sentencia_declarativa	: declaracion_variables	{
													$$ = $1;
												}
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

llamado_funcion			: ID '('')' ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion el llamado de una funcion.");}
;

declaracion_variables   : tipo lista_de_variables ','	{
															//System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una declaracion de variables.");
															$$ = new ParserVal (new Nodo((Nodo)$1.obj,(Nodo)$3.obj,"lista_variables",null));
															setearTipo(((Nodo)$1.obj).getNombre(),(Nodo)$2.obj);
														}
						| error lista_de_variables ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el tipo en la declaracion de variables.");}
						| tipo error ',' {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el identificador o lista de identificadores en la declaracion de variables.");}
						| tipo lista_de_variables error {System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la declaracion de variables.");}
;

tipo				  	: INTEGER 	{
										$$ = new ParserVal (new Nodo($1.sval,null));
									}
					  	| LINTEGER	{
										$$ = new ParserVal (new Nodo($1.sval,null));
									}
;

lista_de_variables    	: lista_de_variables ';' ID	{
														$$ = new ParserVal(new Nodo ((Nodo)$1.obj,((Nodo) new ParserVal(new Nodo($3.sval, "ID")).obj),",","lista"));
													}
					  	| ID 	{
					  				$$ = new ParserVal (new Nodo($1.sval,"ID"));
							  	}
;

sentencia_ejecutable  	: sentencia_seleccion	{
													$$=$1;
												}
						| sentencia_control		{
													$$=$1;
												}
						| sentencia_impresion	{
													$$=$1;
												}
						| sentencia_asignacion	{
													$$=$1;
													//System.out.println("se crea una sentencia ejecutable con "+((Nodo)$1.obj).getNombre());
													
												}
						| llamado_funcion		{
													$$=$1;
												}
;


sentencia_seleccion	  	: IF condicion_IF bloque_control END_IF ',' {
																		//System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una sentencia IF.");
																		$$ = new ParserVal(new Nodo((Nodo)$2.obj,(Nodo)$3.obj,"IF",null));
																	}
					  	| IF condicion_IF bloque_control ELSE bloque_control END_IF ','	{
				  																			//System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una sentencia IF.");				
																							Nodo nodoThen = new Nodo((Nodo)$3.obj,null,"THEN",null);
																							Nodo nodoElse = new Nodo((Nodo)$5.obj,null, "ELSE", null);
																							Nodo nodoIntermedio = new Nodo(nodoThen,nodoElse,"THEN_ELSE",null);
																							$$ = new ParserVal(new Nodo ((Nodo)$2.obj,nodoIntermedio,"IF_CON_ELSE",null));
			  																			}
					  	
					  	| error condicion_IF bloque_control END_IF ','	{
					  														//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'if'.");
				  														}
					  	| IF condicion_IF bloque_control error ','	{
					  													//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'end_if'.");
				  													}
					  	| IF condicion_IF bloque_control END_IF error	{
				  															//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la sentencia IF.");
			  															}
					  	| error condicion_IF bloque_control ELSE bloque_control END_IF ','	{
					  																			//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'if'.");
				  																			}
					  	| IF condicion_IF bloque_control error bloque_control END_IF ','	{
																						  		//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'else'.");
																						  	}
					  	| IF condicion_IF bloque_control ELSE bloque_control error ','	{
																						  	//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'end_if'.");
																					  	}
					  	| IF condicion_IF bloque_control ELSE bloque_control END_IF error	{
																						  		//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la sentencia IF.");
																						  	}
;
					  
condicion_IF          	: '(' condicion ')'	{
												$$=$2;
											}
						|  condicion ')'	{
												//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' en la condicion del IF.");
											}
						| '(' condicion 	{
												//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' en la condicion del IF.");
											}
;

sentencia_control		: CASE '(' ID ')' '{' lista_opciones '}' ','	{
																			//System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una sentencia CASE.");
																			Token tk = TablaSimbolos.getSimbolo($3.sval);
																			if (tk.getTipo() == "sin definir")
																				System.out.println("Linea " + al.getNroLinea() + ": (ArbSin) Variable no declarada en CASE.");
																			Nodo aux = new Nodo($3.sval,tk.getTipo());
																			//String tipo = (String)(((Nodo)$6.obj).getTipo());
																			$$ =  new ParserVal(new Nodo((Nodo)(new ParserVal(aux)).obj, (Nodo)$6.obj, "CASE","integer"));
																		}					
						| error '(' ID ')' '{' lista_opciones '}' ','	{
																			//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'case'.");
																		}
						| CASE  ID ')' '{' lista_opciones '}' ','	{
																		//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' en la sentencia CASE.");
																	}
						| CASE '(' error ')' '{' lista_opciones '}' ','	{
																			//System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba un identificador en la sentencia CASE.");
																		}
						| CASE '(' ID  '{' lista_opciones '}' ','	{
																		//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' en la sentencia CASE.");
																	}
						| CASE '(' ID ')'  lista_opciones '}' ','	{
																		//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '{' en la sentencia CASE.");
																	}
						| CASE '(' ID ')' '{' error '}' ','	{
																//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la lista de opciones en la sentencia CASE.");
															}
						| CASE '(' ID ')' '{' lista_opciones  ','	{
																		//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '}' en la sentencia CASE.");
																	}
						| CASE '(' ID ')' '{' lista_opciones '}' error  {
																			//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la sentencia CASE.");
																		}
						| CASE ID '{' lista_opciones '}' ','	{
																	//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '()' en la sentencia CASE.");
																}
						| CASE '(' ID ')'  lista_opciones  ','	{
																	//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '{}' en la sentencia CASE.");
																}
;

lista_opciones			: lista_opciones opcion {
													$$ = new ParserVal(new Nodo((Nodo)$1.obj, (Nodo)$2.obj, "LISTA_OPCIONES",null));
												}
						| opcion{
									$$ = $1;
								}
;

opcion					: cte ':' DO bloque_control {
														$$ = new ParserVal(new Nodo((Nodo)$1.obj, (Nodo)$4.obj, "DO",null));
													}
						| error ':' DO bloque_control	{
															System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una constante en una opcion.");
														}
						| cte DO bloque_control	{
													System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ':' en una opcion.");
												}
						| cte ':' error bloque_control	{
															System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra resrevada 'DO'.");
														}
;

sentencia_impresion   	: PRINT '(' CADENA ')' ','	{
														//System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocio una impresion por pantalla.");
														Token tk = TablaSimbolos.getSimbolo($3.sval);
														Nodo print = new Nodo($3.sval,tk.getTipo());
														$$ = new ParserVal(new Nodo(print,null,"PRINT",null));
													}
						| error '(' CADENA ')' ','	{
														System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'print'.");
													}
						| PRINT  CADENA ')' ','	{
													System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' en la salida por pantalla.");
												}
						| PRINT '(' error ')' ','	{
														System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una cadena en una salidad por pantalla.");
													}
						| PRINT '(' CADENA  ','	{
													System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' en la salida por pantalla..");
												}
						| PRINT '(' CADENA ')' error	{
															System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la salida por pantalla.");
														}
						| PRINT  CADENA ','	{
												System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba '()' en la salida por pantalla..");
											}
;

sentencia_asignacion	: ID ASIGN expresiones ','	{
														//System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una asignacion.");
														
														Token tk = TablaSimbolos.getSimbolo($1.sval);
														if (tk.getTipo() == "sin definir")
															System.out.println("Linea " + al.getNroLinea() + ": (ArbSin) Variable no declarada.");

														String tipo = (String)(((Nodo)$3.obj).getTipo());
														Nodo aux = new Nodo($1.sval,tk.getTipo());

														if (!(aux.getTipo()).equals(tipo)){
															System.out.println("Linea " + al.getNroLinea() + ": (ArbSin) Error de tipos en asignacion.");
															//System.out.println("tipo de 3 " + tipo + "tipo de 1 " + aux.getTipo());
														}
														$$ =  new ParserVal(new Nodo((Nodo)(new ParserVal(aux)).obj, (Nodo)$3.obj, ":=",tipo));
														//System.out.println("se crea una asignacion con "+$1.sval+" y "+((Nodo)$3.obj).getNombre());
													}
						| ID ASIGN ID '(' ')' ','	{
														//System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una asignacion.");
													}
						| error ASIGN expresiones ','	{
															//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el identificador en la asignacion.");
														}
						| error ASIGN ID '(' ')' ',' 	{
															//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el identificador en la asignacion.");
														}
						| ID expresiones ',' 	{
													//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el operador de asignacion.");
												}
						| ID ID '(' ')' ',' 	{
													//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el operador de asignacion.");
												}
						| ID ASIGN expresiones error 	{
															//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de una asignacion.");
														}
						| ID ASIGN ID '(' ')' error	{
														//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de una asignacion.");
													}
						| ID ASIGN error ',' 	{
													//System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una expresion o funcion.");
												}
;
					  
condicion             	: expresiones comparador expresiones	{
																	String tipo = (String)((Nodo)($3.obj)).getTipo();
																	if ((((Nodo)$1.obj).getTipo()).equals(tipo))
																		$$ =  new ParserVal(new Nodo((Nodo)$1.obj, (Nodo)$3.obj, ((Nodo)$2.obj).getNombre(),tipo));
																	else{
																		System.out.println("Linea " + al.getNroLinea() + ": (ArbSin) Error de tipos en condicion.");
																		$$ =  new ParserVal(new Nodo((Nodo)$1.obj, (Nodo)$3.obj, ((Nodo)$2.obj).getNombre(),tipo));
																	}
																}
						| expresiones comparador error	{
															//System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una expresion en el lado derecho de la condicion.");
														}
                      	| error comparador expresiones	{
                      										//System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una expresion en el lado izquierdo de la condicion.");
                  										}
                      	| expresiones error expresiones {
                      										//System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba un comparador entre las expresiones de la condicion.");
                  										}
                      	| error	{
                      				//System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una condicion.");
                  				}
;

comparador            	: '<'			{
											$$ = new ParserVal (new Nodo($1.sval,null));
										}
					  	| '>'			{
											$$ = new ParserVal (new Nodo($1.sval,null));
										}
					  	| '='			{
											$$ = new ParserVal (new Nodo($1.sval,null));
										}
					  	| MENOR_IGUAL	{
											$$ = new ParserVal (new Nodo($1.sval,null));
										}
					  	| MAYOR_IGUAL	{
											$$ = new ParserVal (new Nodo($1.sval,null));
										}
					  	| DISTINTO		{
											$$ = new ParserVal (new Nodo($1.sval,null));
										}
;

expresiones				: expresion	{
										$$ = $1;
										//System.out.println("de expresion a expresiones con "+((Nodo)$1.obj).getNombre());
									}
						| conversion_explicita	{
													$$ = $1;	
												}
;

conversion_explicita	: tipo '(' expresion ')'	{
														//System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una conversion explicita.");
														$$ = new ParserVal(new Nodo ((Nodo)$3.obj,null,"linteger","integer"));
													}
						| tipo '(' error ')'	{
													//System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una expresion en la conversion.");
												}
						| tipo  expresion ')' 	{
													//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' en la conversion.");
												}
						| tipo '(' expresion error	{
														//System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' en la conversion.");
													}
;

expresion				: expresion '+' termino	{
													String tipo = (((Nodo)$3.obj).getTipo());
													if ((((Nodo)$1.obj).getTipo()).equals(tipo)){
														$$ =  new ParserVal(new Nodo((Nodo)$1.obj, (Nodo)$3.obj, "+",tipo));
														//System.out.println("de suma a expresion con "+((Nodo)$1.obj).getNombre()+" y "+((Nodo)$3.obj).getNombre());	
													}		
													else {
														System.out.println("Linea " + al.getNroLinea() + ": (ArbSin) Tipos incompatible en +.");
														$$ =  new ParserVal(new Nodo((Nodo)$1.obj, (Nodo)$3.obj, "+",null)); 
													}
												}
						| expresion '-' termino {
													String tipo = (((Nodo)$3.obj).getTipo());
													if ((((Nodo)$1.obj).getTipo()).equals(tipo))
														$$ =  new ParserVal(new Nodo((Nodo)$1.obj, (Nodo)$3.obj, "-",tipo));													
													else {
														System.out.println("Linea " + al.getNroLinea() + ": (ArbSin) Tipos incompatible en -.");
														$$ =  new ParserVal(new Nodo((Nodo)$1.obj, (Nodo)$3.obj, "-",null)); 
													}
												}
						| termino				{
													$$ = $1;
													//System.out.println("- de termino a expresion con "+((Nodo)$1.obj).getNombre());
												}
;

termino					: termino '*' factor	{
													String tipo = (((Nodo)$3.obj).getTipo());
													if ((((Nodo)$1.obj).getTipo()).equals(tipo))
														$$ =  new ParserVal(new Nodo((Nodo)$1.obj, (Nodo)$3.obj, "*",tipo));
													else {
														System.out.println("Linea " + al.getNroLinea() + ": (ArbSin) Tipos incompatible en *.");
														$$ =  new ParserVal(new Nodo((Nodo)$1.obj, (Nodo)$3.obj, "*",null)); 
													}
												}
						| termino '/' factor 	{
													String tipo = (((Nodo)$3.obj).getTipo());
													if ((((Nodo)$1.obj).getTipo()).equals(tipo))
														$$ =  new ParserVal(new Nodo((Nodo)$1.obj, (Nodo)$3.obj, "/",tipo));
													else {
														//lex.addError("Linea " + lex.getNroLinea() + ": ERROR de tipos en expresion aritmetica");
														$$ =  new ParserVal(new Nodo((Nodo)$1.obj, (Nodo)$3.obj, "/",null)); 
													}
												}
						| factor 	{
										$$ = $1;
										//System.out.println("- de factor a termino con "+((Nodo)$1.obj).getNombre());
									}
;

factor 					: ID 	{
									Token tk = TablaSimbolos.getSimbolo($1.sval);
									Nodo nodo = new Nodo($1.sval,tk.getTipo());
									$$ = new ParserVal(nodo);
									//System.out.println("- nuevo ID con valor "+$1.sval+" tipo ID");
									//System.out.println("- nuevo nodo "+nodo.getNombre()+" tipo "+nodo.getTipo());

									if (tk.getTipo() == "sin definir")
										System.out.println("Linea " + al.getNroLinea() + ": (ArbSin) Variable no declarada.");
								}
						| cte
;

cte 					: CTE 	{
									String cte = $1.sval;
									verificar_rango(cte, (long) Math.pow(2, 15));
									$$ = new ParserVal (new Nodo($1.sval, "integer"));
									Token tk = TablaSimbolos.getSimbolo($1.sval);
									tk.setTipo("integer");
								}
						| CTE_LARGA {
										String cte = $1.sval;
										verificar_rango(cte, (long) Math.pow(2, 31));
										$$ = new ParserVal (new Nodo($1.sval, "linteger"));
										Token tk = TablaSimbolos.getSimbolo($1.sval);
										tk.setTipo("linteger");
									}
						| '-' CTE 	{
										String cte = $2.sval;
										verificar_negativo(cte);
										$$ = new ParserVal (new Nodo('-'+$1.sval, "integer"));
										Token tk = TablaSimbolos.getSimbolo($1.sval);
										tk.setTipo("integer");
									}
						| '-' CTE_LARGA {
											String cte = $2.sval;
											verificar_negativo(cte);
											$$ = new ParserVal (new Nodo('-'+$1.sval, "linteger"));
											Token tk = TablaSimbolos.getSimbolo($1.sval);
											tk.setTipo("linteger");
										}
;

%%

private AnalizadorLexico al;

public void verificar_rango(String cte, long rango) {
    if ( Long.parseLong(cte) == rango ) {
		System.out.println("Linea " + al.getNroLinea() + ": (AS) WARNING: Constante fuera del rango permitido. (integer = "+ cte +")");
		System.out.println("Linea " + al.getNroLinea() + ": (AS) WARNING: Se le asignara el mayor valor permitido. ("+ Long.toString(rango-1) +")");
		TablaSimbolos.modificarLexema(cte, Long.toString(rango-1));
	}
}

public void verificar_negativo(String cte) {
	String nuevoLex = "-" + cte;
	Token viejo = TablaSimbolos.getSimbolo(cte);
	if( !TablaSimbolos.contiene(nuevoLex)){
		Token t = new Token(viejo.getIdentificador(), nuevoLex, viejo.getDescripcion() + " negativa");
		TablaSimbolos.addSimbolo(t);
	}
	int contador = Integer.parseInt(viejo.getAtributo("contador")) - 1 ;
	if( contador == 0) {
		TablaSimbolos.remove(viejo.getLexema());
		System.out.println("constante borrada");
	} else {
		viejo.addAtributo("contador",String.valueOf(contador));
	}	
}

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

public void setearTipo( String tipo, Nodo variable ){
	if (variable != null){
		setearTipo(tipo,variable.getNodoIzq());
		if (variable.esHoja()){
			Token tk = TablaSimbolos.getSimbolo(variable.getNombre());
			tk.setTipo(tipo);
		}
		setearTipo(tipo,variable.getNodoDer());
	}
	
}

public void cargarArbol(Nodo inicial){	
    try {
        FileWriter fileArbol;
        fileArbol = new FileWriter("Arbol.txt");
        PrintWriter writeArbol = new PrintWriter(fileArbol);
		imprimirArbol(writeArbol, inicial,"");	
        fileArbol.close();
    } catch (IOException ex) {
        Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
    }
}

public static  void imprimirArbol(PrintWriter wr,Nodo inicial, String sangria){
	if (inicial != null){
		String tipo = "";
		if(inicial.getTipo()!=null)
			tipo = " (Tipo: "+inicial.getTipo()+")";
		wr.print(sangria + inicial.getNombre()+tipo+"\r\n");
		imprimirArbol(wr,inicial.getNodoIzq(),sangria + "	");
		imprimirArbol(wr, inicial.getNodoDer(),sangria + "	");
	}
	
}