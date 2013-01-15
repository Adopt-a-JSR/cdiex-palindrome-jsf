package cdi.example.palindrome.decorators;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Decorated;
import javax.enterprise.inject.spi.Bean;
import javax.inject.Inject;

import org.slf4j.Logger;

import cdi.example.palindrome.Palindrome;


@Decorator
@Dependent
public abstract class PalindromeDecorator implements Palindrome {

	@Inject @Delegate @Any 
	private Palindrome palindrome;

	// CDI 1.1 - Allow injection of Bean object for a bean. 
	// This works also for Decorated (@Decorated) and Intercepted (@Intercepted) beans.
	// See: https://issues.jboss.org/browse/CDI-92
	@Inject @Decorated
	private Bean<Palindrome> metadata;
	
	@Inject
	private Logger logger;

	
	public PalindromeDecorator() {
		super();
	}

	public boolean isPalindrome(String arg) {
		boolean result = palindrome.isPalindrome(arg);
		if ( result ) {
			logger.warn("Palindrome Decorator - [{}] == true", arg);
		}
		return result;
	}

	public String serviceInfo() {
		StringBuilder sb = new StringBuilder();
        //
		String initialInfo = palindrome.serviceInfo();
		if ( initialInfo != null )
			sb.append(initialInfo);
		//
		sb.append("Bean Name = [").append(metadata.getName()).append("]; ");
		sb.append("Scope = [").append(metadata.getScope().getName()).append("]; ");
		sb.append("Is Alternative = [").append(metadata.isAlternative()).append("]; ");
		// types
		sb.append("Type(s) = ["); 
		Set<Type> types = metadata.getTypes();
		List<String> typesDesc = new ArrayList<String>();
		for ( Type t : types ) {
			if ( t != null )
				typesDesc.add(t.getClass().getName());
		}		
		sb.append(Arrays.toString(typesDesc.toArray(new String[0]))).append("]"); 		
		// qualifiers
		sb.append("Qualifier(s) = ["); 
		Set<Annotation> qualifiers = metadata.getQualifiers();
		List<String> qualifiersDesc = new ArrayList<String>();
		for ( Annotation a : qualifiers ) {
			if ( a != null )
				qualifiersDesc.add(a.getClass().getName());
		}		
		sb.append(Arrays.toString(qualifiersDesc.toArray(new String[0]))).append("]"); 		
		String info = sb.toString();
		logger.info("Palindrome Service Info: {}", info);
		return info;
	}

}
