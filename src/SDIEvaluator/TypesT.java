package SDIEvaluator;

public class TypesT {

    // type of token in proc
    final static int UNDEFTOK = -1;
    final static int OPERATOR = 1;
    final static int NUMBER   = 2;
    final static int VARIABLE = 3;

    // 1 level type of variable
    final static int std_SYSTEM = 1;
    final static int std_USER   = 2;
    final static int std_TAN    = 17;
    final static int std_SIN    = 13;
    final static int std_SET    = 7;
    final static int std_RB     = 9;
    final static int std_POW    = 5;

    // operation codes defined
    final static int std_PLUS    = 1;
    final static int std_MUL     = 3;
    final static int std_MOD     = 6;
    final static int std_MINUS   = 2;
    final static int std_LOG     = 12;
    final static int std_LN      = 10;
    final static int std_LG      = 11;
    final static int std_LB      = 8;
    final static int std_FLOOR   = 19;
    final static int std_DIV     = 4;
    final static int std_COS     = 15;
    final static int std_CEIL    = 20;
    final static int std_ATAN    = 18;
    final static int std_ASIN    = 14;
    final static int std_ACOS    = 16;
    final static int std_ABS     = 21;
    final static int SYSVARERROR = 4;
    final static int SERROR      = 0;
    final static int PARENS      = 1;

    // error codes and messages
    final static int NOEXP   = 2;
    final static int DIVZERO = 3;

    //~--- methods ------------------------------------------------------------

    void serror(int error) {
        String[] e = { "Syntax Error", "Unbalanced Parentheses",
                       "No expression Present", "Division by zero",
                       "System variable protected" };

        if ((error >= 0) && (error < e.length)) {
            System.err.println(e[error]);
        } else {
            System.err.println("UNKNOWN ERROR - CODE PROBABLY BROKEN");
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
