package data.chunking;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class ChunkingMain {

	public static void main(String[] args) throws IOException, ParseException{
		File originFile = new File("D:\\Documents\\Uni\\Master thesis\\Datasets\\DEBS 2015\\sorted_data_complete.csv");
		File targetDir = new File("D:\\Documents\\Uni\\Master thesis\\Datasets\\DEBS 2015\\Chunked Data");
		new DataChunker(originFile, targetDir).chunk();
	}
}
