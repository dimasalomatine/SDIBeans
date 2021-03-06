package Utils;


import Utils.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;

//~--- classes ----------------------------------------------------------------

/**
 * Simple Help Frame based on JFC JEditorPane.
  */
public class SimpleHelp extends JFrame implements HyperlinkListener {

    /** The contentpane */
    protected Container cp;

    /** The editorpane */
    public JEditorPane help;

    //~--- constructors -------------------------------------------------------

    /*
     * Construct a Help object. Just construct a JEditorPane with a URL, and it
     * does all the help from there.
     */
    public SimpleHelp(String windowName, String helpIndexFileName) {
        super(windowName + " Help Window");
        cp = getContentPane();
        getAccessibleContext().setAccessibleName(windowName + " Help Window");
        getAccessibleContext().setAccessibleDescription(
            "A window for viewing the help for " + windowName
            + ", which is somewhat hyperlinked.");

        try {
            URL url = new File(helpIndexFileName).toURL();

            // Only create the window once.
            if (help == null) {

                // System.out.println("Creat-ing help window for " + url);
                help = new JEditorPane(url);

                // System.out.println("Done!");
                help.setEditable(false);
                help.addHyperlinkListener(this);

                JScrollPane scroller = new JScrollPane();

                scroller.setBorder(BorderFactory.createTitledBorder(windowName
                        + " Help"));
                scroller.getViewport().add(help);
                cp.add(BorderLayout.CENTER, scroller);
                addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        SimpleHelp.this.setVisible(false);
                        SimpleHelp.this.dispose();
                    }
                });
                setSize(500, 400);
            } else {
                System.out.println("Re-using help window!");
            }
        } catch (MalformedURLException e) {
            if(LoggerNB.debuging){System.out.println("Malformed URL: " + e);}
        } catch (IOException e) {
            if(LoggerNB.debuging){System.out.println("IOException: " + e);}
        }
    }

    //~--- methods ------------------------------------------------------------

    /**
     * Notification of a change relative to a hyperlink. From:
     * java.swing.event.HyperlinkListener
     */
    public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            URL target = e.getURL();

            // System.out.println("linkto: " + target);
            // Get the help panel's cursor and the wait cursor
            Cursor oldCursor  = help.getCursor();
            Cursor waitCursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);

            help.setCursor(waitCursor);

            // Now arrange for the page to get loaded asynchronously,
            // and the cursor to be set back to what it was.
            SwingUtilities.invokeLater(new PageLoader(target, oldCursor));
        }
    }

    //~--- inner classes ------------------------------------------------------

    /**
     * Inner class that loads a URL synchronously into the help panel. Loads it
     * later than the request so that a cursor change can be done at the very
     * end.
     *
     * @author BORROWED FROM SUN'S SWING DEMO, UNTIL JAVAHELP AVAILABLE
     */
    class PageLoader implements Runnable {
        Cursor cursor;
        URL    url;

        //~--- constructors ---------------------------------------------------

        PageLoader(URL u, Cursor c) {
            url    = u;
            cursor = c;
        }

        //~--- methods --------------------------------------------------------

        public void run() {

            // System.out.println("PageLoader: u=" + url);
            if (url == null) {

                // restore the original cursor
                help.setCursor(cursor);

                // PENDING(prinz) remove this hack when
                // automatic validation is activated.
                Container parent = help.getParent();

                parent.repaint();
            } else {
                Document doc = help.getDocument();

                try {
                    help.setPage(url);
                } catch (Exception ioe) {
                    help.setDocument(doc);
                    getToolkit().beep();
                } finally {

                    // schedule the cursor to revert after
                    // the paint has happended.
                    url = null;
                    SwingUtilities.invokeLater(this);
                }
            }
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
