package Utils;
//~--- JDK imports ------------------------------------------------------------

import java.awt.*;

/*
 * @author Salomatine Dmitry
 * @version 1.00 04/04/20
 */
import java.io.*;

import javax.swing.*;

//~--- classes ----------------------------------------------------------------

public class MySimpleBrowser extends FileDialog {
    public String file      = "";
    public String directory = "";
    public String fp        = "";

    //~--- constructors -------------------------------------------------------

    public MySimpleBrowser(JFrame parent, String st, JTextArea ta,
                           boolean stype, boolean dir, String[] exts) {
        super(parent, st, (stype == true)
                          ? FileDialog.SAVE
                          : FileDialog.LOAD);
        setFile(ta.getText());

        // debug(exts);
        setVisible(true);

        String tt = getFile();

        if (tt != null) {
            String td = "";

            file = tt;

            if (dir) {
                directory = getDirectory();
                tt        = td + tt;
                fp        = directory + file;
            }

            ta.setText(tt);
        }
    }

    public MySimpleBrowser(JFrame parent, String st, JTextField ta,
                           boolean stype, boolean dir, String[] exts) {
        super(parent, st, (stype == true)
                          ? FileDialog.SAVE
                          : FileDialog.LOAD);
        setFile(ta.getText());

        // debug(exts);
        setVisible(true);

        String tt = getFile();

        if (tt != null) {
            String td = "";

            file = tt;

            if (dir) {
                td        = getDirectory();
                directory = td;
                tt        = td + tt;
                fp        = directory + file;
            }

            ta.setText(tt);
        }
    }

    public MySimpleBrowser(JFrame parent, String st, String ta, boolean stype,
                           boolean dir, String[] exts) {
        super(parent, st, (stype == true)
                          ? FileDialog.SAVE
                          : FileDialog.LOAD);
        setFile(ta);

        // debug(exts);
        setVisible(true);
        file = getFile();

        if (file != null) {
            if (dir) {
                directory = getDirectory();
                fp        = directory + file;
            }
        }
    }

    //~--- methods ------------------------------------------------------------

    void debug(String[] exts) {
        final String[] ext = exts;

        setFilenameFilter(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                for (int i = 0; i < ext.length; i++) {
                    if (name.endsWith(ext[i])) {
                        return true;
                    }
                }

                return (new File(dir, name)).isDirectory();
            }
        });
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
