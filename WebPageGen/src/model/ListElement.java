package model;

import java.util.ArrayList;

import org.junit.validator.PublicClassValidator;

public class ListElement extends TagElement{
	

	ArrayList<Element> content;
	
	public ListElement(boolean ordered, String attributes) { 
		super(ordered ? "ol" : "ul", true, null, attributes); //ol = ordered; ul = unordered
		content = new ArrayList<Element>();
	}
	
	public void addItem(Element item) { 
		content.add(item); 
	}
	
	public String genHTML(int indentation) { 
	    StringBuilder html = new StringBuilder();
	    html.append(Utilities.spaces(indentation)).append(getStartTag()).append("\n");
	    
	    for (Element e : content) {
	        html.append(Utilities.spaces(indentation + 3)).append("<li>\n");
	        html.append("\n").append(e.genHTML(indentation + 6)).append("\n");
	        html.append(Utilities.spaces(indentation + 3)).append("</li>\n");
	    }
	    
	    html.append("\n").append(Utilities.spaces(indentation)).append(getEndTag());
	    return html.toString();
	}
}
