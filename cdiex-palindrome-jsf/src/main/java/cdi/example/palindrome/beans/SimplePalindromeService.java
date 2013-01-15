package cdi.example.palindrome.beans;

import java.util.regex.Pattern;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import cdi.example.palindrome.bindings.Auditable;
import cdi.example.palindrome.bindings.InputSanitizer;

import cdi.example.datastore.DataStore;
import cdi.example.datastore.bindings.Repository;


// Note: this cannot be decorated as the decorator would have to extend this class and  
// "...The decorator bean class and its superclasses are not decorated types of the decorator."
// See: https://issues.jboss.org/browse/CDI-221, https://issues.jboss.org/browse/CDI-224
// It can however be intercepted as shown below.
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
