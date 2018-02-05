package application;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	
	private String text = "one (1), two (2), three (3)";
	
	public List<String> getTokens(String pattern)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(text);
		
		while (m.find()) {
			tokens.add(m.group());
		}
		
		return tokens;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test test = new Test();
		for(String s : "%one%%two%%%three%%%%".split("one|two|three"))
			System.out.println(s);
		
	}

}
