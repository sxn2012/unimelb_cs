package whiteboard;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;



public class TableModel extends AbstractTableModel implements ModelListener
{
    private String[] columns = {"X", "Y", "Width", "Height"}; 
    private ArrayList<DShapeModel> models; 

    public TableModel() {
    	super();
        models = new ArrayList<DShapeModel>(); 
    }
    
    @Override
	public int getRowCount() {
		return models.size();
		
	}
    
    public int getRow(DShapeModel model) { 
        return models.indexOf(model); 
    } 
    
    public String getColumnName(int index) {
        return columns[index];
    }

	@Override
	public int getColumnCount() {
		return columns.length;
		
	}
 
    public void add(DShapeModel shapeModel) { 
        models.add(0, shapeModel); 
        fireTableDataChanged(); 
    }

    public void delete(DShapeModel shapeModel) { 
        models.remove(shapeModel); 
        fireTableDataChanged(); 
    } 
    
    public void clear() { 
        models.clear(); 
        fireTableDataChanged(); 
    } 
    
    public void toBack(DShapeModel shapeModel) { 
        if(!models.isEmpty()) {
        	models.remove(shapeModel);
        	models.add(shapeModel); 
        }
        fireTableDataChanged(); 
    } 
    
    public void toFront(DShapeModel shapeModel) { 
        if(!models.isEmpty()) {
        	models.remove(shapeModel);
        	models.add(0, shapeModel); 
        }
        fireTableDataChanged(); 
    } 
    

    public Object getValueAt(int row, int col) {
        Rectangle bounds = models.get(row).getBounds(); 
  
        if (col == 0) return bounds.x;
        if (col == 1) return bounds.y;
        if (col == 2) return bounds.width;
        if (col == 3) return bounds.height;
        else return null;
	}

	@Override
	public void modelChanged(DShapeModel shapeModel) {
        int index = models.indexOf(shapeModel); 
        fireTableRowsUpdated(index, index); 
	}

	
}