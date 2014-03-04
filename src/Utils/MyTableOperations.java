/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Dmitry
 */
public class MyTableOperations {
     // Removes the specified column from the table and the associated
    // call data from the table model.
    public static void removeColumnAndData(JTable table, int vColIndex) {
        // This subclass adds a method to retrieve the columnIdentifiers
    // which is needed to implement the removal of
    // column data from the table model
    
        // Disable autoCreateColumnsFromModel to prevent
    // the reappearance of columns that have been removed but
    // whose data is still in the table model
    table.setAutoCreateColumnsFromModel(false);
    

        MyDefaultTableModel model = (MyDefaultTableModel)table.getModel();
        TableColumn col = table.getColumnModel().getColumn(vColIndex);
        int columnModelIndex = col.getModelIndex();
        Vector data = model.getDataVector();
        Vector colIds = model.getColumnIdentifiers();
    
        // Remove the column from the table
        table.removeColumn(col);
    
        // Remove the column header from the table model
        colIds.removeElementAt(columnModelIndex);
    
        // Remove the column data
        for (int r=0; r<data.size(); r++) {
            Vector row = (Vector)data.get(r);
            row.removeElementAt(columnModelIndex);
        }
        model.setDataVector(data, colIds);
    
        // Correct the model indices in the TableColumn objects
        // by decrementing those indices that follow the deleted column
        Enumeration enume = table.getColumnModel().getColumns();
        for (; enume.hasMoreElements(); ) {
            TableColumn c = (TableColumn)enume.nextElement();
            if (c.getModelIndex() >= columnModelIndex) {
                c.setModelIndex(c.getModelIndex()-1);
            }
        }
        model.fireTableStructureChanged();
         table.setAutoCreateColumnsFromModel(true);
    }
    public static void adddata(JTable t,Object[] o)
    {
         DefaultTableModel model = (DefaultTableModel) t.getModel();
         model.addRow(o);
    }
   

    public static void creartable(JTable t)
    {
    DefaultTableModel model =
  (DefaultTableModel)t.getModel();
   while(model.getRowCount()>0)model.removeRow(0);
    }
    public static void hideColumnN(JTable t,String columnname) {
        TableColumn tcg = t.getColumn(columnname);

        tcg.setPreferredWidth(0);
        tcg.setMinWidth(0);
        tcg.setMaxWidth(0);
    }
}
