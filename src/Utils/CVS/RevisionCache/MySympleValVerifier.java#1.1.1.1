package Utils;
//~--- JDK imports ------------------------------------------------------------

import java.awt.Toolkit;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//~--- classes ----------------------------------------------------------------

public class MySympleValVerifier extends InputVerifier {
    public static final int STRING = 0;
    public static final int INT    = 1;
    public static final int FLOAT  = 2;
    public static final int DATE   = 3;

    //~--- fields -------------------------------------------------------------

    private int     curenttype  = INT;
    private boolean have_limits = false;
    private float   fl0, fl1;
    private int     il0, il1;

    //~--- constructors -------------------------------------------------------

    public MySympleValVerifier(int type) {
        curenttype = type;
    }

    public MySympleValVerifier(int type, float lims, float lime) {
        curenttype  = type;
        have_limits = true;
        fl0         = lims;
        fl1         = lime;
    }

    public MySympleValVerifier(int type, int lims, int lime) {
        curenttype  = type;
        have_limits = true;
        il0         = lims;
        il1         = lime;
    }

    //~--- methods ------------------------------------------------------------

    public boolean verify(JComponent input) {
        String text="";
        if( input instanceof JTextField) text=((JTextField)input).getText();
        else if( input instanceof JTextArea) text=((JTextArea)input).getText();
        try {
            int   ival;
            float fval;

            switch (curenttype) {
            case INT :
                ival = Integer.parseInt(text);

                if ((have_limits == true) && ((ival < il0) || (ival > il1))) {
                    return false;
                }

                break;

            case FLOAT :
                fval = Float.parseFloat(text);

                if ((have_limits == true) && ((fval < fl0) || (fval > fl1))) {
                    return false;
                }

                break;

            default :
                break;
            }
            ;

            return true;
        } catch (NumberFormatException exception) {
            Toolkit.getDefaultToolkit().beep();
        }

        return false;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
