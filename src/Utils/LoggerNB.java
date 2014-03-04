/*
 * LoggerNB.java
 *
 * Created on October 27, 2006, 11:16 AM
 */

package Utils;

import java.io.*;

/**
 *
 * @author  Dmitry
 */
public class LoggerNB extends javax.swing.JPanel {
    public static final int  DEBUG_NONE =0; //release version
    public static final int  DEBUG_YES =1; //debug version only
    public static final int  DEBUG_DEVELOPER =2; //debug version+developer side options in gui and tool triks
    public static final int  DEBUG      = DEBUG_NONE;    // aplies in jit and skips all my debug prints
    public static final boolean  debuging      = false;    // aplies in jit and skips all my debug prints
    static private LoggerNB inst           = null;
    public static final int  SENSITIVE      = 2;    // must corect in near catch
    static int               MAX_ROWS       = 100;
    public static final int  INFORMATIVE_NC = 100;    // can pass not to runlog console
    public static final int INFORMATIVE = 0;    // can pass
    public static final int FATAL       = 1;    // cant procceed must save datas and exit
    public static final int CRITICAL    = 4;    // must redo processes in more upper level

    //~--- fields -------------------------------------------------------------

    boolean     quaiet      = false;
    boolean     logtofile   = false;
    String      fileerr     = "";
    boolean     exitonfatal = true;
    /**
     * Creates new form LoggerNB
     */
    public LoggerNB(boolean eof, boolean quaietly, String logfile) {
        inst        = this;
        exitonfatal = eof;
        quaiet      = quaietly;
        initComponents();
        if (logfile == "") {
            logtofile = false;
        } else {
            fileerr   = logfile;
            logtofile = true;
            logtofile("<B>Begin log</B>", false);
            logtofile("<B>Begin log</B>", true);
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        jScrollPane1 = new javax.swing.JScrollPane();
        runlog = new javax.swing.JTextArea();
        caprg = new javax.swing.JProgressBar();
        actiontxtlbl = new javax.swing.JLabel();
        ADpanel = new javax.swing.JPanel();

        runlog.setColumns(20);
        runlog.setRows(5);
        jScrollPane1.setViewportView(runlog);

        actiontxtlbl.setText("Action:...");

        org.jdesktop.layout.GroupLayout ADpanelLayout = new org.jdesktop.layout.GroupLayout(ADpanel);
        ADpanel.setLayout(ADpanelLayout);
        ADpanelLayout.setHorizontalGroup(
            ADpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 220, Short.MAX_VALUE)
        );
        ADpanelLayout.setVerticalGroup(
            ADpanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 60, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 325, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(actiontxtlbl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .add(caprg, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(15, 15, 15)
                .add(ADpanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(actiontxtlbl)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 20, Short.MAX_VALUE)
                        .add(caprg, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(21, 21, 21))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, ADpanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                        .addContainerGap())))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ADpanel;
    public javax.swing.JLabel actiontxtlbl;
    public javax.swing.JProgressBar caprg;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea runlog;
    // End of variables declaration//GEN-END:variables
    //~--- methods ------------------------------------------------------------

    /**
     * Method log
     */
    public synchronized void log(String log, int importance) {
        if ((log != "") && quaiet) {
            System.out.println(log);
        }

        if (importance == INFORMATIVE) {
            if (runlog.getLineCount() >= MAX_ROWS) {
                runlog.setText("");
            } else {
                runlog.append(log + "\n");
            }

            runlog.repaint();
        }

        switch (importance) {
        case FATAL :
            logtofile("<B>Fatal<br>" + log + "</B>", true);

            if (exitonfatal) {
                System.out.println("Now will exit");
                System.exit (0);
            }

            break;

        case INFORMATIVE :
            logtofile("<B>Informative<br>" + log + "</B>", true);

            break;

        case INFORMATIVE_NC :
            logtofile("<B>Informative quiet<br>" + log + "</B>", true);

            break;

        case CRITICAL :
            logtofile("<B>Critical<br>" + log + "</B>", true);

            break;

        case SENSITIVE :
            logtofile("<B>Sensitive<br>" + log + "</B>", true);

            break;

        default :
            logtofile("<B>Undefined ERR Level<br>" + log + "</B>", true);

            break;
        }
        ;
    }

    private void logtofile(String msg, boolean append) {
        if (!logtofile) {
            return;
        }

        DataOutputStream dos = null;

        try {
            dos = new DataOutputStream(new FileOutputStream(fileerr, append));
            dos.writeBytes(msg);
            dos.close();
        } catch (FileNotFoundException ex) {}
        catch (IOException ex) {}
    }

    //~--- get methods --------------------------------------------------------

    static public LoggerNB getLogger() {
        return inst;
    }
    
    public void AddADPanel(javax.swing.JPanel p)
    {
     ADpanel.add (p);
    }
}
