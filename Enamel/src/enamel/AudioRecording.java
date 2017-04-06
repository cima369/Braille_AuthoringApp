package enamel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;

@SuppressWarnings("serial")
public class AudioRecording extends JFrame {

	AudioFormat audioFormat;
	TargetDataLine targetDataLine;

	final JButton captureBtn = new JButton("Record");
	final JButton stopBtn = new JButton("Stop");
	final JTextField fileName = new JTextField("Place file name here!");
	final JPanel btnPanel = new JPanel();
	JFileChooser chooser = new JFileChooser();

/*	public static void main(String args[]) {
		new AudioRecording();
	} */

	public AudioRecording() {
		captureBtn.setEnabled(true);
		stopBtn.setEnabled(false);
		chooser.setDialogTitle("Saving File");
		chooser.setCurrentDirectory(new java.io.File("."));

		captureBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				captureBtn.setEnabled(false);
				stopBtn.setEnabled(true);
				
				captureAudio();

			}
		}
		);

		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				captureBtn.setEnabled(true);
				stopBtn.setEnabled(false);
		
				targetDataLine.stop();
				targetDataLine.close();

			}
		}
		);
		getContentPane().add(captureBtn);
		getContentPane().add(stopBtn);
		getContentPane().add(fileName);
		getContentPane().add(btnPanel);

		getContentPane().setLayout(new FlowLayout());
		setTitle("Voice Recording");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300, 95);
		setVisible(true);
	}

	private void captureAudio() {
		try {
			
			audioFormat = getAudioFormat();
			DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
			targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);

			new CaptureThread().start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	
	private AudioFormat getAudioFormat() {
		float sampleRate = 44100.0F;
		// 8000,11025,16000,22050,44100
		int sampleSizeInBits = 16;
		// 8,16
		int channels = 1;
		// 1,2
		boolean signed = true;
		// true,false
		boolean bigEndian = false;
		// true,false
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}
	
	class CaptureThread extends Thread {
		public void run() {
			AudioFileFormat.Type fileType = null;
			File audioFile = null;

			fileType = AudioFileFormat.Type.WAVE;
			
			audioFile = new File("SampleScenarios" + File.separator + "AudioFiles" + 
			File.separator + fileName.getText() + ".wav");

			try {
				targetDataLine.open(audioFormat);
				targetDataLine.start();
				AudioSystem.write(new AudioInputStream(targetDataLine), fileType, audioFile);
			} catch (Exception e) {
				e.printStackTrace();
			} 

		}
	}

}