package enamel;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;


import javax.swing.JMenu;
import javax.swing.JMenuBar;
import java.awt.Color;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class RevisedApp {

	protected JFrame frmAuthoringApp;
	protected AuthoringDisplay disp;
	public JFileChooser fileSelect;
	public JLabel status;
	public File currentFile;
	public JTextArea textAreaBig;
	StringBuilder sb = new StringBuilder();
	StringBuilder sb2 = new StringBuilder();
	public AboutFrame aboutFrame;
	public HelpFrame helpFrame;
	protected AudioRecording ar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RevisedApp window = new RevisedApp();
					window.frmAuthoringApp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RevisedApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() 
	{
		frmAuthoringApp = new JFrame();
		disp = new AuthoringDisplay (frmAuthoringApp);
		frmAuthoringApp.setResizable(false); //I set this to false so our status bar can properly function
		frmAuthoringApp.setTitle("Authoring App");
		frmAuthoringApp.setBackground(Color.WHITE);
		frmAuthoringApp.setBounds(100, 100, 603, 560);
		frmAuthoringApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAuthoringApp.getContentPane().setLayout(null);


		status = new JLabel("Welcome to Team3's Authoring App!");
		status.setLabelFor(frmAuthoringApp);
		status.setBackground(Color.WHITE);
		status.setBounds(0, 480, 602, 14);
		frmAuthoringApp.getContentPane().add(status);

		JMenuBar menuBar = new JMenuBar();
		frmAuthoringApp.setJMenuBar(menuBar);

		fileSelect = new JFileChooser();


		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmNewMenuItem = new JMenuItem("New");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to create a new file?") == 0) 
				{

					disp.newFile ();
				}
			}
		});
		mnFile.add(mntmNewMenuItem);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (JOptionPane.showConfirmDialog(null,
						"All unsaved work will be deleted. Would like to proceed?") == 0) {
					int openResult = fileSelect.showOpenDialog(null);

					if (openResult == JFileChooser.APPROVE_OPTION) 
					{

						disp.open(fileSelect.getSelectedFile());
					}

				}
			}
		});
		mnFile.add(mntmOpen);

		JSeparator separator_2 = new JSeparator();
		mnFile.add(separator_2);

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (currentFile == null) {
					int saveResult = fileSelect.showSaveDialog(null);
					if (saveResult == JFileChooser.APPROVE_OPTION) {
						saveFile(fileSelect.getSelectedFile());
					}
				} else {
					saveFile(currentFile);
				}
				status.setText("File Saved...");
			}
		});
		mnFile.add(mntmSave);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Save As");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileSelect.showSaveDialog(null);
				saveFile(fileSelect.getSelectedFile());
				status.setText("File Saved...");
			}

		});
		mnFile.add(mntmNewMenuItem_1);

		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);

		//Menu item to exit the program
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (JOptionPane.showConfirmDialog(null, "Are you sure you wish to exit without saving?") == 0) {
					System.exit(JFrame.EXIT_ON_CLOSE);
				}
			}
		});
		mnFile.add(mntmExit);

		// #2
		// Components of Voice
		JMenu mnVoice = new JMenu("Voice");
		menuBar.add(mnVoice);

		JMenuItem mntmRecording = new JMenuItem("Recording");
		mnVoice.add(mntmRecording);

		mntmRecording.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ar = new AudioRecording ();
			}
		});
		//Components under view
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);

		
		//Map menu item to pull up the map of the scenario file broken into nodes
		JMenuItem mntmMap = new JMenuItem("Map");
		mnView.add(mntmMap);
		mntmMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				disp.drawMap();
			}
		});
		
		//Adding play menu item so user can play scenario file
		JMenu playItem = new JMenu ("Play");
		menuBar.add(playItem);
		
		//Attempted to play a scenario file, but met with a weird error where the simulator window was blank
		JMenuItem playSceneItem = new JMenuItem ("Play now");
		playItem.add(playSceneItem);
		playSceneItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File f = new File ("SampleScenarios" + File.separator + "temp.txt");
				disp.save(f.toString());
				/*
				SoundPlayer play = new SoundPlayer ();
				play.setScenarioFile(f.toString()); */
			}
		});

		//Help menu item to display the help menu window
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		//About menu item to display the window that displays the about information
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				aboutFrame = new AboutFrame();
				aboutFrame.setSize(600, 400);
				aboutFrame.setVisible(true);
				aboutFrame.setResizable(false);
				aboutFrame.setLocationRelativeTo(null);
			}

		});
		mnHelp.add(mntmAbout);

		JMenuItem mntmHelp = new JMenuItem("Help Contents");
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				helpFrame = new HelpFrame();
				helpFrame.setSize(600, 620);
				helpFrame.setVisible(true);
				helpFrame.setResizable(false);
				helpFrame.setLocationRelativeTo(null);
			}
		});
		mnHelp.add(mntmHelp);


	}

	//this method is used to implement save and Save As feature
	public void saveFile(File file) 
	{
		String filePath = file.getAbsolutePath();
		if (!filePath.endsWith(".txt")) 
		{
			filePath += ".txt";
		}

		try 
		{
			disp.save(filePath);
		} 
		catch (Exception e) 
		{

		}
	}
	
}