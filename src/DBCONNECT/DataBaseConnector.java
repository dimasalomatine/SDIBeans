package DBCONNECT;

import Utils.LoggerNB;

//~--- JDK imports ------------------------------------------------------------

import java.sql.*;
import java.sql.DatabaseMetaData;

import java.util.*;

//~--- classes ----------------------------------------------------------------

public class DataBaseConnector {
    protected boolean connected           = false;
    String            db_FileName         = null;
    String            db_DBUserPassword   = "";
    String            db_DBUserName       = "";
    private boolean   passlimitenabled    = true;
    private int       passizelimitup      = 8;
    private int       passizelimitd       = 4;
    private boolean   enablemixedpassword = false;
    public Connection con;
    private Class     driver;
    protected String     driverName;
    protected String     driverStr;
    protected String     db_Instance="";
    protected int dbstringtype=0;

    //~--- constructors -------------------------------------------------------

    DataBaseConnector(String filename) {
        db_FileName = filename;
    }

    DataBaseConnector(String filename, String user, String pass) {
        db_FileName       = filename;
        db_DBUserName     = user;
        db_DBUserPassword = pass;
    }

    //~--- methods ------------------------------------------------------------

    public java.util.List listTables() {
        java.util.List tl = new ArrayList();
        String         str;

        try {
            DatabaseMetaData md = con.getMetaData();

            System.out.println("Using: " + md.getDatabaseProductName());
            System.out.println("Version: " + md.getDatabaseProductVersion());

            int txisolation = md.getDefaultTransactionIsolation();

            System.out.println("Database default transaction isolation: "
                               + txisolation + " ("
                               + transactionIsolationToString(txisolation)
                               + ").");

            ResultSet rs = md.getTables(null, null, "%", null);

            while (rs.next()) {
                str = rs.getString(3);
                tl.add(str);
                if(LoggerNB.debuging){System.out.println(str);}
            }
        } catch (SQLException e) {
            if(LoggerNB.debuging){System.out.println("Error14b3: " + e);}
        }

        return tl;
    }

    /**
     * Method loadDriver
     * Connection con
     * on oracle driver conection will be:
     * con = DriverManager.getConnection("jdbc:oracle:thin:@dssw2k01:1521:orcl","scott","tiger");
     * on access driver conection will be:
     * con = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ="+filename+";DriverID=22;READONLY=false}" ,db_DBUserName,db_DBUserPassword);
     * on mysql driver connection will be:
     * con = DriverManager.getConnection("jdbc:mysql://192.168.1.25/accounts?user=spider&password=spider");
     * 
     * @return 
     */
    public boolean loadDriver(String _drivername) {
        try {
            driverName=_drivername;
           
            // Try to load an Oracle driver.
            if (_drivername.endsWith("OracleDriver")) {
                driver = Class.forName("oracle.jdbc.driver.OracleDriver");
            } else if (_drivername.endsWith("JdbcOdbcDriver")) {
                driver = Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            } else if (_drivername.equals("com.mysql.jdbc.Driver")) {
                driver = Class.forName("com.mysql.jdbc.Driver");    // .newInstance();
            }else if (_drivername.equals("net.sourceforge.jtds.jdbc.Driver")) {
                driver = Class.forName("net.sourceforge.jtds.jdbc.Driver");    // .newInstance();

                driverStr="jdbc:jtds:sqlserver:";
            }
            else if (_drivername.equals("com.microsoft.sqlserver.jdbc.SQLServerDriver")) {
                driver = Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");    // .newInstance();

                driverStr="jdbc:sqlserver:";
                dbstringtype=2;
            }
            else {
                
                LoggerNB.getLogger().log("No valid Driver name spesified",LoggerNB.CRITICAL);

                return false;
            }
        } catch (ClassNotFoundException ex) {
            if(LoggerNB.debuging){System.out.println(ex);}

            return false;
        }

        System.out.println("Loaded " + driver.getName());

        return true;
    }

    /**
     * Method Connect
     *
     *
     * @return
     *
     */
    public boolean connect() {
        return false;
    }

