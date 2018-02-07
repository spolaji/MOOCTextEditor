package textgen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@SuppressWarnings("unlikely-arg-type")
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method
		String[] words = sourceText.split(" +");
		
		int index = 0;
		starter = words[0];
		String prevWord = starter.trim();
		for(int i = 1; i < words.length; i++) {
			index = getNodeWords().indexOf(prevWord);
			if(index >= 0) {
				
				ListNode oldNode = wordList.get(index);
				oldNode.addNextWord(words[i]);
			} else {
				
				ListNode newNode = new ListNode(prevWord);
				newNode.addNextWord(words[i]);
				wordList.add(newNode);
			}	
			prevWord = words[i].trim();
			
			if(i == words.length -1) {
				index = getNodeWords().indexOf(prevWord);
				if(index >= 0) {
					ListNode oldNode = wordList.get(index);
					oldNode.addNextWord(words[0]);
				} else {
					ListNode newNode = new ListNode(prevWord);
					newNode.addNextWord(words[0]);
					wordList.add(newNode);
				}
					
			}
		}
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		String starterString = getNodeWords().get(0);
		StringBuilder sb = new StringBuilder(starterString + " ");
		Random generator = new Random();
		String word = "";
		int index = 0;
		for(int i = 0; i < numWords; i++) {
			if(index >= 0 && index < getNodeWords().size()) {
				ListNode currWord = wordList.get(index);
				word = currWord.getRandomNextWord(generator);
				sb.append(word + " ");
			}
			index = getNodeWords().indexOf(word);
		}
		return sb.toString();
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
	}
	
	// TODO: Add any private helper methods you need here.
	public List<String> getNodeWords() {
		List<String> list = new ArrayList<String>();
		for(ListNode ln : wordList) {
			list.add(ln.getWord());
		}
		return list;
	}
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		/*
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		//String textString = "hi there hi Leo";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		*/
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.train(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}
	
	public int getWordCount() {
		return nextWords.size();
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
	    int randomInt = generator.nextInt(nextWords.size());
		return nextWords.get(randomInt);
	}
	
	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


