package eshopConverter;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.sound.sampled.UnsupportedAudioFileException;

public class Converter extends Thread{
	
	private String ffmpeg = FfmpegUnpacker.getFfmpeg();
	
	public void run(){
		EshopConverter.setButtonCnv(false);
		try {
			Process p= Runtime.getRuntime().exec("cmd /c " + ffmpeg + " -i \"" + EshopConverter.getInputText() + "\" -c:a libfaac -b:a 128k -ar 44100 \"" + EshopConverter.getOutputText() + "\\" + fileDialogs.getFilename() + "\\" + "boss_bgm.aac\"");
			Scanner sc = new Scanner(p.getErrorStream());
			
	        // Find duration
	        Pattern durPattern = Pattern.compile("(?<=Duration: )[^,]*");
	        String dur = sc.findWithinHorizon(durPattern, 0);
	        if (dur == null)
	          throw new RuntimeException("Could not parse duration.");
	        String[] hms = dur.split(":");
	        double totalSecs = Integer.parseInt(hms[0]) * 3600
	                         + Integer.parseInt(hms[1]) *   60
	                         + Double.parseDouble(hms[2]);
	        //System.out.println("Total duration: " + totalSecs + " seconds.");

	        // Find time as long as possible.
	        Pattern timePattern = Pattern.compile("(?<=time=)[\\d:.]*");
	        String match;
	        while (null != (match = sc.findWithinHorizon(timePattern, 0))) {
	        	String[] match2 = match.split(":");
	        	double Secs = Integer.parseInt(match2[0]) * 3600
                        + Integer.parseInt(match2[1]) *   60
                        + Double.parseDouble(match2[2]);
	        	double progress = (Secs / totalSecs)*100;
	        	EshopConverter.setProgressbarValue(progress);
	        	EshopConverter.setPercentage(progress);
	        }
	        try {
				XMLCreator.createXMLFile();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	              
	        EshopConverter.setPercentage(0.0);
	        EshopConverter.setProgressbarValue(0.0);
			EshopConverter.setButtonCnv(true);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
  }
	
}
