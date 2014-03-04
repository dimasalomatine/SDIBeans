

package Utils;


import java.io.IOException;
import java.util.List;

/**
 *
 * @author Dmitry
 */
public class ReadFromFileURL  extends DnldURL{
    
    public ReadFromFileURL(String url)
    {
   super(url);
     
    }
    public java.util.List  readtolist( ) {
        java.util.List l=new java.util.ArrayList();
        if (!status) {
            return l;
        }
        String       s   = "";
        try {

            // read data to end of file
            while ((s = d.readLine()) != null) {
                if (s.startsWith("#")) {
                    continue;
                }
                l.add(s);

                    }
        } catch (IOException ioe) {
            System.out.println("1 1 IOException happened.");
        }
    return l;
    }

    public java.util.List read(String ts) {
       java.util.List l=new java.util.ArrayList();
    
        String       sp[]   = ts.split("\n");
        // read data to end of string
        for(int i=0;i<sp.length;i++)
            {
             sp[i]=sp[i].replace("\r", "\r");
                if (sp[i].startsWith("#")) {
                    continue;
                }
                l.add(sp[i]);
            }
    return l;
    }
}
