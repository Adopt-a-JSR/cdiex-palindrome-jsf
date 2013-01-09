package cdi.example.palindrome.aop;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.slf4j.Logger;

import cdi.example.palindrome.Palindrome;



@Decorator
@Dependent
public abstract class PalindromeDecorator implements Palindrome {

	@Inject @Delegate @Any 
	private Palindrome palindrome;

	@Inject
	private Logger logger;

	
	public PalindromeDecorator() {
		super();
	}

	public boolean isPalindrome(String arg) {
		boolean result = palindrome.isPalindrome(arg);
		if ( result ) {
			logger.warn("PalindromeDecorator Interface - found new palindrome: [{}]", arg);
		}
		return result;
	}
	
}
