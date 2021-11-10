import java.util.HashMap;
import java.io.*;
import java.util.Scanner;
public class IRMatrix {
	public static void main(String[] args) throws IOException{
//Keeps track of current word and how many times it occurs in text file
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