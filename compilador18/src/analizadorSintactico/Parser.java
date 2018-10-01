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
    0,    1,    1,    1,    1,    4,    4,    4,    3,    3,
    6,    6,    6,    6,    7,    7,    7,    7,    7,    8,
    8,    9,    9,    9,    9,    9,    9,   10,   11,   12,
    5,    5,    5,    5,   13,   13,   14,   14,    2,    2,
    2,    2,    2,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   19,   19,   19,   16,   16,   16,   16,   16,
   16,   16,   16,   16,   16,   16,   21,   21,   22,   22,
   22,   22,   17,   17,   17,   17,   17,   17,   17,   18,
   18,   18,   18,   18,   18,   18,   18,   18,   20,   20,
   20,   20,   20,   25,   25,   25,   25,   25,   25,   24,
   24,   27,   27,   27,   27,   26,   26,   26,   28,   28,
   28,   29,   29,   23,   23,   23,   23,
};
final static short yylen[] = {                            2,
    1,    2,    2,    1,    1,    4,    3,    1,    1,    1,
    2,    2,    2,    2,    8,    7,    8,    7,    7,    3,
    2,    4,    4,    3,    3,    4,    2,    1,    1,    4,
    3,    3,    3,    3,    1,    1,    3,    1,    1,    1,
    1,    1,    1,    5,    7,    5,    5,    5,    7,    7,
    7,    7,    3,    2,    2,    8,    8,    7,    8,    7,
    7,    8,    7,    8,    6,    6,    2,    1,    4,    4,
    3,    4,    5,    5,    4,    5,    4,    5,    3,    4,
    6,    4,    6,    3,    5,    4,    6,    4,    3,    3,
    3,    3,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    4,    4,    3,    4,    3,    3,    1,    3,    3,
    1,    1,    1,    1,    1,    2,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   35,   36,    0,    0,    0,    0,    0,
    0,    4,    5,    9,   10,   43,    0,   39,   40,   41,
   42,    0,    0,  114,  115,    0,    0,    0,    0,    0,
    0,    0,  113,    0,    0,  101,    0,  111,  112,    0,
    0,    0,    0,    0,    0,    0,    0,   13,   12,    0,
   11,   14,    0,    0,    0,    0,    2,    3,    0,   38,
    0,   98,   97,   99,   94,   95,   96,    0,    0,    0,
  116,  117,    0,    0,    0,    0,    0,   32,    0,    0,
    0,    8,    0,   54,    0,    0,    0,    0,    0,    0,
    0,    0,   79,    0,    0,    0,    0,    0,    0,    0,
   27,    0,    0,    0,   29,    0,   21,    0,    0,    0,
    0,    0,   84,   33,   34,   31,   91,    0,   82,    0,
    0,   53,    0,    0,  104,   37,    0,    0,    0,    0,
   92,   90,   89,    0,    0,  109,  110,    0,    0,    0,
   75,    0,    0,   77,    0,    0,   68,    0,    0,    0,
    0,    0,    0,   25,    0,   24,    0,    0,    0,   20,
    0,   88,    0,   86,   80,   30,    0,    0,   74,  103,
  105,  102,    7,    0,    0,   46,   47,    0,    0,   48,
   44,   76,   78,   73,    0,    0,   67,    0,    0,    0,
    0,    0,    0,    0,   23,   26,   22,    0,    0,    0,
    0,   85,    0,   83,    0,    6,    0,    0,    0,    0,
    0,   65,   71,    0,    0,    0,    0,    0,    0,    0,
    0,   66,    0,    0,    0,    0,   87,   81,    0,   49,
   50,   51,   52,   45,   70,   72,   69,   58,    0,   60,
    0,    0,   63,   61,    0,    0,    0,    0,   57,   59,
   62,   64,   56,   19,    0,    0,   18,   16,   17,   15,
};
final static short yydgoto[] = {                         10,
  198,   82,   13,   83,   14,   15,   48,   49,   50,  199,
  107,   16,   29,   30,   18,   19,   20,   21,   31,   32,
  146,  147,   33,   34,   68,   35,   36,   37,   38,
};
final static short yysindex[] = {                       323,
  -29,   98,  -33,    0,    0,  -34, -198, -198,   -7,    0,
  323,    0,    0,    0,    0,    0, -137,    0,    0,    0,
    0,  -25,    0,    0,    0,  160, -229,  123,   15,  -10,
  -79,   18,    0,   38,   29,    0,    9,    0,    0,  134,
  -79,  101, -216,  -26, -120,   -9,  -22,    0,    0,  294,
    0,    0,   26,  145,   55,   61,    0,    0,  109,    0,
  -42,    0,    0,    0,    0,    0,    0,  172,   87,  121,
    0,    0,   96,  131,  132,  118,    7,    0,  -92,  -18,
  -79,    0, -145,    0,  172,  156,  140,  140,  140,  140,
 -164,  136,    0,  146,  117,  167,   63,  147,  -21,  151,
    0,  -20,   71,  164,    0,  115,    0,  154,  162,  168,
  -41,  163,    0,    0,    0,    0,    0,  170,    0,   86,
  169,    0,  177,  -31,    0,    0,   94, -180,  -79,  182,
    0,    0,    0,    9,    9,    0,    0,  216,  -79,  -36,
    0,  185,   -5,    0,  165,   78,    0,  -54,  167,  122,
  167,  192,  133,    0, -110,    0,  323,  227,  323,    0,
  228,    0,  230,    0,    0,    0,  234,  167,    0,    0,
    0,    0,    0,  166,   20,    0,    0,   30,  -99,    0,
    0,    0,    0,    0,   31,  244,    0,  -79, -232,  232,
  167,  237,  179,   34,    0,    0,    0,  323,  258,  367,
  259,    0,   10,    0,  272,    0,  257,  269,  288,   13,
  -79,    0,    0,  -79,  -79,  293,  278,  299,  -32,   37,
  300,    0,  306,  -40,  275,  311,    0,    0,  312,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  314,    0,
  317,   17,    0,    0,  197,  320,  -15,  197,    0,    0,
    0,    0,    0,    0,  197,  197,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  365,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   70,   73,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   68,    0,    2,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  155,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  155,    0,
    0,    0,   79,    0,  198,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  107,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  226,    0,    0,    0,
    0,    0,    0,   24,   46,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  326,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   23,  409,   -2,  388,    0,    0,  362,  379,    0, -129,
  -81,    0,  414,  356,    0,    0,    0,    0,  396,  116,
  372,  324,  426,  427,  366,   -1,    0,   83,   93,
};
final static int YYTABLESIZE=646;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         28,
  246,  116,  165,  189,   27,   45,   43,  181,   58,  172,
   28,   87,  197,   88,   97,   27,   79,  102,  103,  152,
  155,   28,   11,  214,  160,  185,   27,   77,  256,  201,
  100,  215,   55,   78,   65,   67,   66,   27,  184,   94,
   71,   72,  108,   81,  108,  108,  108,  125,   79,   87,
   89,   88,   95,  228,   76,   90,  234,   46,   84,   27,
  253,  108,  108,  108,  106,  108,  106,  106,  106,   47,
  225,   87,  106,   88,  124,   80,    2,  222,   27,    3,
  243,   27,    6,  106,  106,  106,  107,    9,  107,  107,
  107,  138,  241,  139,  140,  112,   96,   65,   67,   66,
  101,  151,  154,   58,  113,  107,  107,  107,  100,  105,
   93,  100,  129,  130,  112,  112,   38,  112,   59,  112,
  112,  112,   27,  112,  108,  112,  118,  100,  100,  100,
   60,   38,  112,  112,  112,   98,  120,   40,  112,  112,
  112,   92,   27,   75,   93,  196,  106,   99,  112,  112,
  112,  112,  114,  112,  159,   75,  209,  143,  221,  210,
  144,  242,   27,  254,  119,  257,  258,   27,  107,  134,
  135,  121,  122,  259,  260,  126,   80,    2,   27,  141,
    3,  136,  137,    6,   27,  149,  142,  150,    9,   27,
  100,  153,   93,  156,  161,   58,  112,  112,  112,  112,
   27,  112,  186,  157,   27,  162,  166,  163,  168,  188,
  167,   27,  169,  115,  164,   22,   27,  170,  173,  180,
    4,    5,  185,   27,  171,  176,   22,   23,  182,   24,
   25,    4,    5,   44,   26,   42,   27,   22,   23,  105,
   24,   25,    4,    5,  191,   26,   62,   63,   64,   39,
  183,   24,   25,    4,    5,  195,   26,  108,  108,  177,
   53,  108,   24,   25,  108,  227,  200,   54,  233,  108,
  203,  202,  252,  108,  108,  108,   27,  204,  207,  106,
  106,   27,   39,  106,   24,   25,  106,  212,  208,  145,
  206,  106,  145,   85,  211,  106,  106,  106,  223,  226,
  230,  107,  107,   24,   25,  107,   24,   25,  107,   62,
   63,   64,  231,  107,  193,  247,   27,  107,  107,  107,
   55,  105,   27,  100,  100,   93,   93,  100,  112,   93,
  100,  232,   93,  145,  112,  100,  238,   93,   81,  100,
  100,  100,  240,  244,  112,  112,  112,   24,   25,  245,
  112,  112,  112,   22,  248,  249,  216,  250,    4,    5,
  251,  218,  112,  255,    1,   39,   28,   24,   25,   51,
    1,    2,   61,  123,    3,    4,    5,    6,   22,    7,
    8,  158,    9,    4,    5,   39,   52,   24,   25,   22,
   73,   74,   24,   25,    4,    5,  229,   41,    0,   86,
  109,   39,  239,   24,   25,    4,    5,   39,   12,   24,
   25,  132,  110,   17,   24,   25,    4,    5,  105,   57,
    4,    5,  145,   39,   17,   24,   25,   69,   91,   24,
   25,    0,    4,    5,  219,   56,   24,   25,    0,   39,
    0,   24,   25,    0,    0,    0,    0,  145,   24,   25,
    0,    0,   70,   55,   55,    0,    0,   55,   12,    0,
   55,   24,   25,   17,    0,   55,    0,    0,  128,  187,
    0,   80,    2,    0,    0,    3,    0,    0,    6,    0,
  111,    8,    8,    9,    0,    8,    0,  145,    8,  127,
    0,    0,  145,    8,  117,    0,    0,    0,    0,    0,
    0,   24,   25,    0,    0,    0,   24,   25,    0,    0,
    0,  131,  133,  187,   57,  187,  175,  187,    0,   17,
  190,  148,  192,  194,    0,  178,  179,  145,  187,    0,
    0,    0,    0,  145,    0,    0,  174,    0,    0,  205,
  187,   24,   25,  187,    0,    0,    0,   24,   25,    1,
    2,    0,    0,    3,    4,    5,    6,    0,    7,    8,
  104,    9,  217,    0,  220,   12,    0,   12,    0,    0,
   17,  148,   17,    0,  148,  213,  148,  148,    1,    2,
    0,    0,    3,    4,    5,    6,    0,    7,    8,    0,
    9,    0,    0,  148,    0,    0,    0,    0,  235,    0,
    0,  236,  237,    0,    0,    0,   57,    0,   12,    0,
    0,   17,    0,   17,    0,  148,  148,  148,  148,  148,
    0,    0,  224,    2,    0,    0,    3,    4,    5,    6,
  148,    7,    8,    0,    9,    0,    0,    0,    0,    0,
    0,    0,  148,    0,    0,  148,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   41,   44,   44,   58,   45,   40,   40,   44,   11,   41,
   40,   43,  123,   45,   41,   45,   59,   40,   41,   41,
   41,   40,    0,  256,  106,   58,   45,   29,   44,  159,
   40,  264,   40,   44,   60,   61,   62,   45,   44,  256,
  270,  271,   41,  123,   43,   44,   45,   41,   59,   43,
   42,   45,  269,   44,   40,   47,   44,  256,   41,   45,
   44,   60,   61,   62,   41,   40,   43,   44,   45,  268,
  200,   43,   50,   45,   76,  256,  257,   44,   45,  260,
   44,   45,  263,   60,   61,   62,   41,  268,   43,   44,
   45,  256,  125,  258,  259,   41,  123,   60,   61,   62,
  123,  123,  123,  106,   44,   60,   61,   62,   41,  125,
   41,   44,  258,  259,   42,   43,   44,   45,  256,   47,
   42,   43,   45,   45,  123,   47,   40,   60,   61,   62,
  268,   59,   60,   61,   62,  256,   41,   40,   60,   61,
   62,   41,   45,   28,   44,  256,  123,  268,   42,   43,
   44,   45,   44,   47,   40,   40,  256,   41,  125,  259,
   44,  125,   45,  245,   44,  247,  248,   45,  123,   87,
   88,   41,   41,  255,  256,  268,  256,  257,   45,   44,
  260,   89,   90,  263,   45,  123,   41,   41,  268,   45,
  123,   41,  123,  123,   41,  198,   42,   43,   44,   45,
   45,   47,  125,   40,   45,   44,   44,   40,  123,  264,
   41,   45,   44,  256,  256,  256,   45,   41,  125,  256,
  261,  262,   58,   45,  256,   44,  256,  268,   44,  270,
  271,  261,  262,  268,  275,  269,   45,  256,  268,  125,
  270,  271,  261,  262,  123,  275,  272,  273,  274,  268,
  256,  270,  271,  261,  262,  123,  275,  256,  257,   44,
  268,  260,  270,  271,  263,  256,   40,  275,  256,  268,
   41,   44,  256,  272,  273,  274,   45,   44,  259,  256,
  257,   45,  268,  260,  270,  271,  263,   44,  259,  256,
  125,  268,  256,  256,  264,  272,  273,  274,   41,   41,
   44,  256,  257,  270,  271,  260,  270,  271,  263,  272,
  273,  274,   44,  268,  123,   41,   45,  272,  273,  274,
  123,  125,   45,  256,  257,  256,  257,  260,  256,  260,
  263,   44,  263,  256,  256,  268,   44,  268,  123,  272,
  273,  274,   44,   44,  272,  273,  274,  270,  271,   44,
  272,  273,  274,  256,   44,   44,  125,   44,  261,  262,
   44,  125,  256,   44,    0,  268,   41,  270,  271,    8,
  256,  257,   17,  256,  260,  261,  262,  263,  256,  265,
  266,  267,  268,  261,  262,  268,    8,  270,  271,  256,
  268,  269,  270,  271,  261,  262,  125,    2,   -1,   34,
  256,  268,  125,  270,  271,  261,  262,  268,    0,  270,
  271,  256,  268,    0,  270,  271,  261,  262,  125,   11,
  261,  262,  256,  268,   11,  270,  271,  268,   41,  270,
  271,   -1,  261,  262,  256,    9,  270,  271,   -1,  268,
   -1,  270,  271,   -1,   -1,   -1,   -1,  256,  270,  271,
   -1,   -1,   26,  256,  257,   -1,   -1,  260,   50,   -1,
  263,  270,  271,   50,   -1,  268,   -1,   -1,   81,  146,
   -1,  256,  257,   -1,   -1,  260,   -1,   -1,  263,   -1,
   54,  256,  257,  268,   -1,  260,   -1,  256,  263,   81,
   -1,   -1,  256,  268,   68,   -1,   -1,   -1,   -1,   -1,
   -1,  270,  271,   -1,   -1,   -1,  270,  271,   -1,   -1,
   -1,   85,   86,  190,  106,  192,  129,  194,   -1,  106,
  149,   96,  151,  152,   -1,  138,  139,  256,  205,   -1,
   -1,   -1,   -1,  256,   -1,   -1,  128,   -1,   -1,  168,
  217,  270,  271,  220,   -1,   -1,   -1,  270,  271,  256,
  257,   -1,   -1,  260,  261,  262,  263,   -1,  265,  266,
  267,  268,  191,   -1,  193,  157,   -1,  159,   -1,   -1,
  157,  146,  159,   -1,  149,  188,  151,  152,  256,  257,
   -1,   -1,  260,  261,  262,  263,   -1,  265,  266,   -1,
  268,   -1,   -1,  168,   -1,   -1,   -1,   -1,  211,   -1,
   -1,  214,  215,   -1,   -1,   -1,  198,   -1,  200,   -1,
   -1,  198,   -1,  200,   -1,  190,  191,  192,  193,  194,
   -1,   -1,  256,  257,   -1,   -1,  260,  261,  262,  263,
  205,  265,  266,   -1,  268,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  217,   -1,   -1,  220,
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
"bloque_control : '{' sentencia_ejecutable '}'",
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
"llamado_funcion : ID '(' ')' ','",
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

