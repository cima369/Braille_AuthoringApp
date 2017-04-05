package enamel;

import java.util.*;
public class SectionNode 
{
	//List of children
	private List <SectionNode> children;
	private String identifier;
	//The information stored in each section
	private List <String> sectionInfo;
	
	public SectionNode(String identifier) 
	{
		children = new ArrayList <SectionNode> ();
		sectionInfo = new ArrayList <String> ();
		this.identifier = "/~" + identifier;
	}
	
	public List <String> getInfo ()
	{
		return sectionInfo;
	}
	public void addChild (SectionNode node)
	{
		children.add(node);
	}
	public void addInfo (String info)
	{
		sectionInfo.add(info);
	}
	
	public String getIdentity ()
	{
		return identifier;
	}
	
	public SectionNode getChild (int childNum)
	{
		return children.get (childNum);
	}
	
	public int getNumChildren ()
	{
		return children.size ();
	}
	
	public String display ()
	{
		StringBuilder sb = new StringBuilder ();

		//This for loop is used to display the scenario file information in a more user readable way
		//The different branches of the if statement are for the different commands of the scenario file
		for (String i : sectionInfo)
		{
			if (i.equals(identifier))
			{
				sb.append ("Identity is: " + i.substring(2) + "\n");
			}
			else if (i.length () >= 4 && i.substring(0, 4).equals("Cell") ||i.length () >= 6 && i.substring(0, 4).equals("Button"))
			{
				sb.append(i + "\n");
			}
			else if (i.length () >= 8 && i.substring(0, 8).equals("/~sound:"))
			{
				sb.append("Sound file to play: " + i.substring(8)+ "\n");
			}
			else if (i.length () >= 8 && i.substring(0, 8).equals("/~pause:"))
			{
				sb.append("Pausing for: " + i.substring(8) + " seconds.\n");
			}
			else if (i.length () >= 12 && i.substring(0, 12).equals("/~set-voice:"))
			{
				sb.append("Changing voice to option: " + i.substring(12) + "\n");
			}
			else if (i.length () >= 16 && i.substring(0, 16).equals("/~repeat-button:"))
			{
				sb.append("This button plays repeated text: " + i.substring(16) + "\n");
			}
			else if (i.length () >= 7 && i.substring(0, 7).equals("/~skip:"))
			{
				sb.append("Skip to this identifier: " + i.substring(7) + "\n");
			}
			else if (i.length () >= 8 && i.substring(0, 8).equals("/~repeat"))
			{
				sb.append("Start repeating text: \n");
			}
			else if (i.length () >= 11 && i.substring(0, 11).equals("/~endrepeat"))
			{
				sb.append("Repeating text has stopped. \n");
			}
			else if (i.length () >= 15 && i.substring(0, 15).equals("/~reset-buttons"))
			{
				sb.append("Resetting button actions. \n");
			}
			else if (i.length () >= 14 && i.substring(0, 14).equals("/~skip-button:"))
			{
				sb.append("This button number goes to this identifier: " + i.substring(14) 
				+ "\n");
				
			}
			else if (i.length () >= 15 && i.substring(0, 15).equals("/~disp-clearAll"))
			{
				sb.append("Clearing all Braille cells. \n");
			}
			else if (i.length () >= 14 && i.substring(0, 14).equals("/~disp-string:"))
			{
				sb.append("Displaying this string in Braille: " + i.substring(14) + "\n");
			}
			else if (i.length () >= 17 && i.substring(0, 17).equals("/~disp-cell-char:"))
			{
				sb.append("At this cell number, display this character as Braille: " + i.substring(17) + "\n");
			}
			else if (i.length () >= 18 && i.substring(0, 18).equals("/~disp-cell-raise:"))
			{
				sb.append("At this cell number, raise this pin: " + i.substring(18) + "\n");
			}
			else if (i.length () >= 18 && i.substring(0, 18).equals("/~disp-cell-lower:"))
			{
				sb.append("At this cell number, lower this pin: " + i.substring(18) + "\n");

			}
			else if (i.length () >= 18 && i.substring(0, 18).equals("/~disp-cell-clear:"))
			{
				sb.append("At this cell number, clear the display: " + i.substring(18) + "\n");

			}
			else if (i.length () >= 12 && i.substring(0, 12).equals("/~user-input"))
			{
				sb.append("Getting user-input. \n");
			}
			else if (i.length () >= 17 && i.substring(0, 17).equals("/~disp-cell-pins:"))
			{
				sb.append("At this cell number, display this Braille representation: " + i.substring(18) + "\n");
			}
			else
			{
				sb.append(i + "\n");
			} 
		}
		return (sb.toString ());
	}
}
