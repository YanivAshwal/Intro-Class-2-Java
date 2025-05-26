package model;

public class TableElement extends TagElement{
	
	private int rows, cols;
	private Element[][] cells;
	
	public TableElement(int rows, int cols, String attributes) {
		super("table", true,new TextElement("(Text)"), (attributes == null ? "" : attributes));
		this.rows = rows;
		this.cols = cols;
		cells = new Element[rows][cols]; 
	}
	
	public void addItem(int rowIndex, int colIndex, Element item) { 
        if (rowIndex >= 0 && rowIndex < rows && colIndex >= 0 && colIndex < cols) {
            cells[rowIndex][colIndex] = item;
        }
	}
	public void setAttributes(String attributes) {
		super.setAttributes(attributes);
	}
	
	public double getTableUtilization() { 
		int totalCells = rows * cols;
		int filledCells = 0;
		
		for (int i = 0; i <  rows; i++) { // checks every cell 
			for (int j = 0; j < cols; j++) { 
				if (cells[i][j] != null) { //if not empty... 
					filledCells++; 
				}
			}
		}
		if (totalCells == 0) {
			return 0; 
		} 
		return (100.0 * filledCells / totalCells);
	}
	
	public String genHTML(int indentation) {
	    StringBuilder html = new StringBuilder();
	    html.append(Utilities.spaces(indentation)).append(getStartTag()).append("\n");
	    
	    for (int i = 0; i < rows; i++) {
	        html.append(Utilities.spaces(indentation + 3));
	        html.append("<tr>");
	        
	        for (int j = 0; j < cols; j++) {
	            html.append("<td>");
	            if (cells[i][j] != null) {
	                html.append(cells[i][j].genHTML(0));
	            }
	            html.append("</td>");
	        }
	        html.append("</tr>").append("\n");
	    }
	    
	    html.append(Utilities.spaces(indentation)).append(getEndTag());
	    
	    return html.toString();
	}
	
}