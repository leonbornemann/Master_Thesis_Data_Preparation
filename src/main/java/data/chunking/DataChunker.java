package data.chunking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataChunker {
	
	private int daysPerFile = 5;
	private File originFile;
	private File targetDir;
	private PrintWriter out;
	private int curFileNumber =0;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public DataChunker(File originFile,File targetDir){
		this.originFile = originFile;
		this.targetDir = targetDir;
	}
	
	public void chunk() throws IOException, ParseException{
		switchToNextOutFile();
		BufferedReader br = new BufferedReader(new FileReader(originFile));
		String curLine = br.readLine();
		LocalDateTime startDay = parseTime(curLine.split(",")[3]);
		LocalDateTime prevDropOffTime = startDay;
		while(curLine!=null){
			String[] tokens = curLine.split(",");
			LocalDateTime dropOffTime = parseTime(tokens[3]);
			assert(prevDropOffTime.compareTo(dropOffTime) <=0);
			if(dropOffTime.minusDays(daysPerFile).isAfter(startDay)){
				startDay = dropOffTime;
				switchToNextOutFile();
			}
			prevDropOffTime = dropOffTime;
			out.println(curLine);
			curLine = br.readLine();
		}
		br.close();
		out.close();
	}

	private LocalDateTime parseTime(String string) throws ParseException {
		LocalDateTime dateTime = LocalDateTime.parse(string, formatter);
		return dateTime;
	}

	private void switchToNextOutFile() throws FileNotFoundException {
		if(out!=null){
			out.close();
		}
		File newTargetFile = new File(targetDir.getAbsolutePath() + File.separator + "sorted_data_"+curFileNumber + ".csv");
		out = new PrintWriter(newTargetFile);
		curFileNumber++;
	}
}
