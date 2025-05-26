package model;


public class HeadingElement extends TagElement{
	
	public HeadingElement(Element content, int level, String attributes) { 
		super ("h"+ ((1 <=level && level <= 6) ? level : 1), true, content, attributes); 
					//if its between 1 and 6, use level, otherwise use 1
	}
}
