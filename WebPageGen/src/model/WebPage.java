package model;

import java.awt.FontFormatException;
import java.lang.module.FindException;
import java.util.ArrayList;

import javax.management.InstanceAlreadyExistsException;
import javax.tools.JavaCompiler;

import org.hamcrest.core.IsInstanceOf;

public class WebPage implements Comparable<WebPage> {
	
	private String title;
	private ArrayList<Element> elements;

	public WebPage(String title) { 
		this.title = title;
		elements = new ArrayList<Element>();
	}

	public int addElement(Element element) {
		elements.add(element);
	    if (element instanceof TagElement) {
	        return ((TagElement) element).getId();
	    }
	    return -1;
	}
	
	public int compareTo(WebPage webPage) {
		return (this.title.compareTo(webPage.title));
	}
	
	public static void enableId(boolean choice) { 
		TagElement.enableId(choice);
	}
	
	public Element findElem(int id) {
		for (Element e: elements) { //parse through
			if(e instanceof TagElement) { //if its a Tag 
				if(((TagElement) e).getId() == id) { //if it matches
					return e; //return e Element
				}
			}
		}
		return null; 
	}
	
	public String getWebPageHTML(int indentation) {
		String result = "<!doctype html>\n";
		result += "<html>\n";
		result += Utilities.spaces(indentation) + "<head>\n";
		result += Utilities.spaces(indentation + 3) + "<meta charset=\"utf-8\"/>\n";
		result += Utilities.spaces(indentation + 3) + "<title>" + title + "</title>\n";
		result += Utilities.spaces(indentation) + "</head>\n";
		result += Utilities.spaces(indentation) + "<body>\n";
		for (Element temp : elements) {
			result += temp.genHTML(indentation) + "\n";
		}
		result += Utilities.spaces(indentation) + "</body>\n";
		result += "</html>";
		return result;
	}
	
	public void writeToFile(String filename, int indentation) {
		String html = getWebPageHTML(indentation);
		Utilities.writeToFile(filename, html);
	}
	
	public String stats() {
		 int numLists = 0; // how many lists
		 int numParags = 0; // how many tables
		 int numTables = 0; //how many tables
		 int tableNum = 1; //which table
		 double avgFilled = 0.0;
		 StringBuilder tableUtil = new StringBuilder(); //format for tables Utilization
		 												//regular string doesn't work here
		 for (Element e: elements) { 
			 if (e instanceof ListElement) {
				 numLists++;
			 } else if (e instanceof ParagraphElement) {
				 numParags++;
			 } else if (e instanceof TableElement) { 
				 numTables++; 
				 TableElement table = (TableElement) e; //cast to table to use its methods
				 double util  = table.getTableUtilization(); //percent of cells filled             
				 tableUtil.append(util);
				 avgFilled += util; 
			 }
		 }
		 
		 String stats = "List Count: " + numLists + "\n"; //building string for stats
		 stats += "Paragraph Count: " + numParags + "\n";
		 stats += "Table Count: " + numTables + "\n"; 
		 if (numTables > 0) { //only if there are tables do we add this info
			 double totalAvgFilled = avgFilled / numTables;
			 stats += "\nTableElement Utilization: " + totalAvgFilled;
		 }
		 return stats;
	}
	
	
}
