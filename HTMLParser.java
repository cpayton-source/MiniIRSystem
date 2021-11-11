import java.io.*;
import java.io.IOException; 
import org.jsoup.Jsoup; 
import org.jsoup.nodes.Document; 
import org.jsoup.nodes.Element;

public class HTMLParser {
	
	public static void main(String[] args) {
		
		//JSoup Example - Reading HTML page from URL 
		String document = null;
		String[] Number;
		Document doc; 
		
		String[] title;
		String[] description;
		String[] narrative;
	
		try 
		{ 
			doc = Jsoup.connect("https://trec.nist.gov/data/blog/07/07.topics.901-950").get(); 
			document = doc.body().text();
		} 
		catch (IOException e) 
		{ 
			e.printStackTrace(); 
		} 
		
		//Separates document by number
		Number = document.split("Number: 9");
		
		String DATA_FILE = "datafile.txt";
		
		//Try Loop to Add HTML contents to a Data Text File
		try {
			FileWriter datafile = new FileWriter(DATA_FILE);
			
			//This Method separates the contents of the document and stores them into their respective list
			title = new String[document.length()];
			description = new String[document.length()];
			narrative = new String[document.length()];
			
			for(int i = 1; i < Number.length; i++) {

				title[i] = Number[i].substring(4, Number[i].indexOf("Description"));
				description[i] = Number[i].substring(Number[i].indexOf("Description") + 13, Number[i].indexOf("Narrative"));
				narrative[i] = Number[i].substring(Number[i].indexOf("Narrative") + 11 , Number[i].length());

				datafile.write(title[i]);
				datafile.write("\r\n");
				datafile.write(description[i]);
				datafile.write("\r\n");
				datafile.write(narrative[i]);
				datafile.write("\r\n");
				datafile.write("\r\n");


			}
			datafile.close();
			System.out.println("Done!!");
		}
		catch (IOException e) 
		{ 
			e.printStackTrace(); 
		} 
	}
}
