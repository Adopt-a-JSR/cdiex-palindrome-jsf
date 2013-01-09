package cdi.example.palindrome;

import java.io.Serializable;


public class PalindromeQuery implements Serializable {

	private static final long serialVersionUID = 1L;

	private String word;
	private Boolean palindrome;
	
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}

	public Boolean getPalindrome() {
		return palindrome;
	}
	public void setPalindrome(Boolean palindrome) {
		this.palindrome = palindrome;
	}
	
}
