package CDate;

//~--- JDK imports ------------------------------------------------------------

import Utils.LoggerNB;
import java.util.Calendar;
import java.util.Date;

//~--- classes ----------------------------------------------------------------

public class CDate {
    private static final int    daysPerMonth[] = {
        31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };
    private static final String monthNames[]   = {
        "Januar", "Februar", "March", "April", "May", "Juny", "July", "August",
        "September", "October", "November", "December"
    };
    private static final char rulez[]  = {
        '-', '_', '/', '@', '#', '$', ':', ';'
    };
    private static final char nrulez[] = {
        ' ', ',', '.', '*', '+', '\\', '~', '!', '`', '%', '^', '&', '[', ']',
        '?', '<', '>', '"', '=', '{', '}'
    };

    //~--- fields -------------------------------------------------------------

    private int d = 1;

    // calendars month is zero based and my month 1's based
    private int m = 1;
    private int y = 2000;

    //~--- constructors -------------------------------------------------------

    public CDate() {
        setDate();
    }

    public CDate(CDate td) {
        d = td.getDay();
        m = td.getMonth();
        y = td.getYear();
    }

    public CDate(Date date) {
        setDate(date);
    }

    public CDate(String date) {
        try {
            parse(date);
        } catch (CDateException e) {
            if(LoggerNB.debuging){System.out.println("1Err" + e);}
        }
    }

    public CDate(String date, char delim) {
        try {
            parse(date, delim,false);
        } catch (CDateException e) {
            if(LoggerNB.debuging){System.out.println("2Err" + e);}
        }
    }

    public CDate(int day, int month, int year) {
        setDate(day, month, year);
    }

    public CDate(String day, String month, String year) {
        setDate(day, month, year);
    }

    //~--- methods ------------------------------------------------------------

    private int checkDay(int day) throws CDateException {
        if ((day > 0) && (day <= daysPerMonth[m - 1])) {
            return day;
        } else if ((m == 2) && (day == 29)
                   && ((y % 400 == 0) || ((y % 4 == 0) && (y % 100 != 0)))) {
            return day;
        }

        // else
        throw new CDateException("11Exception CDate day incorect " + day
                                 + " on month " + m);
    }

    private int checkDay(String day) throws CDateException {
        return checkDay(Integer.parseInt(day));
    }

    private int checkMonth(int month) throws CDateException {
        if ((month > 0) && (month <= 12)) {
            return month;
        }

        // else
        throw new CDateException("9Exception CDate month incorect " + month);
    }

    private int checkMonth(String month) throws CDateException {
        int t;

        if ((t = validFullMonthName(month)) > 0) {
            return t;
        } else if (month.length() == 3) {
            for (t = 0; t < monthNames.length; t++) {

                // verry fast match 3 first chars ignore case
                if (month.regionMatches(true, 0, monthNames[t], 0, 3)) {
                    return t + 1;
                }
            }
        }

        try {
            int mi = Integer.parseInt(month);

            return checkMonth(mi);
        } catch (Exception e) {
            throw new CDateException("10Exception CDate month incorect "
                                     + month + " " + e);
        }
    }

    // will accepted years from 0-35 -> 20xx
    // will accepted years from 36-99 -> 19xx
    // will accepted years from 1900-2100 -> 1900-2100
    private int checkYear(int year) throws CDateException {
        if ((year >= 36) && (year <= 99)) {
            return year + 1900;
        } else if ((year >= 0) && (year <= 35)) {
            return year + 2000;
        } else if ((year >= 1900) && (year <= 2100)) {
            return year;
        }

        // else
        throw new CDateException("8Exception CDate year incorect " + year);
    }

    private int checkYear(String year) throws CDateException {
        return checkYear(Integer.parseInt(year));
    }

    public void cm() {
        m = m - 1;
    }

    public int dayofweek() {
        Calendar rightNow = Calendar.getInstance();

        rightNow.set(Calendar.DAY_OF_MONTH, d);
        rightNow.set(Calendar.MONTH, m - 1);
        rightNow.set(Calendar.YEAR, y);

        return rightNow.get(Calendar.DAY_OF_WEEK);
    }

    public long diff(CDate v) {
        Calendar rightNow = Calendar.getInstance();

        rightNow.set(Calendar.DAY_OF_MONTH, d);
        rightNow.set(Calendar.MONTH, m - 1);
        rightNow.set(Calendar.YEAR, y);

        Calendar rightNow2 = Calendar.getInstance();

        rightNow2.set(Calendar.DAY_OF_MONTH, v.getDay());
        rightNow2.set(Calendar.MONTH, v.getMonth() - 1);
        rightNow2.set(Calendar.YEAR, v.getYear());

        return (rightNow.getTimeInMillis() - rightNow2.getTimeInMillis())
               / 86400 / 1000;
    }

