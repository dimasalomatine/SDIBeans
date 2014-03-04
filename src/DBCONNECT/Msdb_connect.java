package DBCONNECT;

import Utils.LoggerNB;

//~--- JDK imports ------------------------------------------------------------

import java.sql.*;

//~--- classes ----------------------------------------------------------------

public class Msdb_connect extends DataBaseConnector {

    /**
     * Method Msdb_connect
     */
    public Msdb_connect(String dbfile) {
        super(dbfile);
        loadDriver("sun.jdbc.odbc.JdbcOdbcDriver");
    }

    public Msdb_connect(String dbfile, String user, String pass) {
        super(dbfile, user, pass);
        loadDriver("sun.jdbc.odbc.JdbcOdbcDriver");
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

            // set this to a MS Access DB you have on your machine
            String database =
                "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";

            database += db_FileName.trim();
            database += ";DriverID=22;READONLY=false}";    // add on to the end

            // now we can get the connection from the DriverManager
            con = DriverManager.getConnection(database, db_DBUserName,
                                              db_DBUserPassword);
            connected = true;

            // and check login as user table valid instance
            if (db_DBUserName.length() > 0) {
                connected = isRightUserInUT();
            }
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

    private boolean isRightUserInUT() {
        TRS rs = new TRS(con);

        return rs.rowExist("SELECT * FROM USERS WHERE USER='" + db_DBUserName
                           + "' AND PASS='" + db_DBUserPassword + "'");
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
