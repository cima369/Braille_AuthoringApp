package enamel;

import java.util.*;
public class SectionNode 
{
	//List of children
	private List <SectionNode> children;
	private String identifier;
	//The information stored in each section
	private List <String> sectionInfo;
	
	//Creates a new node
	public SectionNode(String identifier) 
	{
		children = new ArrayList <SectionNode> ();
		sectionInfo = new ArrayList <String> ();
		this.identifier = "/~" + identifier;
	}
	
	//Returns the list of all of the lines in the node
	public List <String> getInfo ()
	{
		return sectionInfo;
	}
	
	//Adds a child to the node
	public void addChild (SectionNode node)
	{
		children.add(node);
	}
	
	//Adds a line to the list of info that the node contains
	public void addInfo (String info)
	{
		sectionInfo.add(info);
	}
	
	//Returns identifier of node
	public String getIdentity ()
	{
		return identifier;
	}
	
	//Returns the child SectionNode at the particular index
	public SectionNode getChild (int childNum)
	{
		return children.get (childNum);
	}
	
	//Returns the total number of children
	public int getNumChildren ()
	{
		return children.size ();
	}
	
	//Displays the list of information in a friendly way
	public String display ()
	{
		StringBuilder sb = new StringBuilder ();
		for (String i : sectionInfo)
		{
			sb.append(i + " \n");
		}
		return (sb.toString ());
	}
}
