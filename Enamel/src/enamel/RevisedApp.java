package enamel;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.CardLayout;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import java.awt.Color;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class RevisedApp {

	private JFrame frmAuthoringApp;
	private AuthoringDisplay disp;
	public JFileChooser fileSelect;
	public JLabel status;
	public File currentFile;
	public JTextArea textAreaBig;
	StringBuilder sb = new StringBuilder();
	StringBuilder sb2 = new StringBuilder();

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
	public void initialize() {
		frmAuthoringApp = new JFrame();
		disp = new AuthoringDisplay (frmAuthoringApp);
		frmAuthoringApp.setResizable(false); //I set this to false so our status bar can properly function
		frmAuthoringApp.setTitle("Authoring App");
		frmAuthoringApp.setBackground(Color.WHITE);
		frmAuthoringApp.setBounds(100, 100, 603, 560);
		frmAuthoringApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAuthoringApp.getContentPane().setLayout(null);

		// this editable text area
	//	textAreaBig = new JTextArea();
	//	textAreaBig.setBounds(284, 29, 319, 288);
	//	frmAuthoringApp.getContentPane().add(textAreaBig);

		// initial status of the app
		status = new JLabel("Welcome to Team3's Authoring App!");
		status.setLabelFor(frmAuthoringApp);
		status.setBackground(Color.WHITE);
		status.setBounds(0, 480, 602, 14);
		frmAuthoringApp.getContentPane().add(status);

		// Menu bar
		JMenuBar menuBar = new JMenuBar();
		frmAuthoringApp.setJMenuBar(menuBar);

		// File Chooser
		fileSelect = new JFileChooser();

		
		// Components of the menu bar

		// #1
		// Components under File mn
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmNewMenuItem = new JMenuItem("New");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to create a new file?") == 0) {
					textAreaBig.setText("");
					status.setText("Generated New File....."); //the empty text area created is the new file
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

					if (openResult == JFileChooser.APPROVE_OPTION) {
						
						//openFile(fileSelect.getSelectedFile());
						disp.open(fileSelect.getSelectedFile());
					//	status.setText("Opened File...");
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
						saveFile(fileSelect.getSelectedFile(), textAreaBig.getText());
					}
				} else {
					saveFile(currentFile, textAreaBig.getText());
				}
				status.setText("File Saved...");
			}
		});
		mnFile.add(mntmSave);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Save As");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int saveResult = fileSelect.showSaveDialog(null);
				saveFile(fileSelect.getSelectedFile(), textAreaBig.getText());
				status.setText("File Saved...");
			}

		});
		mnFile.add(mntmNewMenuItem_1);

		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);

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

		// #3 components under view
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);

		JMenuItem mntmMap = new JMenuItem("Map");
		mnView.add(mntmMap);
		
		
	}

	//this method is used to implement save and Save As feature
	public void saveFile(File file, String contents) {
		BufferedWriter writer = null;
		String filePath = file.getPath();
		if (!filePath.endsWith(".txt")) {
			filePath += ".txt";
		}

		try {
			writer = new BufferedWriter(new FileWriter(filePath));
			writer.write(contents);
			writer.close();
			textAreaBig.setText(contents);

			// sets the frame title to that of the file path
			frmAuthoringApp.setTitle("Authoring App" + filePath);
			currentFile = file;
		} catch (Exception e) {

		}
	}

	//this method is used to load and open file
	public void openFile(File file) {
		if (file.canRead()) {

			String filePath = file.getPath();
			String fileContents = "";
			String fileName = "";

			if (filePath.endsWith(".txt")) {
				try {
					Scanner scan = new Scanner(new FileInputStream(file));
					while (scan.hasNextLine()) {
						sb.append(scan.nextLine());
						sb.append("\n");

					}
					sb2.append(file.getName());
					fileName = sb2.toString();
					fileContents = sb.toString();

					scan.close();
				} catch (FileNotFoundException e) {

				}

				textAreaBig.setText(fileContents);

				// sets the frame title to that of the file path
				// can use the whole file path or just file name
				// use the one you feel you be best
				frmAuthoringApp.setTitle("Authoring App" + filePath + fileName);
				currentFile = file;
			} else {
				JOptionPane.showMessageDialog(null, "That file type is not supported!\nOnly .txt files are supported.");

			}
		} else
			JOptionPane.showMessageDialog(null, "Could not open file");
	}
}