    /*
     * Method CreateNewRow_begin - works in pair with CreateNewRow_end
     * @param intable - table to ctreate new row in
     * @param getcolumns - exact column names otherwise SELECT * executed and exeption thrown
     * @param where - where clause otherwise can be retrieved many rows
     * @deprecated
     */
    public ResultSet createNewRow_begin(String intable, String getcolumns,
            String where) {
        ResultSet rs  = null;
        String    sql = "SELECT " + getcolumns + " FROM " + intable;

        if (where != null) {
            sql += " WHERE " + where;
        }

        try {
            rs = execSQL(sql);
            rs.moveToInsertRow();    // moves cursor to the insert row
        } catch (Exception e) {
            if(LoggerNB.debuging){System.out.println("Error14b1: " + e);}
        }

        /*
         *  then outside call to update methods such as
         * rs.updateString(1, "AINSWORTH"); // updates the first column of the insert row to be AINSWORTH
         * rs.updateInt(2,35); // updates the second column to be 35
         * rs.updateBoolean(3, true); // updates the third column to true
         * then outside you must call CreateNewRow_end(rs);
         */
        return rs;
    }

    /*
     * Method CreateNewRow_end - works in pair with CreateNewRow_begin
     * @param rs - to add to
     * @deprecated
     * @return false if some err ocured
     */
    public boolean createNewRow_end(ResultSet rs) {
        boolean ret = true;    // all ok

        try {
            rs.insertRow();

            // rs.moveToCurrentRow();
        } catch (Exception e) {
            if(LoggerNB.debuging){System.out.println("Error14b2: " + e);}
            ret = false;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                rs = null;
            } catch (Exception e) {
                if(LoggerNB.debuging){System.out.println("Inside finally: " + e);}
            }
        }

