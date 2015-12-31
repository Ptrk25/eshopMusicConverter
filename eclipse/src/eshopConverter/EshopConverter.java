package eshopConverter;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class EshopConverter {

	private JFrame frmEshopMusicConverter;
	private static JTextField textField;
	private static JTextField textField_1;
	private static JProgressBar progressBar;
	private static JLabel lblPercentage;
	private static JButton btnConvert;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EshopConverter window = new EshopConverter();
					window.frmEshopMusicConverter.setLocationRelativeTo(null);
					window.frmEshopMusicConverter.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EshopConverter() {
		initialize();
	}
	
	public static void setInputText(String path){
		textField.setText(path);
	}
	
	public static void setOutputText(String path){
		textField_1.setText(path);
	}
	
	public static void setProgressbarValue(double value){
		int progress = (int) value;
		progressBar.setValue(progress);
	}
	
	public static void setPercentage(double value){
		int progress = (int) value;
		lblPercentage.setText(progress + " %");
	}
	
	public static void setButtonCnv(Boolean state){
		btnConvert.setEnabled(state);
	}
	
	public static String getInputText(){
		return textField.getText();
	}
	
	public static String getOutputText(){
		return textField_1.getText();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEshopMusicConverter = new JFrame();
		frmEshopMusicConverter.setResizable(false);
		frmEshopMusicConverter.setTitle("eShop Music Converter");
		frmEshopMusicConverter.setBounds(100, 100, 450, 161);
		frmEshopMusicConverter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmEshopMusicConverter.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(10, 11, 262, 20);
		frmEshopMusicConverter.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(10, 42, 262, 20);
		frmEshopMusicConverter.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnOpenmpFile = new JButton("Open .mp3 file");
		btnOpenmpFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileDialogs.chooseInputFile();
			}
		});
		btnOpenmpFile.setBounds(282, 10, 152, 23);
		frmEshopMusicConverter.getContentPane().add(btnOpenmpFile);
		
		JButton btnSelectOutputFolder = new JButton("Select output folder");
		btnSelectOutputFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileDialogs.chooseOutputFolder();
			}
		});
		btnSelectOutputFolder.setBounds(282, 41, 152, 23);
		frmEshopMusicConverter.getContentPane().add(btnSelectOutputFolder);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(69, 73, 326, 20);
		progressBar.setMaximum(100);
		frmEshopMusicConverter.getContentPane().add(progressBar);
		
		JLabel lblProgress = new JLabel("Progress:");
		lblProgress.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblProgress.setBounds(10, 73, 64, 14);
		frmEshopMusicConverter.getContentPane().add(lblProgress);
		
		lblPercentage = new JLabel("0 %");
		lblPercentage.setBounds(405, 75, 37, 14);
		frmEshopMusicConverter.getContentPane().add(lblPercentage);
		
		btnConvert = new JButton("Convert");
		btnConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(textField.getText().length() > 3){
					if(textField_1.getText().length() > 3){
						
						if(!new File(EshopConverter.getOutputText() + "\\" + fileDialogs.getFilename()).exists()){
							new File(EshopConverter.getOutputText() + "\\" + fileDialogs.getFilename()).mkdirs();
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							Converter convert = new Converter();
							convert.start();
						}else{
							JOptionPane.showMessageDialog(null, "Your mp3 is already converted, select another one.", "File already exists", JOptionPane.INFORMATION_MESSAGE, null);
						}
						
					}else{
						JOptionPane.showMessageDialog(null, "Please select an output folder!", "Error", JOptionPane.ERROR_MESSAGE, null);
					}
				}else{
					JOptionPane.showMessageDialog(null, "Please select a .mp3 file!", "Error", JOptionPane.ERROR_MESSAGE, null);
				}
				
			}
		});
		btnConvert.setBounds(10, 98, 424, 23);
		frmEshopMusicConverter.getContentPane().add(btnConvert);
		
		FfmpegUnpacker.unpackFile();
		
	}
}
