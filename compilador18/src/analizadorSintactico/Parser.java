//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 1 "gramatica.y"
package analizadorSintactico;
  import analizadorLexico.*;
  import globales.*;
  import java.util.Stack;
//#line 22 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
public static ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short IF=257;
public final static short ELSE=258;
public final static short END_IF=259;
public final static short PRINT=260;
public final static short INTEGER=261;
public final static short LINTEGER=262;
public final static short CASE=263;
public final static short DO=264;
public final static short VOID=265;
public final static short FUN=266;
public final static short RETURN=267;
public final static short ID=268;
public final static short CADENA=269;
public final static short CTE=270;
public final static short CTE_LARGA=271;
public final static short MAYOR_IGUAL=272;
public final static short MENOR_IGUAL=273;
public final static short DISTINTO=274;
public final static short ASIGN=275;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    1,    4,    4,    3,    3,    6,
    6,    6,    6,    7,    7,    7,    7,    7,    8,    8,
    9,    9,    9,    9,    9,    9,   10,   11,   12,    5,
    5,    5,    5,   13,   13,   14,   14,    2,    2,    2,
    2,    2,   15,   15,   15,   15,   15,   15,   15,   15,
   15,   19,   19,   19,   16,   16,   16,   16,   16,   16,
   16,   16,   16,   16,   16,   21,   21,   22,   22,   22,
   22,   17,   17,   17,   17,   17,   17,   17,   18,   18,
   18,   18,   18,   18,   18,   18,   18,   20,   20,   20,
   20,   20,   25,   25,   25,   25,   25,   25,   24,   24,
   27,   27,   27,   27,   26,   26,   26,   28,   28,   28,
   29,   29,   23,   23,   23,   23,
};
final static short yylen[] = {                            2,
    1,    2,    2,    1,    1,    4,    1,    1,    1,    2,
    2,    2,    2,    8,    7,    8,    7,    7,    3,    2,
    4,    4,    3,    3,    4,    2,    1,    1,    3,    3,
    3,    3,    3,    1,    1,    3,    1,    1,    1,    1,
    1,    1,    5,    7,    5,    5,    5,    7,    7,    7,
    7,    3,    2,    2,    8,    8,    7,    8,    7,    7,
    8,    7,    8,    6,    6,    2,    1,    4,    4,    3,
    4,    5,    5,    4,    5,    4,    5,    3,    4,    6,
    4,    6,    3,    5,    4,    6,    4,    3,    3,    3,
    3,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    4,    4,    3,    4,    3,    3,    1,    3,    3,    1,
    1,    1,    1,    1,    2,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   34,   35,    0,    0,    0,    0,    0,
    0,    4,    5,    8,    9,   42,    0,   38,   39,   40,
   41,    0,    0,  113,  114,    0,    0,    0,    0,    0,
    0,    0,  112,    0,    0,  100,    0,  110,  111,    0,
    0,    0,    0,    0,    0,    0,    0,   12,   11,    0,
   10,   13,    0,    0,    0,    0,    2,    3,    0,   37,
    0,   97,   96,   98,   93,   94,   95,    0,    0,    0,
  115,  116,    0,    0,    0,    0,    0,   31,    0,    0,
    0,    7,    0,   53,    0,    0,    0,    0,    0,    0,
    0,    0,   78,    0,    0,    0,    0,    0,    0,    0,
   26,    0,    0,    0,   28,    0,   20,    0,    0,    0,
    0,   29,   83,   32,   33,   30,   90,    0,   81,    0,
    0,   52,    0,    0,  103,   36,    0,    0,    0,   91,
   89,   88,    0,    0,  108,  109,    0,    0,    0,   74,
    0,    0,   76,    0,    0,   67,    0,    0,    0,    0,
    0,    0,   24,    0,   23,    0,    0,    0,   19,    0,
   87,    0,   85,   79,    0,    0,   73,  102,  104,  101,
    0,    0,   45,   46,    0,    0,   47,   43,   75,   77,
   72,    0,    0,   66,    0,    0,    0,    0,    0,    0,
    0,   22,   25,   21,    0,    0,    0,    0,   84,    0,
   82,    0,    6,    0,    0,    0,    0,    0,   64,   70,
    0,    0,    0,    0,    0,    0,    0,    0,   65,    0,
    0,    0,    0,   86,   80,    0,   48,   49,   50,   51,
   44,   69,   71,   68,   57,    0,   59,    0,    0,   62,
   60,    0,    0,    0,    0,   56,   58,   61,   63,   55,
   18,    0,    0,   17,   15,   16,   14,
};
final static short yydgoto[] = {                         10,
  195,   82,   13,   83,   14,   15,   48,   49,   50,  196,
  107,   16,   29,   30,   18,   19,   20,   21,   31,   32,
  145,  146,   33,   34,   68,   35,   36,   37,   38,
};
final static short yysindex[] = {                       322,
  -29,   98,  -33,    0,    0,  -34, -216, -216,   -7,    0,
  322,    0,    0,    0,    0,    0, -215,    0,    0,    0,
    0,  -25,    0,    0,    0,  160, -220,  123,   15,  -10,
  -79,  -17,    0,   38,  127,    0,   72,    0,    0,  134,
  -79,  112, -227,  -28, -197,   18,  -22,    0,    0,  294,
    0,    0,   26,  145,  -11,   61,    0,    0,   83,    0,
  -42,    0,    0,    0,    0,    0,    0,  172,   91,  101,
    0,    0,  124,  130,  132,  118,  103,    0,  -92,  -18,
  -79,    0, -184,    0,  172,  156,  140,  140,  140,  140,
 -162,  136,    0,  146,  116,  167,   63,  147,  -21,  151,
    0,  -20,   71,  162,    0,  115,    0,  163,  164,  166,
  -41,    0,    0,    0,    0,    0,    0,  168,    0,   84,
  169,    0,  170,  -31,    0,    0, -180,  -79,  174,    0,
    0,    0,   72,   72,    0,    0,  216,  -79,  -36,    0,
  175,   -5,    0,  165,   78,    0,  -54,  167,  106,  167,
  192,  122,    0, -114,    0,  322,  186,  322,    0,  212,
    0,  226,    0,    0,  227,  167,    0,    0,    0,    0,
  154,   19,    0,    0,   29,  -98,    0,    0,    0,    0,
    0,    8,  245,    0,  -79, -208,  232,  167,  237,  179,
   34,    0,    0,    0,  322,  250,  364,  254,    0,   10,
    0,  272,    0,  255,  256,  257,   13,  -79,    0,    0,
  -79,  -79,  269,  278,  288,  -32,   37,  293,    0,  299,
  -40,  275,  300,    0,    0,  306,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  311,    0,  312,   17,    0,
    0,  197,  314,  -12,  197,    0,    0,    0,    0,    0,
    0,  197,  197,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  361,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   70,   73,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   68,    0,    2,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  153,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  153,    0,
    0,    0,   79,    0,  198,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  107,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   24,   46,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  323,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   23,  409,    4,  388,    0,    0,  357,  359,    0, -127,
  -78,    0,  414,  353,    0,    0,    0,    0,  371,   64,
  339,  307,  426,  427,  365,   -4,    0,   49,   93,
};
final static int YYTABLESIZE=643;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         28,
  243,  116,  164,  186,   27,   45,   43,  178,  194,  170,
   28,   87,   97,   88,   58,   27,   79,  102,  103,  151,
  154,   28,   11,   84,   77,  182,   27,  159,   94,  112,
  198,  253,   55,   78,   65,   67,   66,   27,  181,   46,
   59,   95,  107,   81,  107,  107,  107,  211,   79,   71,
   72,   47,   60,  225,   76,  212,  231,  100,   98,   27,
  250,  107,  107,  107,  105,  108,  105,  105,  105,  222,
   99,  124,  106,  128,  129,   80,    2,  219,   27,    3,
  240,   27,    6,  105,  105,  105,  106,    9,  106,  106,
  106,   75,  238,  137,   96,  138,  139,   65,   67,   66,
  101,  150,  153,   75,  113,  106,  106,  106,   99,   58,
   92,   99,  105,   89,  111,  111,   37,  111,   90,  111,
  111,  111,   27,  111,  107,  111,  114,   99,   99,   99,
  118,   37,  111,  111,  111,  133,  134,   40,  111,  111,
  111,  193,   27,  125,  119,   87,  105,   88,  111,  111,
  111,  111,   92,  111,  158,   93,  142,  206,  218,  143,
  207,  239,   27,  251,  120,  254,  255,   27,  106,   87,
  121,   88,  122,  256,  257,  126,   80,    2,   27,  140,
    3,  135,  136,    6,   27,  148,  141,  149,    9,   27,
   99,  152,   92,  155,  111,  111,  111,  111,   58,  111,
   27,  156,  183,  160,   27,  162,  166,  161,  165,  185,
  168,   27,  167,  115,  163,   22,   27,  173,  179,  177,
    4,    5,  182,   27,  169,  197,   22,   23,  188,   24,
   25,    4,    5,   44,   26,   42,   27,   22,   23,  105,
   24,   25,    4,    5,  192,   26,   62,   63,   64,   39,
  180,   24,   25,    4,    5,  199,   26,  107,  107,  174,
   53,  107,   24,   25,  107,  224,  200,   54,  230,  107,
  201,  208,  249,  107,  107,  107,   27,  204,  203,  105,
  105,   27,   39,  105,   24,   25,  105,  205,  209,  144,
  220,  105,  144,   85,  223,  105,  105,  105,  227,  228,
  229,  106,  106,   24,   25,  106,   24,   25,  106,   62,
   63,   64,  235,  106,  190,  244,   27,  106,  106,  106,
   54,  105,   27,   99,   99,   92,   92,   99,  111,   92,
   99,  237,   92,  144,  111,   99,  241,   92,   81,   99,
   99,   99,  242,  245,  111,  111,  111,   24,   25,  246,
  111,  111,  111,   22,  247,  248,  213,  252,    4,    5,
    1,  215,  111,   27,   51,   39,   52,   24,   25,   61,
    1,    2,   41,  123,    3,    4,    5,    6,   22,    7,
    8,  157,    9,    4,    5,   39,    0,   24,   25,   22,
   73,   74,   24,   25,    4,    5,  226,    0,   86,    0,
  109,   39,  236,   24,   25,    4,    5,   39,   12,   24,
   25,  131,  110,   17,   24,   25,    4,    5,  105,   57,
    4,    5,  144,   39,   17,   24,   25,   69,   91,   24,
   25,    0,    4,    5,  216,   56,   24,   25,    0,   39,
    0,   24,   25,    0,    0,    0,    0,  144,   24,   25,
    0,  184,   70,   54,   54,    0,    0,   54,   12,    0,
   54,   24,   25,   17,    0,   54,    0,    0,  127,    0,
    0,   80,    2,    0,    0,    3,    0,    0,    6,    0,
  111,    0,    0,    9,    0,    0,  187,  144,  189,  191,
    0,    0,  144,  184,  117,  184,    0,  184,    0,    0,
    0,   24,   25,    0,  202,    0,   24,   25,  184,    0,
    0,  130,  132,    0,   57,  172,    0,    0,    0,   17,
  184,  147,    0,  184,  175,  176,  214,  144,  217,    0,
    0,    0,    0,  144,    0,  171,    0,    0,    0,    0,
    0,   24,   25,    0,    0,    0,    0,   24,   25,    1,
    2,    0,    0,    3,    4,    5,    6,    0,    7,    8,
  104,    9,    0,    0,   12,    0,   12,    0,    0,   17,
  147,   17,  210,  147,    0,  147,  147,    1,    2,    0,
    0,    3,    4,    5,    6,    0,    7,    8,    0,    9,
    0,  147,    0,    0,    0,  232,    0,    0,  233,  234,
    0,    0,    0,   57,    0,   12,    0,    0,   17,    0,
   17,    0,  147,  147,  147,  147,  147,    0,    0,  221,
    2,    0,    0,    3,    4,    5,    6,  147,    7,    8,
    0,    9,    0,    0,    0,    0,    0,    0,    0,  147,
    0,    0,  147,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   41,   44,   44,   58,   45,   40,   40,   44,  123,   41,
   40,   43,   41,   45,   11,   45,   59,   40,   41,   41,
   41,   40,    0,   41,   29,   58,   45,  106,  256,   41,
  158,   44,   40,   44,   60,   61,   62,   45,   44,  256,
  256,  269,   41,  123,   43,   44,   45,  256,   59,  270,
  271,  268,  268,   44,   40,  264,   44,   40,  256,   45,
   44,   60,   61,   62,   41,   40,   43,   44,   45,  197,
  268,   76,   50,  258,  259,  256,  257,   44,   45,  260,
   44,   45,  263,   60,   61,   62,   41,  268,   43,   44,
   45,   28,  125,  256,  123,  258,  259,   60,   61,   62,
  123,  123,  123,   40,   44,   60,   61,   62,   41,  106,
   41,   44,  125,   42,   42,   43,   44,   45,   47,   47,
   42,   43,   45,   45,  123,   47,   44,   60,   61,   62,
   40,   59,   60,   61,   62,   87,   88,   40,   60,   61,
   62,  256,   45,   41,   44,   43,  123,   45,   42,   43,
   44,   45,   41,   47,   40,   44,   41,  256,  125,   44,
  259,  125,   45,  242,   41,  244,  245,   45,  123,   43,
   41,   45,   41,  252,  253,  268,  256,  257,   45,   44,
  260,   89,   90,  263,   45,  123,   41,   41,  268,   45,
  123,   41,  123,  123,   42,   43,   44,   45,  195,   47,
   45,   40,  125,   41,   45,   40,  123,   44,   41,  264,
   41,   45,   44,  256,  256,  256,   45,   44,   44,  256,
  261,  262,   58,   45,  256,   40,  256,  268,  123,  270,
  271,  261,  262,  268,  275,  269,   45,  256,  268,  125,
  270,  271,  261,  262,  123,  275,  272,  273,  274,  268,
  256,  270,  271,  261,  262,   44,  275,  256,  257,   44,
  268,  260,  270,  271,  263,  256,   41,  275,  256,  268,
   44,  264,  256,  272,  273,  274,   45,  259,  125,  256,
  257,   45,  268,  260,  270,  271,  263,  259,   44,  256,
   41,  268,  256,  256,   41,  272,  273,  274,   44,   44,
   44,  256,  257,  270,  271,  260,  270,  271,  263,  272,
  273,  274,   44,  268,  123,   41,   45,  272,  273,  274,
  123,  125,   45,  256,  257,  256,  257,  260,  256,  260,
  263,   44,  263,  256,  256,  268,   44,  268,  123,  272,
  273,  274,   44,   44,  272,  273,  274,  270,  271,   44,
  272,  273,  274,  256,   44,   44,  125,   44,  261,  262,
    0,  125,  256,   41,    8,  268,    8,  270,  271,   17,
  256,  257,    2,  256,  260,  261,  262,  263,  256,  265,
  266,  267,  268,  261,  262,  268,   -1,  270,  271,  256,
  268,  269,  270,  271,  261,  262,  125,   -1,   34,   -1,
  256,  268,  125,  270,  271,  261,  262,  268,    0,  270,
  271,  256,  268,    0,  270,  271,  261,  262,  125,   11,
  261,  262,  256,  268,   11,  270,  271,  268,   41,  270,
  271,   -1,  261,  262,  256,    9,  270,  271,   -1,  268,
   -1,  270,  271,   -1,   -1,   -1,   -1,  256,  270,  271,
   -1,  145,   26,  256,  257,   -1,   -1,  260,   50,   -1,
  263,  270,  271,   50,   -1,  268,   -1,   -1,   81,   -1,
   -1,  256,  257,   -1,   -1,  260,   -1,   -1,  263,   -1,
   54,   -1,   -1,  268,   -1,   -1,  148,  256,  150,  151,
   -1,   -1,  256,  187,   68,  189,   -1,  191,   -1,   -1,
   -1,  270,  271,   -1,  166,   -1,  270,  271,  202,   -1,
   -1,   85,   86,   -1,  106,  128,   -1,   -1,   -1,  106,
  214,   96,   -1,  217,  137,  138,  188,  256,  190,   -1,
   -1,   -1,   -1,  256,   -1,  127,   -1,   -1,   -1,   -1,
   -1,  270,  271,   -1,   -1,   -1,   -1,  270,  271,  256,
  257,   -1,   -1,  260,  261,  262,  263,   -1,  265,  266,
  267,  268,   -1,   -1,  156,   -1,  158,   -1,   -1,  156,
  145,  158,  185,  148,   -1,  150,  151,  256,  257,   -1,
   -1,  260,  261,  262,  263,   -1,  265,  266,   -1,  268,
   -1,  166,   -1,   -1,   -1,  208,   -1,   -1,  211,  212,
   -1,   -1,   -1,  195,   -1,  197,   -1,   -1,  195,   -1,
  197,   -1,  187,  188,  189,  190,  191,   -1,   -1,  256,
  257,   -1,   -1,  260,  261,  262,  263,  202,  265,  266,
   -1,  268,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  214,
   -1,   -1,  217,
};
}
final static short YYFINAL=10;
final static short YYMAXTOKEN=275;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"IF","ELSE","END_IF","PRINT","INTEGER",
"LINTEGER","CASE","DO","VOID","FUN","RETURN","ID","CADENA","CTE","CTE_LARGA",
"MAYOR_IGUAL","MENOR_IGUAL","DISTINTO","ASIGN",
};
final static String yyrule[] = {
"$accept : programa",
"programa : bloque_sentencias",
"bloque_sentencias : bloque_sentencias sentencia_ejecutable",
"bloque_sentencias : bloque_sentencias sentencia_declarativa",
"bloque_sentencias : sentencia_ejecutable",
"bloque_sentencias : sentencia_declarativa",
"bloque_control : '{' bloque_control sentencia_ejecutable '}'",
"bloque_control : sentencia_ejecutable",
"sentencia_declarativa : declaracion_variables",
"sentencia_declarativa : funcion",
"funcion : FUN funcion_fun",
"funcion : VOID funcion_void",
"funcion : VOID funcion_fun",
"funcion : FUN funcion_void",
"funcion_fun : comienzo_funcion bloque_sentencias RETURN '(' retorno ')' ',' fin_funcion",
"funcion_fun : comienzo_funcion bloque_sentencias '(' retorno ')' ',' fin_funcion",
"funcion_fun : comienzo_funcion bloque_sentencias RETURN '(' error ')' ',' fin_funcion",
"funcion_fun : comienzo_funcion bloque_sentencias RETURN '(' retorno ')' fin_funcion",
"funcion_fun : comienzo_funcion RETURN '(' retorno ')' ',' fin_funcion",
"funcion_void : comienzo_funcion bloque_sentencias fin_funcion",
"funcion_void : comienzo_funcion fin_funcion",
"comienzo_funcion : ID '(' ')' '{'",
"comienzo_funcion : error '(' ')' '{'",
"comienzo_funcion : ID ')' '{'",
"comienzo_funcion : ID '(' '{'",
"comienzo_funcion : ID '(' ')' error",
"comienzo_funcion : ID '{'",
"retorno : bloque_sentencias",
"fin_funcion : '}'",
"llamado_funcion : ID '(' ')'",
"declaracion_variables : tipo lista_de_variables ','",
"declaracion_variables : error lista_de_variables ','",
"declaracion_variables : tipo error ','",
"declaracion_variables : tipo lista_de_variables error",
"tipo : INTEGER",
"tipo : LINTEGER",
"lista_de_variables : lista_de_variables ';' ID",
"lista_de_variables : ID",
"sentencia_ejecutable : sentencia_seleccion",
"sentencia_ejecutable : sentencia_control",
"sentencia_ejecutable : sentencia_impresion",
"sentencia_ejecutable : sentencia_asignacion",
"sentencia_ejecutable : llamado_funcion",
"sentencia_seleccion : IF condicion_IF bloque_control END_IF ','",
"sentencia_seleccion : IF condicion_IF bloque_control ELSE bloque_control END_IF ','",
"sentencia_seleccion : error condicion_IF bloque_control END_IF ','",
"sentencia_seleccion : IF condicion_IF bloque_control error ','",
"sentencia_seleccion : IF condicion_IF bloque_control END_IF error",
"sentencia_seleccion : error condicion_IF bloque_control ELSE bloque_control END_IF ','",
"sentencia_seleccion : IF condicion_IF bloque_control error bloque_control END_IF ','",
"sentencia_seleccion : IF condicion_IF bloque_control ELSE bloque_control error ','",
"sentencia_seleccion : IF condicion_IF bloque_control ELSE bloque_control END_IF error",
"condicion_IF : '(' condicion ')'",
"condicion_IF : condicion ')'",
"condicion_IF : '(' condicion",
"sentencia_control : CASE '(' ID ')' '{' lista_opciones '}' ','",
"sentencia_control : error '(' ID ')' '{' lista_opciones '}' ','",
"sentencia_control : CASE ID ')' '{' lista_opciones '}' ','",
"sentencia_control : CASE '(' error ')' '{' lista_opciones '}' ','",
"sentencia_control : CASE '(' ID '{' lista_opciones '}' ','",
"sentencia_control : CASE '(' ID ')' lista_opciones '}' ','",
"sentencia_control : CASE '(' ID ')' '{' error '}' ','",
"sentencia_control : CASE '(' ID ')' '{' lista_opciones ','",
"sentencia_control : CASE '(' ID ')' '{' lista_opciones '}' error",
"sentencia_control : CASE ID '{' lista_opciones '}' ','",
"sentencia_control : CASE '(' ID ')' lista_opciones ','",
"lista_opciones : lista_opciones opcion",
"lista_opciones : opcion",
"opcion : cte ':' DO bloque_control",
"opcion : error ':' DO bloque_control",
"opcion : cte DO bloque_control",
"opcion : cte ':' error bloque_control",
"sentencia_impresion : PRINT '(' CADENA ')' ','",
"sentencia_impresion : error '(' CADENA ')' ','",
"sentencia_impresion : PRINT CADENA ')' ','",
"sentencia_impresion : PRINT '(' error ')' ','",
"sentencia_impresion : PRINT '(' CADENA ','",
"sentencia_impresion : PRINT '(' CADENA ')' error",
"sentencia_impresion : PRINT CADENA ','",
"sentencia_asignacion : ID ASIGN expresiones ','",
"sentencia_asignacion : ID ASIGN ID '(' ')' ','",
"sentencia_asignacion : error ASIGN expresiones ','",
"sentencia_asignacion : error ASIGN ID '(' ')' ','",
"sentencia_asignacion : ID expresiones ','",
"sentencia_asignacion : ID ID '(' ')' ','",
"sentencia_asignacion : ID ASIGN expresiones error",
"sentencia_asignacion : ID ASIGN ID '(' ')' error",
"sentencia_asignacion : ID ASIGN error ','",
"condicion : expresiones comparador expresiones",
"condicion : expresiones comparador error",
"condicion : error comparador expresiones",
"condicion : expresiones error expresiones",
"condicion : error",
"comparador : '<'",
"comparador : '>'",
"comparador : '='",
"comparador : MENOR_IGUAL",
"comparador : MAYOR_IGUAL",
"comparador : DISTINTO",
"expresiones : expresion",
"expresiones : conversion_explicita",
"conversion_explicita : tipo '(' expresion ')'",
"conversion_explicita : tipo '(' error ')'",
"conversion_explicita : tipo expresion ')'",
"conversion_explicita : tipo '(' expresion error",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : cte",
"cte : CTE",
"cte : CTE_LARGA",
"cte : '-' CTE",
"cte : '-' CTE_LARGA",
};