        return ret;
    }

    /**
     * Method disConnect
     *
     * @return disconnection status string
     */
    public boolean disConnect() {
        try {
            if (connected == true) {    // close the Connection to let the database know we're done with it
                con.close();
                connected = false;
            }
        } catch (SQLException e) {
            if(LoggerNB.debuging){System.out.println(e);}
        }

        return connected;
    }

    /*
     * Examples on sql execution
     * s.execute("create table TEST12345 ( column_name integer )"); // create a table
     * s.execute("insert into TEST12345 values(1)"); // insert some data into the table
     * s.execute("select column_name from TEST12345"); // select the data from the table
     * ResultSet rs = s.getResultSet(); // get any ResultSet that came from our query
     * if (rs != null) // if rs == null, then there is no ResultSet to view
     * while ( rs.next() ) // this will step through our data row-by-row
     *       {
     *       // the next line will get the first column in our current row's ResultSet
     *       //as a String ( getString( columnNumber) ) and output it to the screen
     *      System.out.println("Data from column_name: " + rs.getString(1) );
     *  }
     *  s.execute("drop table TEST12345");
     */
    public ResultSet execSQL(String sqlstr) {
        ResultSet rs = null;
        Statement s  = null;

        try {
            if (connected == true) {
                // try and create a java.sql.Statement so we can run queries
                s = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);
                con.prepareStatement (sqlstr);

                if (sqlstr.startsWith("SELECT")) {
                    s.execute(sqlstr);
                    rs = s.getResultSet();
                } else if (sqlstr.startsWith("UPDATE")) {
                    s.executeUpdate(sqlstr);
                } else if (sqlstr.startsWith("INSERT")) {
                    s.executeUpdate(sqlstr);
                } else {
                    s.execute(sqlstr);
                }
                
              s.close ();
            }
        } catch (Exception e) {
            if(LoggerNB.debuging){System.out.println("Error16: " + e);}
        }
        
        return rs;
    }
     public ResultSet execSQLnoClose(String sqlstr) {
        ResultSet rs = null;
        Statement s  = null;

        try {
            if (connected == true) {
                // try and create a java.sql.Statement so we can run queries
                s = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);
                con.prepareStatement (sqlstr);

                if (sqlstr.startsWith("SELECT")) {
                    s.execute(sqlstr);
                    rs = s.getResultSet();
                } else if (sqlstr.startsWith("UPDATE")) {
                    s.executeUpdate(sqlstr);
                } else if (sqlstr.startsWith("INSERT")) {
                    s.executeUpdate(sqlstr);
                } else {
                    s.execute(sqlstr);
                }

             // s.close ();
            }
        } catch (Exception e) {
            if(LoggerNB.debuging){System.out.println("Error16: " + e);}
        }

        return rs;
    }

    public TRS execSQL_SV(String sqlstr) {
        TRS RS = null;

        try {
            if (connected == true) {
                RS = new TRS(con);
                con.prepareStatement(sqlstr);

                if (sqlstr.startsWith("SELECT")) 
                {
                    RS.s.execute(sqlstr);
                    RS.rs = RS.s.getResultSet();
                } else    // if(sqlstr.startsWith("UPDATE"))
                {
                    RS.s.executeUpdate(sqlstr);
                }
                
                RS.s.close ();
            }
        } catch (SQLException e) 
        {
            if(LoggerNB.debuging){System.out.println("Error16: " + e);}
        }

        return RS;
    }

    private boolean passmixed(String pass) {
        return true;
    }

    private boolean passwordvalid(String pass) {
        return true;

        /*
         * if(pass!=null&&pass.length()>0&&
         *  (passlimitenabled&&(pass.length()<=passizelimitup&&pass.length()>=passizelimitd))&&
         *  (enablemixedpassword&&passmixed(pass)))return true;
         * return false;
         */
    }

    /*
     * Method RowExist
     * @param sql - is a SQL to check actually some SELECT * FROM ... WHERE ...
     * @return true if exact SQL exist
     */
    public boolean rowExist(String sql) {
        boolean status = false;
        TRS  RS     = null;

        try {
            if (!connected) {
                throw new Exception("DB is DOWN");
            }

            RS = execSQL_SV(sql);

            if (RS.rs.next()) {
                status = true;
            }
        } catch (SQLException e) {
            if(LoggerNB.debuging){System.out.println("Error16: " + e);}
        } catch (Exception e) {
            if(LoggerNB.debuging){System.out.println("Error17: " + e);}
        } finally {
            if (RS != null) {
                RS.close();
            }
        }

        return status;
    }

    /**
     * Convert a TransactionIsolation int (defined in java.sql.Connection) to
     * the corresponding printable string.
     */
    public static String transactionIsolationToString(int txisolation) {
        switch (txisolation) {
        case Connection.TRANSACTION_NONE :

            // transactions not supported.
            return "TRANSACTION_NONE";

        case Connection.TRANSACTION_READ_UNCOMMITTED :

            // All three phenomena can occur
            return "TRANSACTION_NONE";

        case Connection.TRANSACTION_READ_COMMITTED :

            // Dirty reads are prevented; non-repeatable reads and
            // phantom reads can occur.
            return "TRANSACTION_READ_COMMITTED";

        case Connection.TRANSACTION_REPEATABLE_READ :

            // Dirty reads and non-repeatable reads are prevented;
            // phantom reads can occur.
            return "TRANSACTION_REPEATABLE_READ";

        case Connection.TRANSACTION_SERIALIZABLE :

            // All three phenomena prvented; slowest!
            return "TRANSACTION_SERIALIZABLE";

        default :
            throw new IllegalArgumentException(txisolation
                                               + " not a valid TX_ISOLATION");
        }
    }

    //~--- set methods --------------------------------------------------------

    public boolean setPass(String pass) {
        if (passwordvalid(pass)) {
            db_DBUserPassword = pass;
        } else {
            LoggerNB.getLogger().log("password **** not valid",
                                      LoggerNB.CRITICAL);

            return false;
        }

        return true;
    }

    public boolean setUser(String user) {
        if ((user != null) && (user.length() > 0)) {
            db_DBUserName = user;
        } else {
            LoggerNB.getLogger().log("user not valid" + user,
                                      LoggerNB.CRITICAL);

            return false;
        }

        return true;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
