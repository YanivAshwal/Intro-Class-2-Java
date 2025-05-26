package model;
public class AnchorElement extends TagElement{

	private String linkText, url;
	
	public AnchorElement(String url, String linkText, String attributes){		
		super("a", true, null,  (attributes == null ? "" : attributes)); //temp attributes
		this.url = url;
		this.linkText = linkText;
		
		//build fullAttributes
        String fullAttributes = "href=\"" + url + "\" " + (attributes == null ? "" : attributes);
        
        setAttributes(fullAttributes); //setting attributes in constructor to full Attributes
	}
	
	public String getLinkText() { 
		return linkText;
	}
	
	public String getUrlText() {
		return url;
	}
	
	public String genHTML(int indentation) {
		String html = Utilities.spaces(indentation);
		html+= getStartTag();
		html += linkText;
		html += getEndTag();
		return html;
	}
	
}
