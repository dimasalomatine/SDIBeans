package Utils;

//~--- non-JDK imports --------------------------------------------------------

//~--- classes ----------------------------------------------------------------

public class Utils {
    public static final int NOP  = 0;
    public static final int AND  = 1;
    public static final int OR   = 2;
    public static final int NOT  = 3;
    public static final int NOR  = 4;
    public static final int NAND = 5;
    public static final int XOR  = 6;

    //~--- methods ------------------------------------------------------------

    public static Object[][] copyDataInto(Object[][] in) {
        Object[][] data = new Object[in.length][];

        for (int i = 0; i < in.length; i++) {
            data[i] = new Object[in[i].length];

            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = in[i][j];
            }
        }

        return data;
    }

    public static float[][] copyFloat2DArray(float[][] in) {
        float[][] data = new float[in.length][];
        for (int i = 0; i < in.length; i++) 
        {
            data[i] = new float[in[i].length];
            for (int j = 0; j < data[i].length; j++) 
            {
                data[i][j] = in[i][j];
            }
        }
        return data;
    }

    public static float[] copyFloat1DArray(float[] in) {
        float[] data = new float[in.length];
        for (int i = 0; i < in.length; i++) 
        {
                data[i] = in[i];
        }
        return data;
    }
    public static String logicalOperationtoString(int logical) {
        if (logical == AND) {
            return "AND";
        } else if (logical == OR) {
            return "OR";
        } else if (logical == NAND) {
            return "NAND";
        } else if (logical == NOT) {
            return "NOT";
        } else if (logical == NOR) {
            return "NOR";
        } else if (logical == XOR) {
            return "XOR";
        } else {
            return "NOP";
        }
    }

    public static double max(double a, double b) {
        if (a >= b) {
            return a;
        }

        return b;
    }

    public static double min(double a, double b) {
        if (a <= b) {
            return a;
        }

        return b;
    }

    public static void reverselist(java.util.List somelist) {
        TDoc t;

        for (int i = 0; i < somelist.size() / 2; i++) {
            t = (TDoc) somelist.get(i);
            somelist.set(i, (TDoc) somelist.get(somelist.size() - 1 - i));
            somelist.set(somelist.size() - 1 - i, t);
        }
    }

    public static void reverselistO(java.util.List somelist) {
        Object t;

        for (int i = 0; i < somelist.size() / 2; i++) {
            t = somelist.get(i);
            somelist.set(i, somelist.get(somelist.size() - 1 - i));
            somelist.set(somelist.size() - 1 - i, t);
        }
    }

    public static int toLogicalOperation(String opstring) {
        if (opstring.equalsIgnoreCase("and")) {
            return AND;
        } else if (opstring.equalsIgnoreCase("or")) {
            return OR;
        } else if (opstring.equalsIgnoreCase("not")) {
            return NOT;
        } else if (opstring.equalsIgnoreCase("nand")) {
            return NAND;
        } else if (opstring.equalsIgnoreCase("nor")) {
            return NOR;
        } else if (opstring.equalsIgnoreCase("xor")) {
            return XOR;
        } else {
            return NOP;
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
