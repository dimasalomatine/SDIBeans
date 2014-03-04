package Utils;

//~--- JDK imports ------------------------------------------------------------

import Utils.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

//~--- classes ----------------------------------------------------------------

public class PrintSupport implements Printable {
    public static void main(String[] args) {
        PrinterJob pj = PrinterJob.getPrinterJob();

        pj.setPrintable(new PrintSupport());

        if (pj.printDialog()) {
            try {
                pj.print();
            } catch (PrinterException e) {
                if(LoggerNB.debuging){System.out.println(e);}
            }
        }
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) {
        if (pageIndex != 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2 = (Graphics2D) g;

        g2.setFont(new Font("Serif", Font.PLAIN, 10));
        g2.setPaint(Color.black);
        g2.drawString("HellCreator Projects", 0, 0);

        return PAGE_EXISTS;
    }
}



/*
 import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.CubicCurve2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PrintingOneTwoFour {

  static class MyComponent extends JPanel implements Printable {

    public void paint(Graphics g) {
      Graphics2D g2d = (Graphics2D) g;
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
          RenderingHints.VALUE_ANTIALIAS_ON);
      g2d.setPaint(Color.BLUE);
      g2d.setStroke(new BasicStroke(3));
      CubicCurve2D cubic = new CubicCurve2D.Float(10, 80, 60, 30, 110, 130, 160, 80);
      g2d.draw(cubic);
    }

    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
      if (pageIndex == 0) {
        paint(g);
        return Printable.PAGE_EXISTS;
      } else {
        return Printable.NO_SUCH_PAGE;
      }
    }
  }

  public static void main(String args[]) throws Exception {

    final JFrame frame = new JFrame();

    Container contentPane = frame.getContentPane();

    final Component printIt = new MyComponent();
    contentPane.add(printIt, BorderLayout.CENTER);

    JButton button = new JButton("Print");
    contentPane.add(button, BorderLayout.SOUTH);

    ActionListener listener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
        PrintService printService = PrintServiceLookup
            .lookupDefaultPrintService();
        DocPrintJob job = printService.createPrintJob();
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        DocAttributeSet das = new HashDocAttributeSet();
        Doc doc = new SimpleDoc(printIt, flavor, das);
        try {
          job.print(doc, pras);
        } catch (PrintException pe) {
          pe.printStackTrace();
        }
      }
    };
    button.addActionListener(listener);

    frame.setSize(350, 350);
    frame.show();
  }
}
 **/


//~ Formatted by Jindent --- http://www.jindent.com
