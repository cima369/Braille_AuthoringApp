package enamel;

	import java.awt.BorderLayout;
	 
	import javax.swing.JFrame;
	import javax.swing.JTextArea;
	 
	public class AboutFrame  extends JFrame{
	               
	        public JTextArea exp;
	       
	        public AboutFrame(){
	                setTitle("About-Team 3's Authoring App");
	               
	                exp = new JTextArea("Welcome to Team 3's Authoring App!\n\nThis program was made Eric Dao, Siddharth Bhardwaj and Dong Lee.\n\n It was created for EECS 2311 group project.");
	                exp.setLineWrap(true);
	                exp.setWrapStyleWord(true);
	                exp.setEditable(false);
	                add(exp, BorderLayout.CENTER);
	        }
	 
	}
