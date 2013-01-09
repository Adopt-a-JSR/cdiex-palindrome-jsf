package cdi.example.palindrome.aop;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.slf4j.Logger;

import cdi.example.palindrome.beans.SimplePalindromeService;



// TODO this does not work ... 
// See: https://issues.jboss.org/browse/CDI-44 https://issues.jboss.org/browse/CDI-74
// CDI 1.1 proposes decorators implementation by subclassing
//@Decorator
//@Dependent
public class SimplePalindromeDecorator { //extends SimplePalindromeService {

//	@Inject @Delegate @Any 
	private SimplePalindromeService palindrome;

	@Inject
	private Logger logger;

	
	public SimplePalindromeDecorator() {
		super();
	}

//	@Override
	public boolean isPalindrome(String arg) {
		boolean result = palindrome.isPalindrome(arg);
		if ( result ) {
			logger.warn("PalindromeDecorator Implementation - found new palindrome: [{}]", arg);
		}
		return result;
	}
	
}
