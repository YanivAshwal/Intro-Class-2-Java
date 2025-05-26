package model;

import javax.swing.table.TableStringConverter;

import org.junit.validator.PublicClassValidator;

public class ImageElement extends TagElement{
	
	private String imageURL;
	private int width, height;
	private String alt;

	public ImageElement(String imageURL, int width, int height, String alt, String attributes) { 
	    super("img", false, null, attributes); 
	    this.imageURL = imageURL;
	    this.width = width;
	    this.height = height;
	    this.alt = alt;

	}
	
	public String getImageURL() {
		return " src=\"" + imageURL + "\"";
	}
	
    public String getStartTag() {
        String tag = "<img";
        if (TagElement.idInclusion) {
        	tag += " id=\"img" + getId() + "\"";
        }
        tag += " src=\"" + imageURL + "\"";
        tag += " width=\"" + width + "\"";
        tag += " height=\"" + height + "\"";
        tag += " alt=\"" + alt + "\"";
        tag += ">";
        
        return tag;
    }
	 
    public String genHTML(int indentation) {
        String html = Utilities.spaces(indentation);
        html += getStartTag();
        return html;
    }
}

