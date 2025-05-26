package model;

import java.util.ArrayList;

public class ParagraphElement extends TagElement{
	
	private ArrayList<Element> content;
	

	public ParagraphElement(String attributes) { 
		super("p", true, null, attributes);
		content = new ArrayList<Element>();
	}
	
	public void addItem(Element item) { 
		content.add(item);
	}
	
	public String genHTML(int indentation) {
	    StringBuilder html = new StringBuilder();
	    // Append the opening tag and then a newline
	    html.append(Utilities.spaces(indentation)).append(getStartTag()).append("\n");
	    
	    // Append each child (assuming each child's genHTML() does not already add a leading newline)
	    for (Element item : content) {
	        html.append(item.genHTML(indentation + 3)).append("\n");
	    }
	    
	    // Append the closing tag without adding an extra newline at the very end
	    html.append(Utilities.spaces(indentation)).append(getEndTag());
	    return html.toString();
	}
  
}