//#line 212 "gramatica.y"

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
//#line 540 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 10:
//#line 51 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una funcion FUN.");}
break;
case 11:
//#line 52 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una funcion VOID.");}
break;
case 12:
//#line 53 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Tipo de la funcion incorrecto.");}
break;
case 13:
//#line 54 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Tipo de la funcion incorrecto.");}
break;
case 15:
//#line 58 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'return' de la funcion.");}
break;
case 16:
//#line 59 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el retorno de la funcion.");}
break;
case 17:
//#line 60 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego del retorno de la funcion.");}
break;
case 18:
//#line 61 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el cuerpo de la funcion.");}
break;
case 20:
//#line 65 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el cuerpo de la funcion.");}
break;
case 22:
//#line 69 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el identificador de la funcion.");}
break;
case 23:
//#line 70 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' luego del identificador de la funcion.");}
break;
case 24:
//#line 71 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' luego del identificador de la funcion.");}
break;
case 25:
//#line 72 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '{' al comienzo de la funcion.");}
break;
case 26:
//#line 73 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '()' luego del identificador de la funcion.");}
break;
case 30:
//#line 85 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una declaracion de variables.");}
break;
case 31:
//#line 86 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el tipo en la declaracion de variables.");}
break;
case 32:
//#line 87 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el identificador o lista de identificadores en la declaracion de variables.");}
break;
case 33:
//#line 88 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la declaracion de variables.");}
break;
case 43:
//#line 107 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una sentencia IF.");}
break;
case 45:
//#line 109 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'if'.");}
break;
case 46:
//#line 110 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'end_if'.");}
break;
case 47:
//#line 111 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la sentencia IF.");}
break;
case 48:
//#line 112 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'if'.");}
break;
case 49:
//#line 113 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'else'.");}
break;
case 50:
//#line 114 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'end_if'.");}
break;
case 51:
//#line 115 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la sentencia IF.");}
break;
case 53:
//#line 119 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' en la condicion del IF.");}
break;
case 54:
//#line 120 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' en la condicion del IF.");}
break;
case 55:
//#line 123 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una sentencia CASE.");}
break;
case 56:
//#line 124 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'case'.");}
break;
case 57:
//#line 125 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' en la sentencia CASE.");}
break;
case 58:
//#line 126 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba un identificador en la sentencia CASE.");}
break;
case 59:
//#line 127 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' en la sentencia CASE.");}
break;
case 60:
//#line 128 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '{' en la sentencia CASE.");}
break;
case 61:
//#line 129 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la lista de opciones en la sentencia CASE.");}
break;
case 62:
//#line 130 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '}' en la sentencia CASE.");}
break;
case 63:
//#line 131 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la sentencia CASE.");}
break;
case 64:
//#line 132 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '()' en la sentencia CASE.");}
break;
case 65:
//#line 133 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '{}' en la sentencia CASE.");}
break;
case 69:
//#line 141 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una constante en una opcion.");}
break;
case 70:
//#line 142 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ':' en una opcion.");}
break;
case 71:
//#line 143 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra resrevada 'DO'.");}
break;
case 72:
//#line 146 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una impresion por pantalla.");}
break;
case 73:
//#line 147 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'print'.");}
break;
case 74:
//#line 148 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' en la salida por pantalla.");}
break;
case 75:
//#line 149 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una cadena en una salidad por pantalla.");}
break;
case 76:
//#line 150 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' en la salida por pantalla..");}
break;
case 77:
//#line 151 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la salida por pantalla.");}
break;
case 78:
//#line 152 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba '()' en la salida por pantalla..");}
break;
case 79:
//#line 155 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una asignacion.");}
break;
case 80:
//#line 156 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una asignacion.");}
break;
case 81:
//#line 157 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el identificador en la asignacion.");}
break;
case 82:
//#line 158 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el identificador en la asignacion.");}
break;
case 83:
//#line 159 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el operador de asignacion.");}
break;
case 84:
//#line 160 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el operador de asignacion.");}
break;
case 85:
//#line 161 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de una asignacion.");}
break;
case 86:
//#line 162 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de una asignacion.");}
break;
case 87:
//#line 163 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una expresion o funcion.");}
break;
case 89:
//#line 167 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una expresion en el lado derecho de la condicion.");}
break;
case 90:
//#line 168 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una expresion en el lado izquierdo de la condicion.");}
break;
case 91:
//#line 169 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba un comparador entre las expresiones de la condicion.");}
break;
case 92:
//#line 170 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una condicion.");}
break;
case 100:
//#line 182 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una conversion explicita.");}
break;
case 101:
//#line 185 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una conversion explicita.");}
break;
case 102:
//#line 186 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una expresion en la conversion.");}
break;
case 103:
//#line 187 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' en la conversion.");}
break;
case 104:
//#line 188 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' en la conversion.");}
break;
case 113:
//#line 205 "gramatica.y"
{yyval = val_peek(0);}
break;
case 114:
//#line 206 "gramatica.y"
{yyval = val_peek(0);}
break;
//#line 965 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser(AnalizadorLexico al)
{ this.al = al;
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