    private char[] excludeDelimiter(char cd) {
        char ret[] = new char[nrulez.length + rulez.length - 1];
        int  i, j;

        for (i = 0; i < nrulez.length; i++) {
            ret[i] = nrulez[i];
        }

        for (j = 0; j < rulez.length; j++) {
            if (rulez[j] != cd) {
                ret[i++] = rulez[j];
            }
        }

        return ret;
    }

    public boolean greater(CDate v) {
        Calendar rightNow = Calendar.getInstance();

        rightNow.set(Calendar.DAY_OF_MONTH, d);
        rightNow.set(Calendar.MONTH, m - 1);
        rightNow.set(Calendar.YEAR, y);

        Calendar rightNow2 = Calendar.getInstance();

        rightNow2.set(Calendar.DAY_OF_MONTH, v.getDay());
        rightNow2.set(Calendar.MONTH, v.getMonth() - 1);
        rightNow2.set(Calendar.YEAR, v.getYear());

        return rightNow.after(rightNow2);
    }

    public boolean less(CDate v) {
        Calendar rightNow = Calendar.getInstance();

        rightNow.set(Calendar.DAY_OF_MONTH, d);
        rightNow.set(Calendar.MONTH, m - 1);
        rightNow.set(Calendar.YEAR, y);

        Calendar rightNow2 = Calendar.getInstance();

        rightNow2.set(Calendar.DAY_OF_MONTH, v.getDay());
        rightNow2.set(Calendar.MONTH, v.getMonth() - 1);
        rightNow2.set(Calendar.YEAR, v.getYear());

        return rightNow.before(rightNow2);
    }

    /**
     * Method parse - parses given string with "-" delimiter
     * example with parse("1-Dec-2005")
     * do not use "." delimiter in split
     * @param st
     *
     * @return
     *
     */
    public void parse(String st) throws CDateException {
        parse(st, '-',false);
    }

    /**
     * Method parse - parses given string according to given delimiter
     * example with parse("1/12/2005","/")
     * example with parse("1-Dec-2005","-")
     * do not use "." delimiter in split
     * any delimiter not [-,_,/,@,#,$,:,;] will be removed
     * @param st
     * @param delim
     *
     * @return
     *
     */
    public void parse(String st, char delim,boolean DYinterchange) throws CDateException {

        // System.out.println(st);
        int    i, j;
        char[] ed = excludeDelimiter(delim);

        st = st.trim();    // remove blanks before and after

        // remove excluded caracters
        for (i = 0; i < st.length(); i++) {
            for (j = 0; j < ed.length; j++) {
                if (st.charAt(i) == ed[j]) {
                    if (i == 0) {
                        st = st.substring(1, st.length() - 1);
                    } else {
                        st = st.substring(0, i - 1)
                             + st.substring(i + 1, st.length() - 1);
                    }

                    i--;

                    break;
                }
            }
        }

        // remove delimiter duplicates before and after
        if (st.length() > 0) {
            while (st.charAt(0) == delim) {
                st = st.substring(1, st.length());
            }

            while (st.charAt(st.length() - 1) == delim) {
                st = st.substring(0, st.length() - 1);
            }
        }

        if (st.length() < 5) {
            throw new CDateException(
                "12Exception CDate format minimal (1.1.1) 5 chars " + st);
        }

        // remove delimiter duplicates beteewn
        for (i = 0; i < st.length() - 1; i++) {
            if ((st.charAt(i) == delim)
                    && (st.charAt(i) == st.charAt(i + 1))) {
                st = st.substring(0, i) + st.substring(i + 2, st.length());

                if (st.charAt(i) == st.charAt(i + 1)) {
                    i--;
                }
            }
        }

        // System.out.println(st);
        // String s[]=st.split(new String("")+delim);
        String s[] = st.split("" + delim);

        // for(i=0;i<s.length;i++)System.out.println(s[i]);
        if (s.length != 3) {
            throw new CDateException(
                "13Exception CDate format minimal (dd.MMM.yyyy) " + st);
        }
        if(DYinterchange==true)
        setDate(s[2], s[1], s[0]);
        else
        setDate(s[0], s[1], s[2]);
    }

