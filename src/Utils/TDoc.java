package Utils;

/*
  Temp data object definition class
   can store variant object types most times used with lists
  @author Salomatine Dmitry
  @version 1.00 04/04/20
*/
public class TDoc {
    public Object  o[]     = null;
    public boolean changed = false;
   public Object  oadv[]     = null;
    //~--- constructors -------------------------------------------------------

    public TDoc(int sizeofdata) {
        o = new Object[sizeofdata];
    }

    //~--- get methods --------------------------------------------------------

    int getSize() {
        if (o != null) {
            return o.length;
        }

        return 0;
    }
    public void safeResize(int newsize)
    {
     if(newsize<o.length)return;
     Object to[]=new Object[newsize];
     for(int i=0;i<o.length;i++)to[i]=o[i];
     o=to;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
