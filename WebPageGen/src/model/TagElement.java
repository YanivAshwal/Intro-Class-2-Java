package model;

import java.security.Identity;

import javax.lang.model.util.Elements;
import javax.swing.text.AbstractDocument.ElementEdit;

public class TagElement implements Element {
	private String tagName, attributes;
	private boolean endTag; 
	private Element content;
	private int id;
	
	private static int numElements = 1; 
	protected static boolean idInclusion; 
	
	public TagElement(String tagName, boolean endTag, Element content, 
			String attributes) {
		this.tagName = tagName; 
		this.endTag = endTag; 
		this.content = content;
		this.attributes = attributes;
		id = numElements++;
	}
	
	public int getId() { 
		return id; 
	}
	
	public String getStringId() {
		return tagName + id;
	}
	
	public String getStartTag() { 
		String startTag = "<" + tagName;
		
		if(idInclusion) {
			startTag += " id=\"" + getStringId() + "\"";
		}
		
		if (attributes !=null && !attributes.trim().isEmpty()) {
			startTag += " " + attributes;
		}
		
		startTag += ">";
		
		return startTag;
	}
	
	public String getEndTag() {
		if (endTag) {
			return "</" + tagName + ">";
		}
		return "";
	}
	
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	
	public static void resetIds() {
		numElements = 1;
	}
	
	public static void enableId(boolean choice) { 
		idInclusion = choice;
	}
	
	public String genHTML(int indentation) { 
		String html = Utilities.spaces(indentation);
		html += getStartTag();
		if (content != null ) {
			html += content.genHTML(indentation);
		}
		html += getEndTag();
		
		return html;
	}
	
}
