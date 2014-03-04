package Utils;

//~--- JDK imports ------------------------------------------------------------

/*
 * @author Salomatine Dmitry
 * @version 1.00 04/04/20
 */
import java.awt.*;

import javax.swing.*;
import javax.swing.text.*;

//~--- classes ----------------------------------------------------------------

public class ValidatedJTextField extends JTextField {
    public static final int STRING = 0;
    public static final int INT    = 1;
    public static final int FLOAT  = 2;

    //~--- fields -------------------------------------------------------------

    private int               curenttype         = INT;
    private boolean           have_limits        = false;
    private validatedDocument validatedDocument1 = new validatedDocument();
    private float             fl0, fl1;
    private int               il0, il1;

    //~--- constructors -------------------------------------------------------

    /**
     * Method ValidatedJTextField
     */
    public ValidatedJTextField(String val, String actioncommand, int type) {
        super(val, 3);
        curenttype = type;
        setDocument(validatedDocument1);
        setActionCommand(actioncommand);
    }

    public ValidatedJTextField(String val, String actioncommand, int type,
                                  float lims, float lime) {
        super(val, 3);
        curenttype  = type;
        have_limits = true;
        fl0         = lims;
        fl1         = lime;
        setDocument(validatedDocument1);
        setActionCommand(actioncommand);
        setText(val);
    }

    public ValidatedJTextField(String val, String actioncommand, int type,
                                  int lims, int lime) {
        super(val, 3);
        curenttype  = type;
        have_limits = true;
        il0         = lims;
        il1         = lime;
        setDocument(validatedDocument1);
        setActionCommand(actioncommand);
    }

    //~--- inner classes ------------------------------------------------------

    class validatedDocument extends PlainDocument {
        public void insertString(int offset, String string,
                                 AttributeSet attributes)
                throws BadLocationException {
            if (string == null) {
                return;
            } else {
                String newValue;
                int    ival;
                float  fval;
                int    length = getLength();

                if (length == 0) {
                    newValue = string;
                } else {
                    String       currentContent = getText(0, length);
                    StringBuffer currentBuffer  =
                        new StringBuffer(currentContent);

                    currentBuffer.insert(offset, string);
                    newValue = currentBuffer.toString();
                }

                try {
                    switch (curenttype) {
                    case INT :
                        ival = Integer.parseInt(newValue);

                        if ((have_limits == true)
                                && ((ival < il0) || (ival > il1))) {
                            return;
                        }

                        break;

                    case FLOAT :
                        fval = Float.parseFloat(newValue);

                        if ((have_limits == true)
                                && ((fval < fl0) || (fval > fl1))) {
                            return;
                        }

                        break;

                    default :
                        break;
                    }
                    ;

                    super.insertString(offset, string, attributes);
                } catch (NumberFormatException exception) {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
