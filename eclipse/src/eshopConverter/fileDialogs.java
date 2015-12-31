package eshopConverter;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class fileDialogs {
	
	private static String inputFile;
	private static String outputFolder;
	private static String fileName;
	
	public static void chooseInputFile(){
        JFileChooser chooser = new JFileChooser();
        
        FileFilter filter = new FileNameExtensionFilter("Music", "mp3");
        
        chooser.addChoosableFileFilter(filter);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setMultiSelectionEnabled(false);
        
        int rueckgabeWert = chooser.showOpenDialog(null);
        
        if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
        {
           inputFile = chooser.getSelectedFile().getPath();
           fileName = chooser.getSelectedFile().getName().substring(0, chooser.getSelectedFile().getName().length()-4);
           EshopConverter.setInputText(inputFile);
        
        }
	}
	
	public static void chooseOutputFolder(){
        JFileChooser chooser = new JFileChooser();
        
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        int rueckgabeWert = chooser.showOpenDialog(null);
        
        if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
        {
           outputFolder = chooser.getSelectedFile().getPath();
           chooser.getCurrentDirectory().exists();
           EshopConverter.setOutputText(outputFolder);
        }
	}
	
	public static String getFilename(){
		return fileName;
	}
	
	public static String getInputPath(){
		return inputFile;
	}
	
}
