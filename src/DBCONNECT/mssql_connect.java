/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DBCONNECT;

import Utils.LoggerNB;

//~--- JDK imports ------------------------------------------------------------

import java.sql.*;


/*
  @author Salomatine Dmitry
  @version 1.00 04/04/20
 */

//~--- classes ----------------------------------------------------------------

public class mssql_connect extends DataBaseConnector {
    private int    port = 1433;
    private String host = "127.0.0.1";
    

    //~--- constructors -------------------------------------------------------

    /**
     * Method Mysql_connect
     */
    public mssql_connect(String dbfile,String _drvname,String _inst) {
        super(dbfile);
        db_Instance=_inst;
       //  _drvname="net.sourceforge.jtds.jdbc.Driver";
        loadDriver(_drvname);
          //loadDriver("net.sourceforge.jtds.jdbc.Driver");
    }

    public mssql_connect(String dbfile, String user, String pass,String _drvname,String _inst) {
        super(dbfile, user, pass);
        db_Instance=_inst;
         // _drvname="net.sourceforge.jtds.jdbc.Driver";
        loadDriver(_drvname);
          //loadDriver("net.sourceforge.jtds.jdbc.Driver");
    }

    //~--- methods ------------------------------------------------------------

    /**
     * Method connect
     *
     * @return connection status string
     */
    public boolean connect() {
        if (connected) {
            disConnect();
        }

        try {

            // set this to a mssql DB you have on your machine

                    String database = driverStr + host + ":" + getPort() ;
            //String database = "jdbc:jtds:sqlserver:" + host + ":" + getPort() + "/";

             if(dbstringtype==2) database +=";databaseName=";//for mssql
                    else database +="/";//for jtds
            database += db_FileName.trim();

            if(db_Instance.length()>0)
             database += ";instance="+db_Instance.trim();

            database += ";appName=Batool";
          
            // now we can get the connection from the DriverManager
            con = DriverManager.getConnection(database,db_DBUserName,db_DBUserPassword);

            // con = DriverManager.getConnection( database );
            connected = true;
        } catch (Exception e)

        // catch (ClassNotFoundException e)
        {
            LoggerNB.getLogger().log("Failed to connect JDBC driver:"+driverName +"\n"+ e,
                                      LoggerNB.CRITICAL);
            connected = false;
        }

        return connected;
    }

    //~--- get methods --------------------------------------------------------

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    //~--- set methods --------------------------------------------------------

    public boolean setHost(String th) {
        if (th.length() > 0) {
            host = th;
        } else {
            LoggerNB.getLogger().log(
                "Host " + th + " is out of valid ranges",
                LoggerNB.INFORMATIVE);

            return false;
        }

        return true;
    }

    public boolean setPort(int tp) {
        if ((tp >= 1024) && (tp <= 6400)) {
            port = tp;
        } else {
            LoggerNB.getLogger().log(
                "Port " + tp + " is out of valid ranges",
                LoggerNB.INFORMATIVE);

            return false;
        }

        return true;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com

