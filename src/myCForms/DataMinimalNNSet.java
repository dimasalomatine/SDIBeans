package myCForms;
/*
 * DataMinimalNNSet.java
 *
 * Created on November 28, 2006, 10:17 PM
 */

import Utils.MySympleValVerifier;

/**
 *
 * @author  Dmitry
 */
public class DataMinimalNNSet extends javax.swing.JPanel {
    private double[] hidendata=new double[5];
    
    /** Creates new form DataMinimalNNSet */
    public DataMinimalNNSet() {
        initComponents();
    }
    
    //gets exact sequence data used in Test_2H_2.calculateInAndOutData
    public double[]getData()
    {
     double[]ret=new double[5];
     ret[0]=Double.parseDouble(c2c.getText());
     ret[1]=Double.parseDouble(a2a.getText());
     ret[2]=Double.parseDouble(c2o.getText());
     ret[3]=Double.parseDouble(c2h.getText());
     ret[4]=Double.parseDouble(c2l.getText());
     return ret;
    }
    public double[]getHidenData()
    {
     double[]ret=null;
     if(hidendata!=null)ret=hidendata.clone();
     return ret;
    }
    public void setDataFormat(String f)
    {
    fmt=f;
    }
    public void setData(double[]d)
    {
     c2c.setText(String.format(fmt,d[0]));
     c2l.setText(String.format(fmt,d[1]));
     c2o.setText(String.format(fmt,d[2]));
     c2h.setText(String.format(fmt,d[3]));
     a2a.setText(String.format(fmta,d[4]));
    }

    public void setHidenOnChangeData(double[] d) {
        hidendata=d.clone();
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        a2a = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        c2o = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        c2h = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        c2l = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        c2c = new javax.swing.JTextField();

        setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(51, 0, 204)));
        setMaximumSize(new java.awt.Dimension(200, 60));
        setMinimumSize(new java.awt.Dimension(280, 60));
        setPreferredSize(new java.awt.Dimension(300, 50));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Amount");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, -1));

        a2a.setText("0.001");
        a2a.setInputVerifier(new MySympleValVerifier(MySympleValVerifier.FLOAT,-100.0f,100.0f));
        add(a2a, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 60, -1));

        jLabel2.setText("Open");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        c2o.setText("0.001");
        c2o.setInputVerifier(new MySympleValVerifier(MySympleValVerifier.FLOAT,-100.0f,100.0f));
        add(c2o, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 50, -1));

        jLabel3.setText("High");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, -1, -1));

        c2h.setText("0.001");
        c2h.setInputVerifier(new MySympleValVerifier(MySympleValVerifier.FLOAT,-100.0f,100.0f));
        add(c2h, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 50, -1));

        jLabel4.setText("Low");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, -1, -1));

        c2l.setText("0.001");
        c2l.setInputVerifier(new MySympleValVerifier(MySympleValVerifier.FLOAT,-100.0f,100.0f));
        add(c2l, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 50, -1));

        jLabel5.setText("Close");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, -1, -1));

        c2c.setText("0.001");
        c2c.setInputVerifier(new MySympleValVerifier(MySympleValVerifier.FLOAT,-100.0f,100.0f));
        c2c.setMaximumSize(new java.awt.Dimension(20, 80));
        add(c2c, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 50, -1));
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField a2a;
    private javax.swing.JTextField c2c;
    private javax.swing.JTextField c2h;
    private javax.swing.JTextField c2l;
    private javax.swing.JTextField c2o;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables

    private String fmt="%10.4f";
    private String fmta="%12.0f";
    
}