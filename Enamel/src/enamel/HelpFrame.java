package enamel;


import java.awt.BorderLayout;
	 
import javax.swing.JFrame;
import javax.swing.JTextArea;
	 
	public class HelpFrame extends JFrame{
	       
	        public JTextArea helpTextArea;
	     	       
	        public HelpFrame(){
	                setTitle("Help-Team 3's Authoring App");
	                helpTextArea = new JTextArea("This section provides information about each feature that deals with editing the Senario file.\n\n"
	                		+ "1) Add 'Normal text': Add text using this option.\n\n"
	                		+ "2) Add 'Sound file': Load a sound file(.wav) by selecting this option.\n\n"
	                		+ "3) Add 'Display char': Display a specific optionchar adding this option\n\n"
	                		+ "4) Add 'Create answer': Add a the answer by inputing it, by selecting this option\n\n"
	                		+ "5) Add 'Repeat': Repeat a specifc function by selecting this option\n\n"
	                		+ "6) Add 'Reset buttons': Reset all the buttons by selecting this option\n\n"
	                		+ "7) Add 'Clear all display': Clear everything currently being displayed back to its initial setting by selecting this option\n\n"
	                		+ "8) Button 'Edit': Turn on the editing capabilites by selecting this option\n\n"
	                		+ "9) Button 'Delete': Delete the current line in the senario file being displayed, by selecting this option\n\n"
	                		+ "10) Button 'Up': Move the text selector to the above line in the selected senario by selecting this option\n\n"
	                		+ "11) Button 'Down': Move the text selector to the line below in the selected senario by selecting this option\n\n"
	                		+ "More Options:\ta) Add 'Set Voice': Set the voice by selecting this option\n\n\t"
	                		+ "b) Add 'Repeat Button': Repeat a button by adding this option\n\n\t"
	                		+ "c) Add 'Display cell pins': Display cell pins by adding this option\n\n\t"
	                		+ "d) Add 'Raise one pin': Raise a specific pin by selecting this option\n\n\t"
	                		+ "e) Add 'Lower one pin': Lower a specific pin by selecting this option\n\n\t"
	                		+ "f) Add 'Clear one cell': Clear on cell, lowering all pins in it, by adding this option");
	                helpTextArea.setEditable(false);
	                helpTextArea.setLineWrap(true);
	                helpTextArea.setWrapStyleWord(true);
	                getContentPane().add(helpTextArea, BorderLayout.CENTER);
	               
	        }
	 
	}

