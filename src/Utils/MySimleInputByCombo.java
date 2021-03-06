package Utils;
//~--- non-JDK imports --------------------------------------------------------

/*
 * @author Salomatine Dmitry
 * @version 1.00 04/04/20
 */
import DBCONNECT.TRS;
import Utils.*;

//~--- JDK imports ------------------------------------------------------------

import java.sql.*;

import javax.swing.*;

//~--- classes ----------------------------------------------------------------

public class MySimleInputByCombo extends JComboBox {
    Object[][] data = null;
    private String advclause="";
    

    //~--- constructors -------------------------------------------------------

    /**
     * Method mysimleinputbycombo
     *
     *
     */
    public MySimleInputByCombo()
    {
        }
    public MySimleInputByCombo(String ac, Object[][] in) {
        if (ac != null) {
            setActionCommand(ac);
        }

        // this.data=in;
        data = Utils.copyDataInto(in);
        initcombofromdata();
    }

    public MySimleInputByCombo(String ac, Connection con, String table,
                               String vc1, String vc2) {
        buildfromsource( ac,  con,  table,vc1,  vc2);
    }

    public MySimleInputByCombo(String ac, Connection con, String table,
                               String vc1, String vc2,String advclause) {
        this.advclause=advclause;
        buildfromsource( ac,  con,  table,vc1,  vc2,advclause);
    }
    
    public void  buildfromsource(String ac, Connection con, String table,
                               String vc1, String vc2) {
        this.removeAllItems();
        data=null;
        if (ac != null) {
            setActionCommand(ac);
        }

        TRS RS = null;
        int    count, i;
if(con!=null)
{
        try {
            RS = new TRS(con);
            RS.execSQL("SELECT Count(*) from " + table);

            if (RS.rs.next()) {
                i = count = Integer.parseInt(RS.rs.getString(1));
                RS.reuse();
                String cols=table+"."+vc1+","+table+"."+vc2+" ";
                RS.execSQL("SELECT "+cols+" from " + table);
                data = new Object[i][];

                while (RS.rs.next()) {
                    data[count - i] = new Object[2];

                    // data[count-i][0]=new String(RS.rs.getString(vc1));
                    data[count - i][0] = RS.rs.getString(vc1);

                    // data[count-i][1]=new String(RS.rs.getString(vc2));
                    data[count - i][1] = RS.rs.getString(vc2);
                    i--;
                }
            }
        } catch (SQLException e) {
            if(LoggerNB.debuging){System.out.println("Error 59: " + e);}
        }
   
        finally {
            if (RS != null) {
                RS.close();
            }
        }
 }
        initcombofromdata();
    }

    public void buildfromsource(String ac, Connection con, String table, String vc1, String vc2, String advclause) {
        this.advclause=advclause;
        this.removeAllItems();
        data=null;
        if (ac != null) {
            setActionCommand(ac);
        }

        TRS RS = null;
        int    count, i;

        try {
            RS = new TRS(con);
            RS.execSQL("SELECT Count(*) from " + table+advclause);

            if (RS.rs.next()) {
                i = count = Integer.parseInt(RS.rs.getString(1));
                RS.reuse();
                String cols=table+"."+vc1+","+table+"."+vc2+" ";
                //RS.execSQL("SELECT "+cols+" from " + table+advclause);
                RS.execSQL("SELECT * from " + table+advclause);
                data = new Object[i][];

                while (RS.rs.next()) {
                    data[count - i] = new Object[2];

                     data[count-i][0]=new String(RS.rs.getString(vc1));
                    //data[count - i][0] = RS.rs.getString(vc1);

                     data[count-i][1]=new String(RS.rs.getString(vc2));
                    //data[count - i][1] = RS.rs.getString(vc2);
                    i--;
                }
            }
        } catch (SQLException e) {
            if(LoggerNB.debuging){System.out.println("Error 59: " + e);}
        } finally {
            if (RS != null) {
                RS.close();
            }
        }

        initcombofromdata();
    }
    //~--- methods ------------------------------------------------------------

    private void initcombofromdata() {

        if(data!=null)
        {
        for (int i = 0; i < data.length; i++) {
            addItem((String) data[i][0]);
        }
        }
        this.revalidate();
    }

    //~--- get methods --------------------------------------------------------

    public String getVc1byVc2(String Vc2) {
        if (data != null) {
            for (int i = 0; i < data.length; i++) {
                if (((String) data[i][1]).equals(Vc2)) {
                    return (String) data[i][0];
                }
            }
        }

        return "";
    }

    public String getVc2byVc1(String Vc1) {
        if (data != null) {
            for (int i = 0; i < data.length; i++) {
                if (((String) data[i][0]).equals(Vc1)) {
                    return (String) data[i][1];
                }
            }
        }

        return "";
    }

    public String getVc1bySelected() {
        if (data != null) {
            int ind=this.getSelectedIndex ();
            return (String) data[ind][0];
        }
        return "";
    }

    public String getVc2bySelected() {
        if (data != null) {
            int ind=this.getSelectedIndex ();
            return (String) data[ind][1];
        }

        return "";
    }
    public String getVc2byid(int id) {
        if (data != null) {
        
            return (String) data[id][1];
        }

        return "";
    }
    //~--- set methods --------------------------------------------------------

    public void setSelectedbyVc1(String Vc1) {
        if (data != null) {
            for (int i = 0; i < data.length; i++)
            {
                if (((String) data[i][0]).equals(Vc1))
                {
                    setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    public void setSelectedbyVc2(String Vc2) {
        if (data != null) {
            for (int i = 0; i < data.length; i++) {
                if (((String) data[i][1]).equals(Vc2)) {
                    setSelectedIndex(i);

                    break;
                }
            }
        }
    }
    public Object[][] getdata()
    {
     return data;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
