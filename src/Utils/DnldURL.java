package Utils;

import CDate.CDate;
import DBCONNECT.DataBaseConnector;

import java.io.*;

import java.net.*;

//~--- classes ----------------------------------------------------------------

/*  A Class that can be used to download the contents
 *      of a specified URL.
 */
public class DnldURL {
    InputStream           is     = null;
    public BufferedReader d      = null;
    URL                   u      = null;
    public boolean        status = false;
    public String         url;

    //~--- constructors -------------------------------------------------------

    public DnldURL() {
        url = "";
    }

    // also can init from bufered reader tha has been constructed outside
    public DnldURL(BufferedReader reader) {
        this.d = reader;
        status = true;
    }

    // url to read from
    public DnldURL(String url) {
        this.url = url;
        create();
        open();
    }

    

    //~--- methods ------------------------------------------------------------

    // closes conection to url
    public void close() {

        /*
         * try {
         *  is.close();
         * } catch (IOException ioe)
         * {
         *  // just going to ignore this one
         * }
         */
    }

    // creates conection to url
    public void create() {
        try {

            // Step 1:  Create the URL.
            // u = new URL("http://200.210.220.1:8080/index.html");
            u = new URL(url);
        } catch (MalformedURLException mue) {
            if(LoggerNB.debuging){System.out.println("MalformedURLException happened.");
            mue.printStackTrace();}
        }
    }

    // open conection to url
    public void open() {
        try {
            status = true;

            // Step 2:  Open an input stream from the url.  //
            is = u.openStream();    // throws an IOException
        }

        // catch(Exception ioe)
        catch (FileNotFoundException ioe) {
            close();
            status = false;
            if(LoggerNB.debuging){System.out.println("1FileNotFoundException happened." + ioe);}

            return;
        } catch (IOException ioe2) {
            status = false;
            close();
            if(LoggerNB.debuging){System.out.println("1IOException happened." + ioe2);}

            return;
        }

        // Step 4:                                                     //
        // Convert the InputStream to a buffered DataInputStream.      //
        // Buffering the stream makes the reading faster; the          //
        // readLine() method of the DataInputStream makes the reading  //
        // easier.                                                     //
        d = new BufferedReader(new InputStreamReader(is));
    }
    // open conection to url
    public void SaveLocaly(String as) {
        try {
            status = true;

            // Step 2:  Open an input stream from the url.  //
            is = u.openStream();    // throws an IOException
        }

        // catch(Exception ioe)
        catch (FileNotFoundException ioe) {
            close();
            status = false;
            if(LoggerNB.debuging){System.out.println("1FileNotFoundException happened." + ioe);}

            return;
        } catch (IOException ioe2) {
            status = false;
            close();
            if(LoggerNB.debuging){System.out.println("1IOException happened." + ioe2);}

            return;
        }

        // Step 4:                                                     //
        // save the InputStream to a FileOutputStream.      //
        try {
        OutputStream out = new FileOutputStream(as);
    
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = is.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        is.close();
        out.close();
    } catch (IOException e) {
    }
    }

    // void read(msdb_connect data,int table){}
    public void read() {}

    public void read(DataBaseConnector data, int table) {}

    /* reads must be defined */
    public void read(DataBaseConnector data, int table, CDate last) {}
}


//~ Formatted by Jindent --- http://www.jindent.com
