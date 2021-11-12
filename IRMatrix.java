import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
This class contains details such as the word frequency count for each term and term frequency for each term of the document.
 */

public class IRMatrix {
	
	public static class document{
		
		int docSize;
		String title;
		String description;
		String narrative;
		
		public document(String t, String d, String n) {
			title = t;
			description = d;
			narrative = n;
			docSize++;
		}
		
		public document() {
			title = null;
			description = null;
			narrative = null;
			docSize = 0;
		}
		
		public String getTitle() {
			return title;
		}
		
		public String getDescription() {
			return description;
		}
		
		
		public String getNarrative() {
			return narrative;
		}
		
	}
	


	public static class TfidfCalculation {

		SortedSet<String> wordList = new TreeSet(String.CASE_INSENSITIVE_ORDER);

		//Calculates inverse Doc frequency.
		public HashMap<String,Double> calculateInverseDocFrequency(DocumentProperties [] docProperties)
		{

			HashMap<String,Double> InverseDocFreqMap = new HashMap<>();
			int size = docProperties.length;
			double wordCount ;
			for (String word : wordList) {
				wordCount = 0;
				for(int i=0;i<size;i++)
				{
					//System.out.println(i);
					HashMap<String,Integer> tempMap = docProperties[i].getWordCountMap();
					if(tempMap.containsKey(word))
					{
						wordCount++;
						continue;
					}
				}
				double temp = size/ wordCount;
				double idf = 1 + Math.log(temp);
				InverseDocFreqMap.put(word,idf);
			}
			return InverseDocFreqMap;
		}

		//calculates Term frequency for all terms
		public HashMap<String,Double> calculateTermFrequency(HashMap<String,Integer>inputMap) {

			HashMap<String ,Double> termFreqMap = new HashMap<>();
			double sum = 0.0;
			//Get the sum of all elements in hashmap
			for (float val : inputMap.values()) {
				sum += val;
			}

			//create a new hashMap with Tf values in it.
			Iterator it = inputMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				double tf = (Integer)pair.getValue()/ sum;
				termFreqMap.put((pair.getKey().toString()),tf);
			}
			return termFreqMap;
		}

		//Returns if input contains numbers or not
		public  boolean isDigit(String input)
		{
			String regex = "(.)*(\\d)(.)*";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(input);

			boolean isMatched = matcher.matches();
			if (isMatched) {
				return true;
			}
			return false;
		}

		//Writes the contents of hashmap to CSV file
		public  void outputAsText(HashMap<String,Double>treeMap,String OutputPath) throws IOException {
			StringBuilder builder = new StringBuilder();
        	for (Map.Entry<String, Double> keymap : treeMap.entrySet()) {
        		builder.append(keymap.getKey());
        		builder.append(", ");
        		builder.append(keymap.getValue());
        		builder.append("\r\n");
        	}
        	String content = builder.toString().trim();
        	BufferedWriter writer = new BufferedWriter(new FileWriter(OutputPath));
        	writer.write(content);
        	writer.close();
		}
		//cleaning up the input by removing .,:"
		public  String cleanseInput(String input)
		{
			String newStr = input.replaceAll("[, . : ;\"]", "");
			newStr = newStr.replaceAll("\\p{P}","");
			newStr = newStr.replaceAll("\t","");
			return newStr;
		}
		