//#line 225 "gramatica.y"

private AnalizadorLexico al;

public void verificar_rango(String cte, long rango) {
    if ( Long.parseLong(cte) == rango ) {
		System.out.println("Linea " + al.getNroLinea() + ": (AS) WARNING: Constante fuera del rango permitido. (integer = "+ cte +")");
		System.out.println("Linea " + al.getNroLinea() + ": (AS) WARNING: Se le asignara el mayor valor permitido. ("+ Long.toString(rango) +")");
		TablaSimbolos.modificarLexema(cte, Long.toString(rango-1));
	}
}

public void verificar_negativo(String cte)
{
	String nuevoLex = "-" + cte;
	Token viejo = TablaSimbolos.getSimbolo(cte);
	if( !TablaSimbolos.contiene(nuevoLex)){
		Token t = new Token(viejo.getIdentificador(), nuevoLex, viejo.getDescripcion() + " negativa");
		TablaSimbolos.addSimbolo(t);
	}
	int contador = Integer.parseInt(viejo.getAtributo("contador")) - 1 ;
	if( contador == 0) {
		TablaSimbolos.remove(viejo.getLexema());
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
//#line 559 "Parser.java"
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
case 11:
//#line 52 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una funcion FUN.");}
break;
case 12:
//#line 53 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una funcion VOID.");}
break;
case 13:
//#line 54 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Tipo de la funcion incorrecto.");}
break;
case 14:
//#line 55 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Tipo de la funcion incorrecto.");}
break;
case 16:
//#line 59 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'return' de la funcion.");}
break;
case 17:
//#line 60 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el retorno de la funcion.");}
break;
case 18:
//#line 61 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego del retorno de la funcion.");}
break;
case 19:
//#line 62 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el cuerpo de la funcion.");}
break;
case 21:
//#line 66 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el cuerpo de la funcion.");}
break;
case 23:
//#line 70 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el identificador de la funcion.");}
break;
case 24:
//#line 71 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' luego del identificador de la funcion.");}
break;
case 25:
//#line 72 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' luego del identificador de la funcion.");}
break;
case 26:
//#line 73 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '{' al comienzo de la funcion.");}
break;
case 27:
//#line 74 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '()' luego del identificador de la funcion.");}
break;
case 30:
//#line 83 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion el llamado de una funcion.");}
break;
case 31:
//#line 86 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una declaracion de variables.");}
break;
case 32:
//#line 87 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el tipo en la declaracion de variables.");}
break;
case 33:
//#line 88 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el identificador o lista de identificadores en la declaracion de variables.");}
break;
case 34:
//#line 89 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la declaracion de variables.");}
break;
case 44:
//#line 108 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una sentencia IF.");}
break;
case 45:
//#line 109 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una sentencia IF.");}
break;
case 46:
//#line 110 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'if'.");}
break;
case 47:
//#line 111 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'end_if'.");}
break;
case 48:
//#line 112 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la sentencia IF.");}
break;
case 49:
//#line 113 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'if'.");}
break;
case 50:
//#line 114 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'else'.");}
break;
case 51:
//#line 115 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'end_if'.");}
break;
case 52:
//#line 116 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la sentencia IF.");}
break;
case 54:
//#line 120 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' en la condicion del IF.");}
break;
case 55:
//#line 121 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' en la condicion del IF.");}
break;
case 56:
//#line 124 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una sentencia CASE.");}
break;
case 57:
//#line 125 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'case'.");}
break;
case 58:
//#line 126 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' en la sentencia CASE.");}
break;
case 59:
//#line 127 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba un identificador en la sentencia CASE.");}
break;
case 60:
//#line 128 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' en la sentencia CASE.");}
break;
case 61:
//#line 129 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '{' en la sentencia CASE.");}
break;
case 62:
//#line 130 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la lista de opciones en la sentencia CASE.");}
break;
case 63:
//#line 131 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '}' en la sentencia CASE.");}
break;
case 64:
//#line 132 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la sentencia CASE.");}
break;
case 65:
//#line 133 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '()' en la sentencia CASE.");}
break;
case 66:
//#line 134 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '{}' en la sentencia CASE.");}
break;
case 70:
//#line 142 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una constante en una opcion.");}
break;
case 71:
//#line 143 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ':' en una opcion.");}
break;
case 72:
//#line 144 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra resrevada 'DO'.");}
break;
case 73:
//#line 147 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una impresion por pantalla.");}
break;
case 74:
//#line 148 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta la palabra reservada 'print'.");}
break;
case 75:
//#line 149 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' en la salida por pantalla.");}
break;
case 76:
//#line 150 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una cadena en una salidad por pantalla.");}
break;
case 77:
//#line 151 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' en la salida por pantalla..");}
break;
case 78:
//#line 152 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de la salida por pantalla.");}
break;
case 79:
//#line 153 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba '()' en la salida por pantalla..");}
break;
case 80:
//#line 156 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una asignacion.");}
break;
case 81:
//#line 157 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una asignacion.");}
break;
case 82:
//#line 158 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el identificador en la asignacion.");}
break;
case 83:
//#line 159 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el identificador en la asignacion.");}
break;
case 84:
//#line 160 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el operador de asignacion.");}
break;
case 85:
//#line 161 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta el operador de asignacion.");}
break;
case 86:
//#line 162 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de una asignacion.");}
break;
case 87:
//#line 163 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ',' luego de una asignacion.");}
break;
case 88:
//#line 164 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una expresion o funcion.");}
break;
case 90:
//#line 168 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una expresion en el lado derecho de la condicion.");}
break;
case 91:
//#line 169 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una expresion en el lado izquierdo de la condicion.");}
break;
case 92:
//#line 170 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba un comparador entre las expresiones de la condicion.");}
break;
case 93:
//#line 171 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una condicion.");}
break;
case 102:
//#line 186 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se reconocion una conversion explicita.");}
break;
case 103:
//#line 187 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Se esperaba una expresion en la conversion.");}
break;
case 104:
//#line 188 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta '(' en la conversion.");}
break;
case 105:
//#line 189 "gramatica.y"
{System.out.println("Linea " + al.getNroLinea() + ": (AS) Falta ')' en la conversion.");}
break;
case 114:
//#line 206 "gramatica.y"
{
									String cte = val_peek(0).sval;
									verificar_rango(cte, (long) Math.pow(2, 15));
								}
break;
case 115:
//#line 210 "gramatica.y"
{
										String cte = val_peek(0).sval;
										verificar_rango(cte, (long) Math.pow(2, 31));
									}
break;
case 116:
//#line 214 "gramatica.y"
{
										String cte = val_peek(0).sval;
										verificar_negativo(cte);
									}
break;
case 117:
//#line 218 "gramatica.y"
{
											String cte = val_peek(0).sval;
											verificar_negativo(cte);
										}
break;
//#line 1008 "Parser.java"
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
