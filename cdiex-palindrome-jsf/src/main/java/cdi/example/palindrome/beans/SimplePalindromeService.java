package cdi.example.palindrome.beans;

import java.util.regex.Pattern;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import cdi.example.palindrome.bindings.Auditable;
import cdi.example.palindrome.bindings.InputSanitizer;

import cdi.example.datastore.DataStore;
import cdi.example.datastore.bindings.Repository;


// Singleton scope does not mix well with beans that can be passivated
// (e.g. session or conversation scoped). Serialization could change the 
// singleton into something that is not really a singleton. Probably not good!
//@Singleton 
@ApplicationScoped
public class SimplePalindromeService {

	// Field injection
	@Inject @Repository
    private DataStore dataStoreBean;

	@Inject 
	private Logger logger;

	private Pattern pattern; 
	
	public SimplePalindromeService() {
	}
	// Constructor injection
	@Inject
	public SimplePalindromeService(@InputSanitizer Pattern pattern) {
		this.pattern = pattern;
	}
	
	@Auditable
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
