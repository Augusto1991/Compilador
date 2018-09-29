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
    6,    6,    6,    7,    7,    7,    7,    8,    9,    9,
    9,    9,    9,    9,   10,   11,   12,    5,    5,    5,
    5,   13,   13,   14,   14,    2,    2,    2,    2,    2,
   15,   15,   15,   15,   15,   15,   15,   15,   15,   19,
   19,   19,   16,   16,   16,   16,   16,   16,   16,   16,
   16,   16,   16,   21,   21,   22,   22,   22,   22,   22,
   22,   17,   17,   17,   17,   17,   17,   17,   18,   18,
   18,   18,   18,   18,   18,   18,   18,   20,   20,   20,
   20,   20,   25,   25,   25,   25,   25,   25,   24,   24,
   27,   27,   27,   27,   26,   26,   26,   28,   28,   28,
   29,   29,   23,   23,   23,   23,
};
final static short yylen[] = {                            2,
    1,    2,    2,    1,    1,    4,    1,    1,    1,    2,
    2,    2,    2,    9,    8,    9,    9,    4,    4,    4,
    4,    4,    4,    2,    1,    1,    3,    3,    3,    3,
    3,    1,    1,    3,    1,    1,    1,    1,    1,    1,
    5,    7,    5,    5,    5,    7,    7,    7,    7,    3,
    3,    3,    8,    8,    8,    8,    8,    8,    8,    8,
    8,    6,    6,    2,    1,    5,    5,    5,    5,    5,
    5,    5,    5,    5,    5,    5,    5,    3,    4,    6,
    4,    6,    4,    6,    4,    6,    4,    3,    3,    3,
    3,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    4,    4,    4,    4,    3,    3,    1,    3,    3,    1,
    1,    1,    1,    1,    2,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   32,   33,    0,    0,    0,    0,    0,
    0,    4,    5,    8,    9,   40,    0,   36,   37,   38,
   39,    0,    0,    0,   35,    0,    0,   12,   13,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   11,
   10,    0,    0,    0,    2,    3,    0,    0,    0,  111,
  113,  114,    0,    0,    0,  112,    0,    0,  100,    0,
  110,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   29,    0,    0,    0,    7,    0,    0,    0,   78,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   27,   30,   31,   28,   97,   96,   98,   93,   94,   95,
    0,  115,  116,    0,    0,   51,    0,    0,    0,    0,
    0,    0,    0,    0,   24,    0,    0,    0,    0,   81,
    0,    0,   52,   50,   34,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   65,    0,
    0,    0,    0,    0,   83,   87,    0,   85,   79,   90,
    0,    0,    0,   91,   89,   88,    0,    0,  108,  109,
    0,    0,    0,    0,   26,   18,    0,    0,    0,    0,
   73,    0,    0,   43,   44,    0,    0,   45,   41,   74,
   75,   76,   77,   72,    0,    0,    0,   64,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  103,  102,  104,
  101,   20,   21,   22,   23,   19,    0,    0,    0,   82,
    0,    6,    0,    0,    0,    0,    0,    0,   62,    0,
    0,    0,    0,    0,    0,    0,    0,   63,   84,   86,
   80,    0,    0,    0,    0,   46,   47,   48,   49,   42,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   54,   55,   67,   68,   69,
   70,   71,   66,   56,   57,   58,   59,   60,   61,   53,
    0,    0,    0,   15,   16,   17,   14,
};
final static short yydgoto[] = {                         10,
  208,   75,   13,   76,   14,   15,   28,   29,   64,  209,
  166,   16,   17,   30,   18,   19,   20,   21,   31,   70,
  138,  139,  140,   57,  101,   58,   59,   60,   61,
};
final static short yysindex[] = {                       212,
  -36,   41,  -33,    0,    0,   -4, -248, -214,  -37,    0,
  212,    0,    0,    0,    0,    0, -171,    0,    0,    0,
    0,   44, -154, -154,    0,   70,   33,    0,    0,   31,
 -112,   44, -112, -212,   39, -200, -191,  -30, -150,    0,
    0,   81,   55,   45,    0,    0,   69,  -34,  110,    0,
    0,    0, -111,   52,   58,    0,  103,   96,    0,    8,
    0,   76,  -32,  212,  212,   80,   89,   88,   99,   21,
    0, -146,  -35, -112,    0,  -90, -124,  106,    0,  108,
   54,  112,   98,  114,   62,  102,  136,  146,  118,  -25,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   85,    0,    0,   28,   -3,    0,   85,   66,   28,   28,
   28,   28,  120,  125,    0,   63,  -51,   25,  132,    0,
   59,  148,    0,    0,    0, -132, -112,  149,  147, -112,
   19,  154,  155,  160,   20,   90,  127,   13,    0,  -49,
  133,  151,   74,  167,    0,    0,  207,    0,    0,    0,
  438,  283,   53,    0,    0,    0,    8,    8,    0,    0,
  227,  235,  238,  -99,    0,    0,  326,  212,  323,   98,
    0,  249,  131,    0,    0,  137, -177,    0,    0,    0,
    0,    0,    0,    0,   98,  134,  342,    0,  152, -180,
   98,   98,   92,  101,  -10,  347,   57,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  229,  212,  354,    0,
  109,    0,  358,  373,  374,   73,  117, -112,    0, -112,
 -112,  -79,  122,  129,  138,  -45,  141,    0,    0,    0,
    0,  -40,  378,  379,  388,    0,    0,    0,    0,    0,
  389,  392,  394,  395,  -38,   77,  400,  401,  404,  411,
  -17,   79,  414,   83,  297,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  297,  297,  297,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  459,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   64,    0,
    0,    0,    0,    0,    0,    0,    0,   -1,    0,  -29,
    0,    0,    0,    0,    0,  384,    0,    6,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  384,    0,    0,   65,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -23,  -15,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  419,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   23,  370,  296,  327,    0,    0,  453,  458,  442,  260,
 -201,    0,  420,  465,    0,    0,    0,    0,  474,  462,
    9,  226,  398,  363,  436,   71,    0,   78,   84,
};
final static int YYTABLESIZE=578;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         27,
  253,   27,   44,   27,   27,  261,   36,  116,  190,   94,
   74,  107,  186,  107,  107,  107,   23,  105,  149,  105,
  105,  105,   11,  206,   72,  106,  268,  106,  106,  106,
  107,  107,  107,  228,   53,   39,  105,  105,  105,   99,
  186,   53,   99,   74,  106,  106,  106,  111,  111,  111,
  111,   24,  111,  274,  112,   80,   78,   53,   99,   99,
   99,  124,  179,  184,  168,  111,  111,  111,   81,  275,
  276,  277,   53,  165,   71,  221,   82,   53,  215,  250,
   32,  216,   79,  222,   47,   91,  117,  118,   53,   72,
  115,  105,   83,  201,  135,  109,   25,  110,  106,   53,
  231,   62,  143,  164,   92,   84,  111,  111,  111,  111,
   53,  111,   92,   63,   53,  113,  240,   85,   53,  119,
  263,  125,  270,   73,    2,   53,  273,    3,  121,   53,
    6,  129,  120,  130,  131,    9,   53,  187,  109,  122,
  110,  144,   53,   73,    2,   53,  132,    3,  133,  186,
    6,  195,  136,   53,  141,    9,  205,  147,  102,  103,
  161,   53,   98,  100,   99,  162,   53,  127,  128,   98,
  100,   99,  169,   53,  151,  153,  245,    2,  211,  145,
    3,  170,   53,    6,  186,   53,  157,  158,    9,  146,
  175,  171,  174,  217,  159,  160,  194,  180,  181,  223,
  224,  225,  227,  182,    1,    2,  189,  196,    3,    4,
    5,    6,  185,    7,    8,   22,    9,   22,   42,   22,
   22,   93,   34,  114,   23,   24,  107,   25,   23,   24,
  148,   25,  105,  235,   26,   35,   26,   43,   26,   26,
  106,  241,  107,  107,  107,  137,  247,  197,  105,  105,
  105,   37,  152,  248,   99,  191,  106,  106,  106,   51,
   52,  111,  249,   38,   50,  252,   51,   52,  137,   74,
   99,   99,   99,  192,  178,  183,  123,  111,  111,  111,
    1,    2,   51,   52,    3,    4,    5,    6,   49,    7,
    8,  167,    9,    4,    5,   50,   22,   51,   52,   49,
   68,   69,   51,   52,    4,    5,   46,  104,  200,  134,
   88,   50,  230,   51,   52,    4,    5,  142,  163,   92,
  111,  155,   89,  199,   51,   52,    4,    5,  239,  193,
    4,    5,  262,   50,  269,   51,   52,   66,  272,   51,
   52,    4,    5,   51,   52,    4,    5,  137,   86,  202,
   51,   52,   50,  137,   51,   52,  226,  203,  107,   77,
  204,   51,   52,  188,  137,  207,  210,   51,   52,   12,
   51,   52,  137,  212,   95,   96,   97,  137,   51,   52,
   45,   95,   96,   97,  137,  219,   51,   52,   67,  213,
  229,   51,   52,  137,  234,  214,  251,  218,   51,   52,
  126,  236,   73,    2,   87,   90,    3,   51,   52,    6,
   51,   52,   46,   46,    9,  220,  237,  238,  254,   56,
  188,  165,  255,   56,   56,  111,  111,  111,  111,   56,
  111,  256,  257,   12,   12,  258,  188,  259,  260,   56,
   56,   54,  188,  264,  265,   54,   54,  266,  188,  188,
  188,   54,  188,  173,  267,  176,  177,  271,    1,   25,
   41,   54,   54,  150,   40,   65,  233,    1,    2,  154,
  156,    3,    4,    5,    6,   33,    7,    8,  198,    9,
  109,   48,  110,   55,  232,    2,   45,   45,    3,    4,
    5,    6,  108,    7,    8,  172,    9,    0,   56,    0,
    0,   56,   56,   46,   56,   56,   56,   56,   56,   56,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   54,    0,    0,    0,    0,    0,   54,   54,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   12,    0,    0,
    0,    0,    0,    0,  242,    0,  243,  244,  246,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   12,   45,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   41,   40,   40,   40,   40,   44,   40,   40,   58,   44,
  123,   41,   58,   43,   44,   45,  265,   41,   44,   43,
   44,   45,    0,  123,   59,   41,   44,   43,   44,   45,
   60,   61,   62,   44,   45,   40,   60,   61,   62,   41,
   58,   45,   44,  123,   60,   61,   62,   42,   43,   42,
   45,  266,   47,  255,   47,  256,  269,   45,   60,   61,
   62,   41,   44,   44,   40,   60,   61,   62,  269,  271,
  272,  273,   45,  125,   44,  256,  268,   45,  256,  125,
   40,  259,   44,  264,  256,   41,   64,   65,   45,   59,
  123,   40,  123,   41,   41,   43,  268,   45,   41,   45,
   44,  256,   41,   41,   41,  256,   42,   43,   44,   45,
   45,   47,   44,  268,   45,   40,   44,  268,   45,   40,
   44,  268,   44,  256,  257,   45,   44,  260,   41,   45,
  263,  256,   44,  258,  259,  268,   45,  125,   43,   41,
   45,   40,   45,  256,  257,   45,   41,  260,   41,   58,
  263,  143,   41,   45,   41,  268,  256,   40,  270,  271,
   41,   45,   60,   61,   62,   41,   45,  258,  259,   60,
   61,   62,   41,   45,  104,  105,  256,  257,  170,   44,
  260,  123,   45,  263,   58,   45,  109,  110,  268,   44,
   44,   44,   44,  185,  111,  112,  123,   44,   44,  191,
  192,  193,  194,   44,  256,  257,  256,   41,  260,  261,
  262,  263,  123,  265,  266,  256,  268,  256,  256,  256,
  256,  256,  256,  256,  265,  266,  256,  268,  265,  266,
  256,  268,  256,  125,  275,  269,  275,  275,  275,  275,
  256,  125,  272,  273,  274,  256,  125,   41,  272,  273,
  274,  256,  256,  125,  256,  123,  272,  273,  274,  270,
  271,  256,  125,  268,  268,  125,  270,  271,  256,  123,
  272,  273,  274,  123,  256,  256,  256,  272,  273,  274,
  256,  257,  270,  271,  260,  261,  262,  263,  256,  265,
  266,  267,  268,  261,  262,  268,  256,  270,  271,  256,
  268,  269,  270,  271,  261,  262,   11,  256,  256,  256,
  256,  268,  256,  270,  271,  261,  262,  256,  256,  256,
  256,  256,  268,   41,  270,  271,  261,  262,  256,  256,
  261,  262,  256,  268,  256,  270,  271,  268,  256,  270,
  271,  261,  262,  270,  271,  261,  262,  256,  268,  123,
  270,  271,  268,  256,  270,  271,  256,  123,  256,   33,
  123,  270,  271,  138,  256,   40,   44,  270,  271,    0,
  270,  271,  256,  125,  272,  273,  274,  256,  270,  271,
   11,  272,  273,  274,  256,   44,  270,  271,   26,  259,
   44,  270,  271,  256,   41,  259,  256,  264,  270,  271,
   74,   44,  256,  257,   42,   43,  260,  270,  271,  263,
  270,  271,  117,  118,  268,  264,   44,   44,   41,   22,
  195,  125,   44,   26,   27,   42,   43,   44,   45,   32,
   47,   44,   44,   64,   65,   44,  211,   44,   44,   42,
   43,   22,  217,   44,   44,   26,   27,   44,  223,  224,
  225,   32,  227,  127,   44,  129,  130,   44,    0,   41,
    8,   42,   43,  101,    7,   24,  207,  256,  257,  107,
  108,  260,  261,  262,  263,    2,  265,  266,   41,  268,
   43,   17,   45,   22,  256,  257,  117,  118,  260,  261,
  262,  263,   57,  265,  266,  126,  268,   -1,  101,   -1,
   -1,  104,  105,  208,  107,  108,  109,  110,  111,  112,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  101,   -1,   -1,   -1,   -1,   -1,  107,  108,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  168,   -1,   -1,
   -1,   -1,   -1,   -1,  218,   -1,  220,  221,  222,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  207,  208,
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
"funcion : error funcion_fun",
"funcion : error funcion_void",
"funcion_fun : FUN comienzo_funcion bloque_sentencias RETURN '(' retorno ')' ',' fin_funcion",
"funcion_fun : FUN comienzo_funcion bloque_sentencias '(' retorno ')' ',' fin_funcion",
"funcion_fun : FUN comienzo_funcion bloque_sentencias RETURN '(' error ')' ',' fin_funcion",
"funcion_fun : FUN comienzo_funcion bloque_sentencias RETURN '(' retorno ')' error fin_funcion",
"funcion_void : VOID comienzo_funcion bloque_sentencias fin_funcion",
"comienzo_funcion : ID '(' ')' '{'",
"comienzo_funcion : error '(' ')' '{'",
"comienzo_funcion : ID error ')' '{'",
"comienzo_funcion : ID '(' error '{'",
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
"condicion_IF : error condicion ')'",
"condicion_IF : '(' condicion error",
"sentencia_control : CASE '(' ID ')' '{' lista_opciones '}' ','",
"sentencia_control : error '(' ID ')' '{' lista_opciones '}' ','",
"sentencia_control : CASE error ID ')' '{' lista_opciones '}' ','",
"sentencia_control : CASE '(' error ')' '{' lista_opciones '}' ','",
"sentencia_control : CASE '(' ID error '{' lista_opciones '}' ','",
"sentencia_control : CASE '(' ID ')' error lista_opciones '}' ','",
"sentencia_control : CASE '(' ID ')' '{' error '}' ','",
"sentencia_control : CASE '(' ID ')' '{' lista_opciones error ','",
"sentencia_control : CASE '(' ID ')' '{' lista_opciones '}' error",
"sentencia_control : CASE ID '{' lista_opciones '}' ','",
"sentencia_control : CASE '(' ID ')' lista_opciones ','",
"lista_opciones : lista_opciones opcion",
"lista_opciones : opcion",
"opcion : cte ':' DO bloque_control ','",
"opcion : error ':' DO bloque_control ','",
"opcion : cte error DO bloque_control ','",
"opcion : cte ':' error bloque_control ','",
"opcion : cte ':' DO error ','",
"opcion : cte ':' DO bloque_control error",
"sentencia_impresion : PRINT '(' CADENA ')' ','",
"sentencia_impresion : error '(' CADENA ')' ','",
"sentencia_impresion : PRINT error CADENA ')' ','",
"sentencia_impresion : PRINT '(' error ')' ','",
"sentencia_impresion : PRINT '(' CADENA error ','",
"sentencia_impresion : PRINT '(' CADENA ')' error",
"sentencia_impresion : PRINT CADENA ','",
"sentencia_asignacion : ID ASIGN expresiones ','",
"sentencia_asignacion : ID ASIGN ID '(' ')' ','",
"sentencia_asignacion : error ASIGN expresiones ','",
"sentencia_asignacion : error ASIGN ID '(' ')' ','",
"sentencia_asignacion : ID error expresiones ','",
"sentencia_asignacion : ID error ID '(' ')' ','",
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
"conversion_explicita : tipo error expresion ')'",
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
//#line 532 "Parser.java"
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
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una funcion.");}
break;
case 11:
//#line 52 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una funcion.");}
break;
case 12:
//#line 53 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el tipo de la funcion.");}
break;
case 13:
//#line 54 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el tipo de la funcion.");}
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
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la funcion.");}
break;
case 20:
//#line 67 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el identificador de la funcion.");}
break;
case 21:
//#line 68 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' luego del identificador de la funcion.");}
break;
case 22:
//#line 69 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' luego del identificador de la funcion.");}
break;
case 23:
//#line 70 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '{' al comienzo de la funcion.");}
break;
case 24:
//#line 71 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '()' luego del identificador de la funcion.");}
break;
case 28:
//#line 83 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una declaracion de variables.");}
break;
case 29:
//#line 84 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el tipo en la declaracion de variables.");}
break;
case 30:
//#line 85 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el identificador o lista de identificadores en la declaracion de variables.");}
break;
case 31:
//#line 86 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la declaracion de variables.");}
break;
case 41:
//#line 105 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una sentencia IF.");}
break;
case 43:
//#line 107 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'if'.");}
break;
case 44:
//#line 108 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'end_if'.");}
break;
case 45:
//#line 109 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la sentencia IF.");}
break;
case 46:
//#line 110 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'if'.");}
break;
case 47:
//#line 111 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'else'.");}
break;
case 48:
//#line 112 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'end_if'.");}
break;
case 49:
//#line 113 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la sentencia IF.");}
break;
case 51:
//#line 117 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' en la condicion del IF.");}
break;
case 52:
//#line 118 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' en la condicion del IF.");}
break;
case 53:
//#line 121 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una sentencia CASE.");}
break;
case 54:
//#line 122 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'case'.");}
break;
case 55:
//#line 123 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' en la sentencia CASE.");}
break;
case 56:
//#line 124 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba un identificador en la sentencia CASE.");}
break;
case 57:
//#line 125 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' en la sentencia CASE.");}
break;
case 58:
//#line 126 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '{' en la sentencia CASE.");}
break;
case 59:
//#line 127 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la lista de opciones en la sentencia CASE.");}
break;
case 60:
//#line 128 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '}' en la sentencia CASE.");}
break;
case 61:
//#line 129 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la sentencia CASE.");}
break;
case 62:
//#line 130 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '()' en la sentencia CASE.");}
break;
case 63:
//#line 131 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '{}' en la sentencia CASE.");}
break;
case 67:
//#line 139 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una constante en una opcion.");}
break;
case 68:
//#line 140 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ':' en una opcion.");}
break;
case 69:
//#line 141 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra resrevada 'DO'.");}
break;
case 70:
//#line 142 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba un bloque o sentencia.");}
break;
case 71:
//#line 143 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba ',' luego de una opcion.");}
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
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '('.");}
break;
case 75:
//#line 149 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una cadena en una salidad por pantalla.");}
break;
case 76:
//#line 150 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')'.");}
break;
case 77:
//#line 151 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de una salida por pantalla.");}
break;
case 78:
//#line 152 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba '()'.");}
break;
case 79:
//#line 155 "gramatica.y"
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
//#line 949 "Parser.java"
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
{
  this.al = al;
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
