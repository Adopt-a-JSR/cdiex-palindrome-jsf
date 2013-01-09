package cdi.example.palindrome.beans;

import java.util.regex.Pattern;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import cdi.example.palindrome.Palindrome;
import cdi.example.palindrome.bindings.InputSanitizer;
import cdi.example.palindrome.bindings.PalindromeServiceAsync;

import cdi.example.datastore.DataStore;
import cdi.example.datastore.bindings.Repository;


// Note: this is not really asynchronous...but that is irrelevant in this context
@PalindromeServiceAsync
@ApplicationScoped
public class PalindromeServiceAsynchronous implements Palindrome {

	// Field injection
	@Inject @Repository
    private DataStore dataStoreBean;

	@Inject 
	private Logger logger;

	private Pattern pattern; 
	
	public PalindromeServiceAsynchronous() {
	}
	// Constructor injection
	@Inject
	public PalindromeServiceAsynchronous(@InputSanitizer Pattern pattern) {
		this.pattern = pattern;
	}
	
	public boolean isPalindrome(String arg) {
		logger.info("Palindrome service called for [{}].", arg);
		if (arg == null)
			return false;
		String word = pattern.matcher(arg).replaceAll("");
		logger.info("Palindrome arg sanitized to [{}].", word);
		Boolean palindrome = dataStoreBean.getValue(word);
		if ( palindrome == null ) {
			logger.info("Value [{}] not found in datastore.", word);
			StringBuilder rev = new StringBuilder(word);
			rev.reverse();
			if (word.equalsIgnoreCase(rev.toString()))
				palindrome = true;
			else
				palindrome = false;
			dataStoreBean.putValue(word, palindrome);
		}
		else {
			logger.info("Value [{}] retrieved from datastore.", word);			
		}
		return palindrome;
	}

} 
