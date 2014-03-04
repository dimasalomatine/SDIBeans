/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dmitry
 */
public class MyDefaultTableModel extends DefaultTableModel {
        public Vector getColumnIdentifiers() {
            return columnIdentifiers;
        }
    }
