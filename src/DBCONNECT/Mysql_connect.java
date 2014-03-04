package DBCONNECT;

//~--- non-JDK imports --------------------------------------------------------

/*
  @author Salomatine Dmitry
  @version 1.00 04/04/20
 */
import Utils.LoggerNB;

//~--- JDK imports ------------------------------------------------------------

import java.sql.*;

//~--- classes ----------------------------------------------------------------

public class Mysql_connect extends DataBaseConnector {
    private int    port = 3306;
    private String host = "localhost";

    //~--- constructors -------------------------------------------------------

    /**
     * Method Mysql_connect
     */
    public Mysql_connect(String dbfile) {
        super(dbfile);
        loadDriver("com.mysql.jdbc.Driver");
    }

    public Mysql_connect(String dbfile, String user, String pass) {
        super(dbfile, user, pass);
        loadDriver("com.mysql.jdbc.Driver");
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

            // set this to a mysql DB you have on your machine
            String database = "jdbc:mysql://" + host + ":" + getPort() + "/";

            // String database ="jdbc:mysql://localhost/";
            database += db_FileName.trim();
            database += "?user=" + db_DBUserName + "&password="
                        + db_DBUserPassword;    // add on to the end

            // now we can get the connection from the DriverManager
            con = DriverManager.getConnection(database);

            // con = DriverManager.getConnection( database );
            connected = true;
        } catch (Exception e)

        // catch (ClassNotFoundException e)
        {
            LoggerNB.getLogger().log("Failed to load JDBC/ODBC driver." + e,
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
