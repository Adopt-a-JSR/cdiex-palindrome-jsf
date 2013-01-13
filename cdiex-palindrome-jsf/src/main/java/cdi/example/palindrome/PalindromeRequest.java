package cdi.example.palindrome;


import javax.inject.Inject;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import cdi.example.palindrome.beans.SimplePalindromeService;



@Named
@RequestScoped
public class PalindromeRequest {
    
	// Context: Injecting an application scoped bean into a request scoped bean.
//	@Inject @PalindromeServiceSync 
//	private Palindrome palindromeBean; 
	@Inject
	private SimplePalindromeService palindromeBean; 

	private String word;
    private Boolean palindrome;

    public void checkPalindrome() {
        this.palindrome = palindromeBean.isPalindrome(word);
    }

    public Boolean getPalindrome() {
        return palindrome;
    }

    public void setWord(String word) {
        this.word = word;
    }
    public String getWord() {
        return word;
    }

}
