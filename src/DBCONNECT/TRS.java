package DBCONNECT;

//~--- JDK imports ------------------------------------------------------------

/*
  @author Salomatine Dmitry
  @version 1.00 04/04/20
 */
import Utils.LoggerNB;
import java.sql.*;

//~--- classes ----------------------------------------------------------------

public class TRS {
    public ResultSet  rs = null;
    public Statement  s  = null;
    public Connection con;

    //~--- constructors -------------------------------------------------------

    public TRS(Connection con) {
        this.con = con;
        create();
    }

    //~--- methods ------------------------------------------------------------

    public void close() {
        try {
            if (rs != null) {
                rs.close();
            }

            rs = null;

            if (s != null) {
                s.close();
            }

            s = null;
        } catch (SQLException e) {
            if(LoggerNB.debuging){System.out.println("Error14a: " + e);}
        }
    }

    public void create() {
        close();

        try {
            /*
            s = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);
             * */
               s = con.createStatement();
        } catch (SQLException e) {
            if(LoggerNB.debuging){System.out.println("Error14a: " + e);}
        }
    }

    public void create(Connection con) {
        close();
        this.con = con;

        try {
            /*
            s = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);
             * */
            s = con.createStatement();
        } catch (SQLException e) {
            if(LoggerNB.debuging){System.out.println("Error14a: " + e);}
        }
    }

    /*
     * Method CreateTable
     * @param sql - is a SQL to check actually some create table ... WHERE ...
     * @return true if done
     */
    public void createTable(String sql) throws SQLException {
        try {
            con.prepareStatement(sql);
            s.execute(sql);
        } catch (SQLException e) {

            // it throws exeption but we can do not throw anyting
            if (e.toString().indexOf("already exists") == -1) {
                throw e;
            }
        }
    }

    public ResultSet execSQL(String sqlstr) {
        try {
            //con.prepareStatement(sqlstr);

            if (sqlstr.startsWith("SELECT")) {
               // s.execute(sqlstr);
                //rs = s.getResultSet();
                rs=s.executeQuery(sqlstr);
                
            } else    // if(sqlstr.startsWith("UPDATE"))
            {
                s.executeUpdate(sqlstr);
            }
        } catch (SQLException e) {
            if(LoggerNB.debuging){System.out.println("Error16: " + e);}
        }

        return rs;
    }

    public void reuse() {
        close();
        create();
    }

    /*
     * Method RowExist
     * @param sql - is a SQL to check actually some SELECT * FROM ... WHERE ...
     * @return true if exact SQL exist
     */
    public boolean rowExist(String sql) {
        boolean status = false;

        try {
            ResultSet trs = execSQL(sql);

            if (trs.next()) {
                status = true;
            }
        } catch (SQLException e) {
            if(LoggerNB.debuging){System.out.println("Error16: " + e);}
        }

        return status;
    }

    /*
     * Method RowExistID
     * @param sql - is a SQL to check actually some SELECT * FROM ... WHERE ...
     * @return true if exact SQL exist
     */
    public int rowExistID(String sql, String idfield) {
        int id = -1;

        try {
            ResultSet trs = execSQL(sql);

            if (trs.next()) {
                id = trs.getInt(idfield);
            }
        } catch (SQLException e) {
            if(LoggerNB.debuging){System.out.println("Error16: " + e);}
        }

        return id;
    }
    public String getSpecValue(String sql, String idfield) {
         String ret= "";

        try {
            ResultSet trs = execSQL(sql);

            if (trs.next()) {
                ret = trs.getString(idfield);
            }
        } catch (SQLException e) {
            if(LoggerNB.debuging){System.out.println("Error16: " + e);}
        }

        return ret;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
