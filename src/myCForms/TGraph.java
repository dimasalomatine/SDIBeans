package myCForms;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
/*
 * TGraph.java
 *
 * Created on November 5, 2006, 11:30 AM
 */

/**
 *
 * @author  Dmitry
 */
public class TGraph extends javax.swing.JPanel {
    
    /** Creates new form TGraph */
    public TGraph() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        setMaximumSize(new java.awt.Dimension(1024, 1024));
        setMinimumSize(new java.awt.Dimension(100, 100));
        setPreferredSize(new java.awt.Dimension(100, 100));
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 396, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 296, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
/**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame tf=new JFrame();
                TGraph tr=new TGraph();
                tf.add(tr);
                tf.setBounds(tr.getBounds());
                tf.setVisible(true);
            }
        });
    }

   public void setDataSeries(String[] tstring, int[] tdata) {
        datalbls=tstring;
        data=tdata;
        repaint();
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public void setMaxValues(float tmaxX,float tmaxY)
    {
     realmaxX=tmaxX;
     realmaxY=tmaxY;
     calculateARates();
    }
    void calculateARates()
    {
     rateX=((float)(WIDTH-xOFSET-begingraffromx))/(float)realmaxX;
     rateY=((float)(HEIGHT-yOFSET-begingraffromy))/(float)realmaxY;
    }
    public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			drawMY(g);
		}
    private void drawMY(Graphics g)
    {
     calculateARates();
     int w=getWidth(),h=getHeight();
     g.setColor(Color.white);
     g.fillRect(0,0,w,h);
     g.setColor(Color.gray);
     g.drawLine(begingraffromx,yOFSET,begingraffromx,h-begingraffromy);
     g.drawLine(begingraffromx,h-begingraffromy,w-xOFSET,h-begingraffromy);
     g.setColor(Color.green);
     g.drawRect(10,10,10,50);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    private String[] datalbls;

    private int[] data;

    private float rateX;

    private float rateY;

    private int xOFSET=10;

    private int begingraffromx=20;

    private int yOFSET=10;

    private int begingraffromy=20;

    private float realmaxX=100.0f;

    private float realmaxY=100.0f;
    
}
