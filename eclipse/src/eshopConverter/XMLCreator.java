package eshopConverter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

public class XMLCreator {
	
	public static int calculateSamples() throws UnsupportedAudioFileException, IOException{
		File file = new File(fileDialogs.getInputPath());
		
	    AudioFileFormat baseFileFormat = new MpegAudioFileReader().getAudioFileFormat(file);
	    Map properties = baseFileFormat.properties();
	    Long duration = (Long) properties.get("duration");
	    int samples =  (int) (duration/1000/1000)*44100;
	    return samples;
	}
	
	public static int getDataSize(){
		int dataSize = 0;
		
		File file = new File((EshopConverter.getOutputText() + "\\" +fileDialogs.getFilename() + "\\"+"boss_bgm.aac"));
		dataSize = (int) file.length();
		
		if(dataSize >= 8388608){
			EshopConverter.setPercentage(100.0);
	        EshopConverter.setProgressbarValue(100.0);
			
			JOptionPane.showMessageDialog(null, "Converted file is bigger than 8 MB! It won't work, please select an other file with less duration.", "Warning", JOptionPane.WARNING_MESSAGE, null);
			return -1;
		}
		
		return dataSize;
	}
	
	public static void createXMLFile() throws UnsupportedAudioFileException, IOException{
		int dataSize = getDataSize();
		
		if(dataSize != -1){
			EshopConverter.setPercentage(100.0);
	        EshopConverter.setProgressbarValue(100.0);
			JOptionPane.showMessageDialog(null, "Successfully converted!", "Done", JOptionPane.INFORMATION_MESSAGE, null);
			
			PrintWriter writer = new PrintWriter(EshopConverter.getOutputText() + "\\" + fileDialogs.getFilename() + "\\" + "boss_xml.xml", "UTF-8");
			String XMLContent = "<?xml version=\"1.0\" encoding=\"utf-8\"?><header><version>1</version><task>1</task><priority>128</priority><beginYear>2015</beginYear><beginMonth>10</beginMonth><beginDay>1</beginDay><beginHour>15</beginHour><beginMinute>0</beginMinute><beginSecond>0</beginSecond><endYear>2032</endYear><endMonth>10</endMonth><endDay>15</endDay><endHour>15</endHour><endMinute>0</endMinute><endSecond>0</endSecond><indefinite>1</indefinite><dataSize>" + dataSize + "</dataSize><loopStartFrame>0</loopStartFrame><loopEndFrame>" + calculateSamples() + "</loopEndFrame></header>";
			writer.print(XMLContent);
			writer.close();
		}else{
			File dir = new File(EshopConverter.getOutputText() + "\\" + fileDialogs.getFilename());
			File bgm = new File(EshopConverter.getOutputText() + "\\" + fileDialogs.getFilename() + "\\" + "boss_bgm.aac");
			
			bgm.delete();
			dir.delete();
		}
	}
}