		// Converts the input text file to hashmap and even dumps the final output as CSV files
		public  HashMap<String, Integer> getTermsFromDoc(document doc ,int count) {
			HashMap<String,Integer> WordCount = new HashMap<String,Integer>();
			Scanner Doc = new Scanner(doc.getDescription());
			HashMap<String, Integer> finalMap = new HashMap<>();

			String line = Doc.nextLine();
			while(line!=null)
			{
				String[] words = line.toLowerCase().split(" ");
				for(String term : words)
				{
					//cleaning up the term ie removing .,:"
					//System.out.println(term);
					term = cleanseInput(term);
					
					//ignoring numbers
					if(isDigit(term))
						continue;
					
					if(term.length() == 0)
						continue;
					
					wordList.add(term);
					if(WordCount.containsKey(term))
						WordCount.put(term,WordCount.get(term)+1);
					
					else
						WordCount.put(term,1);
				}
				if(Doc.hasNext())
					line = Doc.nextLine();
				else
					line = null;

			}
			//System.out.println(WordCount.toString());
		
			// sorting the hashmap
			Map<String, Integer> treeMap = new TreeMap<>(WordCount);
			finalMap = new HashMap<String, Integer>(treeMap);
			return finalMap;
		}
	}
	
	public static void main(String[] args) throws IOException{
		
		
		//Gets Document from File (being read in by user)
		ArrayList<document> doclist = new ArrayList<document>();
	    System.out.print("Enter input file: ");
	    Scanner scan = new Scanner(System.in);
        int count = 0;
	    TfidfCalculation TfidfObj = new TfidfCalculation();
	    File fileName = new File(scan.nextLine());
	    Scanner file = new Scanner(fileName);
	    
	    int i = 0;
	    while(file.hasNext()) {
	    	doclist.add(new document(file.nextLine(), file.nextLine(), file.nextLine()));
	    	doclist.get(i).getTitle();
	    	doclist.get(i).getDescription();
	    	doclist.get(i).getNarrative();

	    	file.nextLine(); //Gets rid of Seperator
	    	i++;
	    }
	    
	    int noOfDocs = doclist.size();
	    System.out.println("Started");
	    DocumentProperties [] docProperties = new DocumentProperties[noOfDocs];
	    for(int j = 0; j < doclist.size(); j++) { 
                docProperties[count] = new DocumentProperties();
                //System.out.println("Document Found " + doclist.get(j).getTitle()); //Debugging
                HashMap<String,Integer> wordCount = TfidfObj.getTermsFromDoc(doclist.get(j),count);
                docProperties[count].setWordCountMap(wordCount);
                
                //System.out.println(); //Debugging
                HashMap<String,Double> termFrequency = TfidfObj.calculateTermFrequency(docProperties[count].DocWordCounts);
                docProperties[count].setTermFreqMap(termFrequency);
                count++;
            
        }
	    
        //System.out.println("Done");
	    //calculating InverseDocument frequency
        HashMap<String,Double> inverseDocFreqMap = TfidfObj.calculateInverseDocFrequency(docProperties);
        System.out.println(inverseDocFreqMap.toString());

        //Calculating tf-idf
        count = 0;
        HashMap<String,Double> tfIDF = new HashMap<>();

        for (int j = 0; j < doclist.size(); j++) {
                double tfIdfValue = 0.0;
                double idfVal = 0.0;
                HashMap<String,Double> tf = docProperties[count].getTermFreqMap();
                Iterator itTF = tf.entrySet().iterator();
                while (itTF.hasNext()) {
                    Map.Entry pair = (Map.Entry)itTF.next();
                    double tfVal  = (Double)pair.getValue() ;
                    if(inverseDocFreqMap.containsKey((String)pair.getKey()))
                    {
                         idfVal = inverseDocFreqMap.get((String)pair.getKey());
                    }
                    tfIdfValue = tfVal *idfVal;
                    tfIDF.put((pair.getKey().toString()),tfIdfValue);
                }
                String OutPutPath = "datafile_output.txt";
                TfidfObj.outputAsText(tfIDF,OutPutPath);
                count++;
            
        }
        
        System.out.println("Done");
	    
	    /*
		HashMap<String, Integer> WordCountMap = new HashMap<String, Integer>();
//Holds the total amount of words
		int amount = 0;
//Holds the frequency of words  ->     WordCountMap.get(currentWord) / sum
		HashMap<String, Double> WordFrequencyMap = new HashMap<String, Double>();
		//HashMap<>
		File myFile = new File("");
		Scanner input = new Scanner(myFile);
//Go through the file
		/*while(input.hasNext()) {
			String currentWords = 
			WordCountMap.put(, value)
		}
		*/
	}
}
class Documents{
	String title;
	String description;
	String Narrative;
	int sum;
	HashMap<String, Integer> WordCountMap;
	HashMap<String, Double> frequency; 
	public Documents() {
		WordCountMap = new HashMap<String, Integer>();
		frequency = new HashMap<String, Double>();
	}
	public void setMap() {
			//String currentLine = input.nextLine();//Gets current line of text file
			String allWords = this.title + " " + this.description + " " + this.Narrative;
			String[] wordArray = allWords.split(" ");//Creates word array of current line
			sum = wordArray.length;
			for(String currWord: wordArray) {//Traverse through array
				WordCountMap.put(currWord, WordCountMap.getOrDefault(currWord, 0) + 1);//Either increment or initiailize the new word in map
				frequency.put(currWord, (double) (WordCountMap.get(currWord)/sum));
				
			}
		}

	public int getWordCount(String word) {//Returns the word count of the word
		return WordCountMap.get(word);
	}
	public void printStats() {//Prints all the words with occurences and frequency
		//Word Occurences Frequency
		for(String word: WordCountMap.keySet()) {
			System.out.println(word + " " + WordCountMap.get(word) + " " + frequency.get(word));
		}
	}
}