    public void roll(int days) {
        Calendar rightNow = Calendar.getInstance();

        rightNow.set(Calendar.DAY_OF_MONTH, d);
        rightNow.set(Calendar.MONTH, m - 1);
        rightNow.set(Calendar.YEAR, y);

        int td = days % 30;
        int ty = days / 365;
        int tm = (days / 30) % 12;

        // private roll 1 day case
        if (days == 1) {
            if (d + 1 > daysPerMonth[m - 1]) {
                tm = 1;
            }

            // also if it is 12 month then after rolling to next month the year increases too
            if ((m == 12) && (tm == 1)) {
                ty = 1;
            }
        }

        rightNow.roll(Calendar.DAY_OF_MONTH, td);
        rightNow.roll(Calendar.YEAR, ty);
        rightNow.roll(Calendar.MONTH, tm);

        if ((tm < 0) && (m - Math.abs(tm) < 0)) {
            rightNow.roll(Calendar.YEAR, -1);
        }

        try {
            y = checkYear(rightNow.get(Calendar.YEAR));
            m = checkMonth(rightNow.get(Calendar.MONTH) + 1);
            d = checkDay(rightNow.get(Calendar.DAY_OF_MONTH));
        } catch (CDateException e) {
           if(LoggerNB.debuging){ System.out.println("6Err" + e);}
        } catch (NumberFormatException nfe) {
            if(LoggerNB.debuging){System.out.println("7Err" + nfe);}
        }
    }

    public String toString() {
        return "" + d + "-" + m + "-" + y;
    }

    // calendars month is zero based and my month 1's based
    public String toStringCalendaric() {
        return "" + d + "-" + (m - 1) + "-" + y;
    }

    public String toStringDelimited(char delim) {
        return "" + d + delim + m + delim + y;
    }
public String toStringDelimitedSQLANSI(char delim) {
       String ltd="";
       String ltm="";
       if(d<10)ltd="0"+d;else ltd=""+d;
       if(m<10)ltm="0"+m;else ltm=""+m;
        return "" + y + delim + ltm + delim + ltd;
    }
    // calendars month is zero based and my month 1's based
    // will return Apr-2005 for example
    public String toStringMonthYear() {
        return monthNames[m - 1].substring(0, 3) + "-" + y;
    }

    // calendars month is zero based and my month 1's based
    // will return Apr-2005 for example
    public String toStringMonthYear_Short() {
        return monthNames[m - 1].substring(0, 3) + "-" + ((y >= 2000)
                ? y - 2000
                : y);
    }

    public String toStringTaseFileDate() {
        String tms = "";
        String tds = "";

        if (m < 10) {
            tms = "0" + m;
        } else {
            tms = "" + m;
        }

        if (d < 10) {
            tds = "0" + d;
        } else {
            tds = "" + d;
        }

        return "" + y + tms + tds + "0";
    }

    private int validFullMonthName(String month) {
        for (int i = 0; i < monthNames.length; i++) {
            if (month.equalsIgnoreCase(monthNames[i])) {
                return i + 1;
            }
        }

        return -1;
    }

    //~--- get methods --------------------------------------------------------

    public Date getDate() {
        return new Date(getDateMilis());
    }

    public long getDateMilis() {
        Calendar rightNow = Calendar.getInstance();

        rightNow.set(Calendar.DAY_OF_MONTH, d);
        rightNow.set(Calendar.MONTH, m - 1);
        rightNow.set(Calendar.YEAR, y);

        return rightNow.getTimeInMillis();
    }

    public int getDay() {
        return d;
    }

    // calendars month is zero based and my month 1's based
    public int getMonth() {
        return m;
    }

    public int getMonthCalendar() {
        return m - 1;
    }

    public int getWeekOfYear() {
        Calendar rightNow = Calendar.getInstance();

        rightNow.set(Calendar.DAY_OF_MONTH, d);
        rightNow.set(Calendar.MONTH, m - 1);
        rightNow.set(Calendar.YEAR, y);

        return rightNow.get(Calendar.WEEK_OF_YEAR);
    }

    public int getYear() {
        return y;
    }

    //~--- set methods --------------------------------------------------------

    public void setDate() {
        Calendar rightNow = Calendar.getInstance();

        setDate(rightNow.get(Calendar.DAY_OF_MONTH),
                rightNow.get(Calendar.MONTH) + 1, rightNow.get(Calendar.YEAR));
    }

    public void setDate(Date date) {
        Calendar rightNow = Calendar.getInstance();

        rightNow.setTime(date);
        setDate(rightNow.get(Calendar.DAY_OF_MONTH),
                rightNow.get(Calendar.MONTH) + 1, rightNow.get(Calendar.YEAR));
    }

    public void setDate(int day, int month, int year) {
        try {
            y = checkYear(year);
            m = checkMonth(month);
            d = checkDay(day);
        } catch (CDateException e) {
            if(LoggerNB.debuging){System.out.println("3Err" + e);}
        }
    }

    public void setDate(String day, String month, String year) {
        try {
            y = checkYear(year);
            m = checkMonth(month);
            d = checkDay(day);
        } catch (CDateException e) {
            if(LoggerNB.debuging){System.out.println("4Err" + e);}
        } catch (NumberFormatException nfe) {
            if(LoggerNB.debuging){System.out.println("5Err" + nfe);}
        }
    }

    public void parseDYinterchanged (String st)throws CDateException {
        parse(st, '-',true);
    }
    
}


//~ Formatted by Jindent --- http://www.jindent.com
