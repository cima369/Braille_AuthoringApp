package enamel;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import java.util.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Paths;

public class AuthoringDisplay 
{

	// Used to store the different sections
	protected ArrayList <String> identifiers; 
	protected ArrayList <SectionNode> nodes;
	protected JTextArea field;
	protected JFrame frame = new JFrame ();
	protected SectionNode currNode;
	protected SectionNode root;
	protected ArrayDeque <SectionNode> q = new ArrayDeque <SectionNode> ();
	protected int numLines = 0;
	protected int highlightLocation = 0;
	protected Highlighter highlighter;
	protected String scenarioFilePath;
	protected int cellNum;
	protected int buttonNum;
	protected int update = 0;
	protected boolean question;
    protected int numChange = 0;
    protected int numberQ = 1;
    
	public AuthoringDisplay(JFrame fr) 
	{
		field = new JTextArea (21, 32);
		frame = fr;
		SpringLayout layout = new SpringLayout ();
		nodes = new ArrayList <SectionNode> ();
		question = false;
		//When initialized, creates a blank scenario file
		newFile ();


		field.setText(root.display());

		//Creates a scrollable JPanel to read in the information of the node
		JScrollPane scroll = new JScrollPane (field);
		JPanel textPane = new JPanel ();
		String[] additional = {"Add 'Set voice'", "Add 'Repeat button'", "Add 'Display cell pins'", 
				"Add 'Raise one pin'", "Add 'Lower one pin'", "Add 'Clear one cell'"};
		highlightLocation = 0;
		highlighter = field.getHighlighter();
		highlight ();

		field.setFont(new Font ("Serif", Font.PLAIN, 16));
		field.setLineWrap (true);
		field.setWrapStyleWord (true);
		field.setEditable(false);
		textPane.add(scroll);


		frame.setSize (603, 560);

		//Adds all the buttons that the user can press to add lines
		JButton addLines = new JButton ("Add 'Normal text'");
		JButton addSound = new JButton ("Add 'Sound file'");
		JButton dispChar = new JButton ("Add 'Display char'");
		JButton answerButton = new JButton ("Add 'Create answer'");
		JButton repeatTextButton = new JButton ("Add 'Repeat'");
		JButton resetButton = new JButton ("Add 'Reset buttons'");
		JButton dispClearAll = new JButton ("Add 'Clear all display'");
		JButton editButton = new JButton ("Edit");
		JButton deleteButton = new JButton ("Delete");
		JButton upButton = new JButton ("Up");
		JButton downButton = new JButton ("Down");

		//Additional options for the uses to add
		JLabel options = new JLabel ("More options to add: ");
		JComboBox<String> cb = new JComboBox<String>(additional);
		JButton addOptions = new JButton ("Add");

		//Sets the actions of all the buttons to perform the corresponding action
		//This button allows the user to enter normal text to the scenario file
		addLines.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed (ActionEvent arg0)
			{
				add ("", addLines.getText());
			}				
		});
		//This button allows the user to add a sound file to the scenario file
		addSound.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed (ActionEvent arg0)
			{
				add ("/~sound:", "Add 'Sound file'");
			}				
		});
		//This button allows the user to add a display-character in the scenario file
		dispChar.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed (ActionEvent arg0)
			{
				add ("/~disp-cell-char:", "Add 'Display char'");
			}				
		});

		answerButton.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed (ActionEvent arg0)
			{
				add ("/~skip-button:", "Add 'Getting answer'");
			}				
		});


		//This button allows the user to add repeated text
		repeatTextButton.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed (ActionEvent arg0)
			{
				add ("/~repeat", "Add 'Repeat'");
			}				
		});

		//This button allows the user to reset the functionality of the buttons of the simulator
		resetButton.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed (ActionEvent arg0)
			{
				addNoInput ("/~reset-buttons", "Add 'Reset buttons'");
			}				
		});

		//This button clears the display of all of the simulator braille cells
		dispClearAll.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed (ActionEvent arg0)
			{
				addNoInput ("/~disp-clearAll", "Add 'Clear all display'");
			}				
		});

		//Add options is for the JComboBox object which has the additional options that
		//the user can use to add more features to the scenario file.
		addOptions.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed (ActionEvent arg0)
			{
				//This option allows the user to change the voice of the 
				if (cb.getSelectedItem ().equals (additional [0]))
				{
					add ("/~set-voice:", additional [0]);
				}
				//This option allows the user to set a button of the simulator to repeat the text
				else if (cb.getSelectedItem().equals (additional [1]))
				{
					add ("/~repeat-button:", additional [1]);
				}
				//This option allows the user to display cell pins based on a string
				else if (cb.getSelectedItem().equals(additional [2]))
				{
					add ("/disp-cell-pins:", additional [2]);
				}
				//This option allows the user to raise a cell pin
				else if (cb.getSelectedItem().equals(additional [3]))
				{
					add ("/disp-cell-raise:", additional [3]);
				}
				//This option allows the user to lower a cell pin
				else if (cb.getSelectedItem().equals(additional [4]))
				{
					add ("/disp-cell-lower:", additional [4]);
				}
				//This option allows the user to clear the display of a braille cell
				else if (cb.getSelectedItem().equals(additional [5]))
				{
					add ("/disp-cell-clear:", additional [5]);
				}

			}				
		}); 
		//This button shifts the highligther up 
		downButton.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed (ActionEvent arg0)
			{

				if (highlightLocation < numLines - 2)
				{
					highlightLocation ++;
				}
				highlight ();
			}
		});
		//This button shifts the highlight down
		upButton.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed (ActionEvent arg0)
			{
				if (highlightLocation > 0)
				{
					highlightLocation --;
				}	
				highlight ();
			}				
		});
		//This button deletes a line in the scenario file
		deleteButton.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed (ActionEvent arg0)
			{
				remove ();
			}				
		});
		//This button allows the edit of the cell and button options
		editButton.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed (ActionEvent arg0)
			{
				try
				{
					int start = field.getLineStartOffset(highlightLocation);
					int end = field.getLineEndOffset(highlightLocation);
					if (end - start >= 6)
					{
						if (nodes.get(update).getInfo().get(highlightLocation).substring(0, 4).equals("Cell"))
						{
							add ("Cell ", "Cell");
						}
						else if (nodes.get(update).getInfo().get(highlightLocation).substring(0, 6).equals("Button"))
						{
							add ("Button ", "Button");

						}
					}
				}
				catch (BadLocationException e)
				{

				}


			}				
		});

		//Adding the layout and necessary buttons to the frame
		frame.getContentPane().setLayout(layout);
		frame.getContentPane().add (textPane);
		frame.getContentPane().add (addLines);
		frame.getContentPane().add (addSound);
		frame.getContentPane().add (dispChar);
		frame.getContentPane().add (answerButton);
		frame.getContentPane().add (repeatTextButton);
		frame.getContentPane().add (resetButton);
		frame.getContentPane().add (dispClearAll);
		frame.getContentPane().add (options);
		frame.getContentPane().add (cb);
		frame.getContentPane().add (addOptions);
		frame.getContentPane().add (editButton);
		frame.getContentPane().add (deleteButton);
		frame.getContentPane().add (upButton);
		frame.getContentPane().add (downButton);

		//Defines the layout of the buttons
		layout.putConstraint(SpringLayout.WEST, textPane, 221, SpringLayout.WEST, frame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, textPane, 20, SpringLayout.NORTH, frame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, addLines, 40, SpringLayout.WEST, frame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, addLines, 20, SpringLayout.NORTH, frame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, addSound, 40, SpringLayout.WEST, frame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, addSound, 60, SpringLayout.NORTH, frame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, dispChar, 40, SpringLayout.WEST, frame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, dispChar, 100, SpringLayout.NORTH, frame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, answerButton, 40, SpringLayout.WEST, frame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, answerButton, 140, SpringLayout.NORTH, frame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, repeatTextButton, 40, SpringLayout.WEST, frame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, repeatTextButton, 180, SpringLayout.NORTH, frame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, resetButton, 40, SpringLayout.WEST, frame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, resetButton, 220, SpringLayout.NORTH, frame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, dispClearAll, 40, SpringLayout.WEST, frame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, dispClearAll, 260, SpringLayout.NORTH, frame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, options, 5, SpringLayout.WEST, frame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, options, 300, SpringLayout.NORTH, frame.getContentPane());	
		layout.putConstraint(SpringLayout.WEST, cb, 5, SpringLayout.WEST, frame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, cb, 320, SpringLayout.NORTH, frame.getContentPane());	
		layout.putConstraint(SpringLayout.WEST, addOptions, 160, SpringLayout.WEST, frame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, addOptions, 320, SpringLayout.NORTH, frame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, editButton, 40, SpringLayout.WEST, frame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, editButton, 360, SpringLayout.NORTH, frame.getContentPane());	
		layout.putConstraint(SpringLayout.WEST, deleteButton, 40, SpringLayout.WEST, frame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, deleteButton, 400, SpringLayout.NORTH, frame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, upButton, 40, SpringLayout.WEST, frame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, upButton, 440, SpringLayout.NORTH, frame.getContentPane());
		layout.putConstraint(SpringLayout.WEST, downButton, 40, SpringLayout.WEST, frame.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, downButton, 480, SpringLayout.SOUTH, frame.getContentPane());	

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}


	//Used to check if the arguments to change the button and cell numbers are valid
	protected boolean checkEdit (String argument)
	{
		boolean works = false;
		try
		{
			works = true;
			int args = Integer.parseInt(argument);
			// Checks to see if the argument is a positive integer
			if (args <= 0 )
			{
				JOptionPane.showMessageDialog(new JFrame (), "Sorry, that argument is incorrect. Expected argument "
						+ "to be a positive integer.") ;
				works = false;
			}
		}
		// Throws an error if you try to put in an argument that is not a number
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(new JFrame (), "Sorry, that argument is incorrect. Expected argument "
					+ "to be a positive integer.") ;
			works = false;
		}
		return works;
	}

	//Creates a new scenario file
	protected void newFile ()
	{
		nodes.clear ();
		String identifier = "RootNode";
		root = new SectionNode (identifier);
		q = new ArrayDeque <SectionNode> ();
		question = false;
		numLines = 0;
		highlightLocation = 0;
		update = 0;
		highlighter = field.getHighlighter();
		cellNum = 1;
		buttonNum = 1;
		scenarioFilePath = Paths.get("").toAbsolutePath().toString() + File.separator +"SampleScenarios";
		root.addInfo("Cell " + 1);
		root.addInfo("Button " + 1);
		nodes.add(root);
		currNode = root;
		field.setText(nodes.get(update).display());
		highlight();
	}
	protected void open (File fileName)
	{
		try
		{
			File f = fileName;
			Scanner fileScanner = new Scanner (f);
			String absolutePath = f.getAbsolutePath();
			scenarioFilePath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));

			cellNum = Integer.parseInt(fileScanner.nextLine().split("\\s")[1]);
			buttonNum = Integer.parseInt(fileScanner.nextLine().split("\\s")[1]);
			identifiers = new ArrayList <String> ();
			nodes = new ArrayList <SectionNode> ();

			String identifier = "RootNode";
			root = new SectionNode (identifier);
			identifiers.add(identifier);
			nodes.add(root);
			currNode = nodes.get(identifiers.lastIndexOf(identifier));

			root.addInfo("Cell " + cellNum);
			root.addInfo("Button " + buttonNum);
			String lines, oldidentifier, rootidentifier = identifier;

			//Goes through the whole scenario file, and create a tree that has nodes based on the scenario file
			while (fileScanner.hasNextLine ())
			{
				oldidentifier = identifier;
				lines = fileScanner.nextLine();
				if (lines.trim().length () > 0)
				{
					//Creates a new node based on a new identifier
					for (String i : identifiers)
					{
						if (lines.equals("/~" + i))
						{
							identifier = i;
							currNode = nodes.get(identifiers.lastIndexOf(identifier));
							break;
						}
					}

					//Creates a new node if the line skip-button was found
					if (lines.length() >= 14 && lines.substring(0, 14).equals("/~skip-button:"))
					{
						identifier = lines.substring (16);
						identifiers.add(identifier);
						nodes.add (new SectionNode (identifier));

						currNode.addChild(nodes.get(identifiers.lastIndexOf(identifier)));
					}
					//Adds the line being read in, to the current node
					currNode.addInfo (lines);
					//Ends the node and moves on to the next node
					if (lines.equals("/~user-input"))
					{
						nodes.remove(identifiers.lastIndexOf(rootidentifier));
						identifiers.remove(rootidentifier);		
					}
					else
					{
						//Creates a new node if the command skip was found
						if (lines.length () >= 7 && lines.substring(0, 7).equals("/~skip:"))
						{
							oldidentifier = identifier;
							identifier = lines.substring(7);
							rootidentifier = identifier;
							//Checks to see if the node has not been created already, and if it has not
							//then to create it
							if (!identifiers.contains(identifier))
							{
								identifiers.add(identifier);
								nodes.add (new SectionNode (identifier));
							}

							//Adds the child the to the current node, then advances the currNode to the 
							//next one
							currNode.addChild(nodes.get(identifiers.lastIndexOf(identifier)));

							nodes.remove(identifiers.lastIndexOf(oldidentifier));
							identifiers.remove(oldidentifier);

						}
					}
				}
			}
			//nodes.remove(0);
			nodes.clear();
			fileScanner.close ();

			currNode = root;
			nodes.add(root);
			field.setText(currNode.display());
			highlightLocation = 0;

			highlighter = field.getHighlighter();
			highlight ();

		}
		catch (Exception e)
		{

		}
	}
	//Save function
	protected void save (String filePath)
	{
		try
		{
			// Writes to the saved file
			PrintWriter writer = new PrintWriter(filePath);
			q.add(root);
			SectionNode n;
			//Breadth first searching to save a well file formatted tree
			while (!q.isEmpty())
			{
				n = q.pop ();
				for (String i : n.getInfo())
				{
					writer.println(i);
				}
				writer.println("");
				for (int a = 0; a < n.getNumChildren (); a ++)
				{
					if (!q.contains(n.getChild(a)))
					{
						q.add(n.getChild(a));
					}
				}
			}
			writer.close();
			scenarioFilePath = filePath.substring(0, filePath.lastIndexOf(File.separator));
		} 
		catch (IOException e) 
		{
			// do something
			JOptionPane.showMessageDialog(new JFrame (), "Error in saving file.");
		}
	}

	// Used to update the JTextArea view to the correct node
	protected void updater (String toUpdate)
	{
		try
		{
			update = Integer.parseInt(toUpdate);
		}
		catch (Exception e)
		{

		}
	}

	// Removes a line from the node
	protected void remove ()
	{
		//Checks to see if there are any lines to remove
		if (nodes.get(update).getInfo().size () <= 0)
		{
			JOptionPane.showMessageDialog(new JFrame (), "No lines to remove.");
		}
		else
		{
			//Removes the current line at the highlight location and moves the highlight location
			nodes.get(update).getInfo().remove(highlightLocation);
			field.setText(nodes.get(update).display());
			highlightLocation --;
			if (highlightLocation == - 1)
			{
				highlightLocation = 0;
			}
			highlight ();
		}
	}

	//Reset the highlight location to the first line
	protected void highlightReset ()
	{
		highlightLocation = 0;
		numLines = field.getLineCount();
	}

	//Creates a map to traverse through the different nodes
	protected void drawMap ()
	{

		JFrame frame = new JFrame("Map");
		JPanel panel = new JPanel();
		nodes.clear ();
		int incrementer = 0;
		boolean first = true;
		BoxLayout boxY = new BoxLayout (panel, BoxLayout.Y_AXIS);
		ArrayList <JButton> mapButtons = new ArrayList <JButton> ();
		panel.setLayout(boxY);
		q.add(root);
		SectionNode n;
		mapButtons.add(new JButton ("Introduction - Question " +  numberQ));
		panel.add(mapButtons.get(incrementer));
		incrementer ++;
		while (!q.isEmpty())
		{
			first = true;
			n = q.pop ();
			nodes.add(n);
			int answerQ = 1;
			for (int a = 0; a < n.getNumChildren (); a ++)
			{
				if (!q.contains(n.getChild(a)))
				{

					if (first)
					{
						panel.add(Box.createRigidArea (new Dimension (0, 35)));
						first = false;
						question = !question;

					}
					
					if (question)
					{
						mapButtons.add(new JButton ("Answer: " + numberQ + " - " + answerQ));
					}
					else
					{
						numberQ ++;
						mapButtons.add(new JButton ("Question: " + numberQ));
						

					}
					panel.add(mapButtons.get(incrementer));
					incrementer++;
					q.add(n.getChild(a));
					answerQ ++;

				}
			}
		}  
		//Display the name of the JButtons on the map
		for (int i = 0; i < nodes.size(); i ++)
		{
			String text = nodes.get(i).display();
			String updateNum = i +"";
			//By clicking on the map buttons, it displays the information of the node on the text area
			mapButtons.get(i).addActionListener(new ActionListener () {

				@Override
				public void actionPerformed (ActionEvent arg0)
				{
					field.setText(text);
					updater (updateNum);
					highlightReset();
					highlight ();
					frame.dispose();
				}
			});
		} 

		//Creates a scroll bar if the map is too big for the current size of the JFrame
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		frame.getContentPane().add(scrollPane);
		frame.setSize (600, 350);
		frame.setLocation (100, 150);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);


	}

	//Used to check if the input for the method of the display cell clear is correct
	protected boolean checkDispCellClear (String argument)
	{
		boolean works = false;
		try
		{
			works = true;
			int args = Integer.parseInt(argument);
			// Checks to see if the argument is a valid cell number
			if (args < 0 || args > cellNum - 1)
			{
				JOptionPane.showMessageDialog(new JFrame (), "Sorry, the arguments are incorrect. Expected cell number to be"
						+ "the range of 0 .. " + (cellNum - 1));
				works = false;
			}
		}
		// Throws an error if you try to put in an argument that is not a number
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(new JFrame (), "Sorry, the arguments are incorrect. Expected cell number to be"
					+ "the range of 0 .. " + (cellNum - 1));
			works = false;
		}
		return works;
	}

	//Used to check if the input for the method of raise lower is correct
	protected boolean checkRaiseLower (String argument)
	{
		boolean works = false;
		try
		{
			works = true;
			String [] args = argument.split("\\s");
			int argsIndex = Integer.parseInt(args[0]);
			int pinIndex = Integer.parseInt(args[1]);
			// Checks to see if the arguments are valid cell and pin numbers
			if (argsIndex > cellNum - 1 || argsIndex < 0 || pinIndex < 1 || pinIndex > 8)
			{
				JOptionPane.showMessageDialog(new JFrame (), "Sorry, the arguments are incorrect. Expected cell number to be"
						+ "the range of 0 .. " + (cellNum - 1) + "\n Or the pin number to be the range of 1 .. 8");
				works = false;
			}
		}
		// Throws an error if you are trying to put in an argument that is not a number
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(new JFrame (), "Sorry, the arguments are incorrect. Expected cell number to be"
					+ "the range of 0 .. " + (cellNum - 1) + "\n Or the pin number to be the range of 1 .. 8");
			works = false;
		}
		return works;
	}

	//Used to check if the input for the method display cell pins is correct
	protected boolean checkDispCellPins (String argument)
	{
		boolean works = false;
		try
		{
			works = true;
			String [] args = argument.split("\\s");
			int argsIndex = Integer.parseInt(args[0]);
			// Checks if the first argument is a valid cell number
			if (argsIndex > cellNum - 1 || argsIndex < 0 || args.length > 2 || args[1].length() != 8)
			{
				JOptionPane.showMessageDialog(new JFrame (), "Sorry, the arguments are incorrect. Expected cell number to be"
						+ "the range of 0 .. " + (cellNum - 1) + "\n Or the string to have 8 characters of 1s and 0s.");
				works = false;
			}
			// Checks if the second argument is a valid 8 character string of 0 and 1
			for (int x = 0; x < args [1].length(); x ++)
			{
				if (args[1].charAt(x) != '0' && args[1].charAt(x) != '1')
				{
					JOptionPane.showMessageDialog(new JFrame (), "Sorry, the arguments are incorrect. Expected cell number to be"
							+ "the range of 0 .. " + (cellNum - 1) + "\n Or the string to have 8 characters of 1s and 0s.");
					works = false;
					return works;
				}
			}
		}
		// Throws an error if the first argument is not a number
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(new JFrame (), "Sorry, the arguments are incorrect. Expected cell number to be"
					+ "the range of 0 .. " + (cellNum - 1) + "\n Or the string to have 8 characters of 1s and 0s.");
			works = false;
		}
		return works;
	}

	//Used to check if the input for the method repeat-button is correct
	protected boolean checkRepeatButton (String argument)
	{
		boolean works = false;
		try
		{
			works = true;
			int argsIndex = Integer.parseInt(argument);
			//Checks to see that the argument is a valid button number
			if (argsIndex > buttonNum - 1 || argsIndex < 0)
			{
				JOptionPane.showMessageDialog(new JFrame (), "Sorry, the arguments are incorrect. Expected button number to be"
						+ "the range of 0 .. " + (buttonNum - 1) + "\n Or the argument to have two values.");
				works = false;
			}
		}
		//Throws an error pane if the argument is not a number
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(new JFrame (), "Sorry, the arguments are incorrect. Expected button number to be"
					+ "the range of 0 .. " + (buttonNum - 1) + "\n Or the argument to have two values.");
			works = false;
		}
		return works;	
	}

	//Used to check if the input for the method skip-button is correct
	protected boolean checkAnswerButton (String argument)
	{
		boolean works = false;
		try
		{
			works = true;
			int argsIndex = Integer.parseInt(argument);
			//Checks if the first argument is a valid button number
			if (argsIndex > buttonNum - 1 || argsIndex < 0)
			{
				JOptionPane.showMessageDialog(new JFrame (), "Sorry, the arguments are incorrect. Expected button number to be"
						+ "the range of 0 .. " + (buttonNum - 1) + ".");
				works = false;
			}
		}
		//Throws an error pane if the first argument is not a number
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(new JFrame (), "Sorry, the arguments are incorrect. Expected button number to be"
					+ "the range of 0 .. " + (buttonNum - 1) + "\n Or the argument to have two values.");
			works = false;
		}
		return works;
	}

	//Used to check if the input of the display-char method is correct
	protected  boolean checkDispChar (String argument)
	{
		boolean works = false;
		try
		{
			works = true;
			String [] args = argument.split("\\s");
			int argsIndex = Integer.parseInt(args[0]);
			char dispChar = args[1].charAt(0);
			//Checks if the first argument is a valid cell number and the second argument is a valid English character
			if (argsIndex > cellNum - 1 || argsIndex < 0 || args[1].length() > 1 || ((dispChar < 65 || dispChar > 90) && (dispChar < 97 || dispChar > 122)))
			{
				JOptionPane.showMessageDialog(new JFrame (), "Sorry, the arguments are incorrect. Expected cell number to be"
						+ "the range of 0 .. " + (cellNum - 1) + "\n Or the character to be any letter of the English"
						+ "alphabet, either lower or upper case.");
				works = false;
			}
		}
		//Throws an error pane if the first argument is not a number
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(new JFrame (), "Sorry, the arguments are incorrect. Expected cell number to be"
					+ "the range of 0 .. " + (cellNum - 1) + "\n Or the character to be any letter of the English"
					+ "alphabet, either lower or upper case.");
			works = false;
		}
		return works;
	}

	//Used to check if the input of the set-voice method is correct
	protected boolean checkVoice (String argument)
	{
		boolean works = false;
		try
		{
			works = true;
			int args = Integer.parseInt(argument);
			//Checks if the argument is a valid number from 1 .. 4
			if (args < 1 || args > 4)
			{
				JOptionPane.showMessageDialog(new JFrame (), "Sorry, the arguments are incorrect. Expected a number from 1 .. 4 "
						+ "to change the voice of the text to speech.");
				works = false;
			}
		}
		//Throws an error pane of the argument is not a number
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(new JFrame (), "Sorry, the arguments are incorrect. Expected a number from 1 .. 4 "
					+ "to change the voice of the text to speech.");
			works = false;
		}
		return works;
	}

	//Used to check if the input of the sound method is correct
	protected boolean checkSound (String argument)
	{
		boolean works = false;
		try
		{
			works = true;
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File(scenarioFilePath + File.separator + "AudioFiles" + File.separator + argument)));
			clip.start();
			clip.close();  
		}
		//Throws an error pane if the argument is not a valid sound file
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(new JFrame (), "Sorry, that sound file does not exist or is of incorrect format.");
			works = false;
		}
		return works;
	}


	//Method to highlight the text on the JTextArea
	protected  void highlight ()
	{
		//Gets the line to highlight in the JTextArea
		try 
		{
			highlighter.removeAllHighlights();
			int start = field.getLineStartOffset(highlightLocation);
			int end = field.getLineEndOffset(highlightLocation);
			numLines = field.getLineCount();
			highlighter.addHighlight(start, end, new DefaultHighlighter.DefaultHighlightPainter(Color.pink));
		} 
		catch (BadLocationException e) 
		{
		}
	}

	//Method to change the view of the JTextArea
	public  void change (int index)
	{

		field.setText(nodes.get(index).display());

	}

	//Method to add a child node to the current node
	protected  void addChild (String identifier)
	{
		boolean createNew = true;
		boolean moreThanOne = false;
		//For loop used to check if the child already exists, or to add an existing node as a child
		for (int i = update; i < nodes.size(); i ++)
		{
			if (nodes.get(i).getIdentity().equals(identifier.split("\\s").length > 1))
			{
				moreThanOne = true;

			}
			if (moreThanOne)
			{
				if (nodes.get(i).getIdentity().equals("/~" + identifier.split("\\s")[1]))
				{
					nodes.get(update).addChild(nodes.get(i));
					createNew = false;
					break;
				}
			}
			else
			{
				if (nodes.get(i).getIdentity().equals("/~" + identifier))
				{
					nodes.get(update).addChild(nodes.get(i));
					createNew = false;
					break;
				}
			}
		}
		//If statement used to check if the child exists
		if (createNew)
		{
			nodes.get(update).addChild(new SectionNode (identifier));
			if (moreThanOne)
			{
				nodes.get(update).getChild(nodes.get(update).getNumChildren() - 1).addInfo("/~" + identifier.split("\\s")[1]);
			}
			else
			{
				nodes.get(update).getChild(nodes.get(update).getNumChildren() - 1).addInfo("/~" + identifier);

			}
		}

	}


	//Used to add methods (i.e user-input, reset-buttons) that require no input
	protected void addNoInput (String identifier, String title)
	{
		//Create a new JFrame for the user to accept the change or not
		JFrame frame = new JFrame(title);
		SpringLayout layout = new SpringLayout ();

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(300, 200);
		frame.setLocation(250, 150);
		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(layout);

		String [] choices = {"Add before highlighted text", "Add after highlighted text"};

		//Creates a drop down menu list so the user can choose to add it before or after the highlighted text
		JComboBox<String> cb = new JComboBox<String>(choices);

		JButton okay = new JButton ("OK");
		JButton cancel = new JButton ("Cancel");

		cb.setVisible(true);

		panel.add(cb);
		panel.add(okay);

		panel.add(cancel);

		//Adds in the line to the node if the user presses okay
		okay.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed (ActionEvent arg0)
			{
				//Adds the line before or after the highlighted line
				if (((String)(cb.getSelectedItem())).equals(choices[0]))
				{
					nodes.get(update).getInfo().add (highlightLocation, identifier);
				}
				else
				{
					nodes.get(update).getInfo().add (highlightLocation + 1, identifier);

				}
				field.setText(nodes.get(update).display());
				highlight();
				frame.dispose();
			}				
		});
		//The user closes the window if they press the cancel button 
		cancel.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed (ActionEvent arg0)
			{
				frame.dispose();
			}				
		});

		layout.putConstraint(SpringLayout.WEST, cb, 10, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, cb, 13, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, okay, 20, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.SOUTH, okay, -20, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.WEST, cancel, 130, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.SOUTH, cancel, -20, SpringLayout.SOUTH, panel);
	}

	//Used to add methods that require additional arguments (i.e set-voice, sound)
	public void add (String identifier, String title)
	{
		//Creates a new JFrame for the user to enter information and to accept changes
		JFrame frame = new JFrame(title);
		SpringLayout layout = new SpringLayout ();

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(450, 350);
		frame.setLocation(430, 100);
		JPanel panel = new JPanel();
		frame.add(panel);
		panel.setLayout(layout);

		String [] choices = {"Add before highlighted text: ", "Add after highlighted text: "};

		//Creates a drop down menu list so the user can choose to add it before or after the highlighted text
		JComboBox<String> cb = new JComboBox<String>(choices);

		//Creates the JTextArea to get arguments from the user
		JTextArea area = new JTextArea (11, 17);
		JScrollPane scroll = new JScrollPane (area);
		JPanel textPane = new JPanel ();
		JButton okay = new JButton ("OK");
		JButton cancel = new JButton ("Cancel");
		JLabel lab = new JLabel ("Enter your lines in the box below:");
		area.setFont(new Font ("Serif", Font.PLAIN, 16));
		area.setLineWrap (true);

		area.setWrapStyleWord (true);
		textPane.add(scroll);		    
		panel.add(textPane);

		cb.setVisible(true);

		panel.add(cb);
		panel.add(okay);

		panel.add(lab);

		panel.add(cancel);

		//Adds in the line if the user presses okay
		okay.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed (ActionEvent arg0)
			{
				//Checks if the user wants to add the method sound and if arguments are valid
				if (title.equals("Add 'Sound file'"))
				{
					if (checkSound (area.getText()) == false)
					{
						return;
					}
				}
				//Checks if the user wants to add the method display char and if arguments are valid
				else if (title.equals("Add 'Display char'"))
				{
					if (checkDispChar (area.getText()) == false)
					{
						return;
					}
				}
				//Checks if the user wants to add a get answer part to the scetion of the scenario file
				else if (title.equals("Add 'Getting answer'"))
				{
					if (checkAnswerButton (area.getText ()) == false)
					{
						return;
					}
					else
					{
						//Creates a new node which represents the answer, and that answer node links to a new question node
						if (currNode.getNumChildren() == 0)
						{
							currNode.addInfo("/~skip-button:" + area.getText() + " ANSW" + numChange);
							currNode.addInfo("/~user-input");
							currNode.addChild(new SectionNode ("ANSW" + numChange));
							numChange ++;
							currNode.getChild(currNode.getNumChildren() - 1).addInfo(currNode.getChild(currNode.getNumChildren() - 1).getIdentity());
							currNode.getChild(currNode.getNumChildren() - 1).addInfo("/~skip:QUES" + numChange);
							currNode.getChild(currNode.getNumChildren()-1).addChild(new SectionNode("QUES" + numChange));	
							currNode.getChild(currNode.getNumChildren()-1).getChild(0).addInfo(currNode.getChild(currNode.getNumChildren()-1).getChild(0).getIdentity());	
						}
						//Creates a new node which represents the answer, and that answer node links to an existing question node
						else
						{
							currNode.getInfo().add(currNode.getInfo().size() - 1, "/~skip-button:" + area.getText() + " ANSW" + numChange);
							currNode.addChild (new SectionNode ("ANSW" + numChange));
							numChange ++;
							currNode.getChild(currNode.getNumChildren() - 1).addInfo(currNode.getChild(currNode.getNumChildren() - 1).getIdentity());
							currNode.getChild(currNode.getNumChildren() - 1).addInfo("/~skip:" + currNode.getChild(0).getChild(0).getIdentity().substring(2));
						}
						field.setText(nodes.get(update).display());
						highlight();
						frame.dispose();
						return;
					}
				}
				//Checks if the user wants to add the method skip and if arguments are valid
				else if (title.equals("Add 'Skip to'"))
				{
					if (area.getLineCount () != 1 || (area.getText().trim().isEmpty() == true) || area.getText().length() == 0)
					{
						JOptionPane.showMessageDialog(new JFrame (), "Please enter only one line that contains characters for a new identifier.");
						return;
					}
					addChild(area.getText());
				}
				//Checks if the user wants to add the method set-voice and if arguments are valid
				else if (title.equals("Add 'Set-voice'"))
				{
					if (checkVoice (area.getText()) == false)
					{
						return;
					}
				}
				//Checks if the user wants to add the method repeat-button and if arguments are valid
				else if (title.equals("Add 'Repeat button'"))
				{
					if (checkRepeatButton (area.getText()) == false)
					{
						return;
					}
				}
				//Checks if the user wants to add the method display cell pins and if arguments are valid
				else if (title.equals("Add 'Display cell pins'"))
				{
					if (checkDispCellPins (area.getText()) == false)
					{
						return;
					}
				}
				//Checks if the user wants to add the method raise or lower pin and if arguments are valid
				else if (title.equals("Add 'Raise one pin'") || title.equals("Add 'Lower one pin'"))
				{
					if (checkRaiseLower (area.getText()) == false)
					{
						return;
					}
				}
				//Checks if the user wants to add the method clear cell and if arguments are valid
				else if (title.equals("Add 'Clear one cell'"))
				{
					if (checkDispCellClear (area.getText ()) == false)
					{
						return;
					}
				}
				//Adds the line before or after the highlighted text
				if (title.equals("Cell") || title.equals("Button"))
				{
					if (checkEdit (area.getText ()) == false)
					{
						return;
					}
					if (title.equals("Cell"))
					{
						cellNum = Integer.parseInt(area.getText());
					}
					else
					{
						buttonNum = Integer.parseInt(area.getText());

					}
					nodes.get(update).getInfo().remove(highlightLocation);
					nodes.get(update).getInfo().add(highlightLocation, identifier + area.getText());

				}
				//Adds normal text to be repeated
				else if (((String)(cb.getSelectedItem())).equals(choices[0]))
				{
					if (title.equals("Add 'Repeat'"))
					{
						nodes.get(update).getInfo().add (highlightLocation, identifier + "\n" + area.getText().trim() + "\n" + "/~endrepeat");
					}
					else
					{
						nodes.get(update).getInfo().add (highlightLocation, identifier + area.getText().trim());

					}
				}
				else
				{
					//Adds the repeat and end-repeat identifiers
					if (title.equals("Add 'Repeat'"))
					{
						nodes.get(update).getInfo().add (highlightLocation + 1, identifier + "\n" + area.getText() + "\n" + "/~endrepeat");
					}
					else
					{
						nodes.get(update).getInfo().add (highlightLocation + 1, identifier + area.getText());
					}

				}
				field.setText(nodes.get(update).display());
				highlight();
				frame.dispose();
			}				
		});

		//Closes the window and does no action if the user presses cancel
		cancel.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed (ActionEvent arg0)
			{
				frame.dispose();
			}				
		});

		//Sets the layout positions of the buttons and text area
		layout.putConstraint(SpringLayout.WEST, cb, 10, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, cb, 13, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, textPane, 225, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, textPane, 40, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, okay, 20, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.SOUTH, okay, -20, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.WEST, lab, 230, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, lab, 20, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.WEST, cancel, 130, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.SOUTH, cancel, -20, SpringLayout.SOUTH, panel);

	}
}