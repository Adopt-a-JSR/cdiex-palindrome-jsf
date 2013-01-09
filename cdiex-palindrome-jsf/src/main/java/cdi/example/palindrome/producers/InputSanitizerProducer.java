package cdi.example.palindrome.producers;

import java.util.regex.Pattern;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import cdi.example.palindrome.bindings.InputSanitizer;



public class InputSanitizerProducer {

	// Anything can be injected anywhere, using a "thing producer"
	// This is essentially a CDI variation on the factory method pattern
	@Produces @InputSanitizer
	Pattern pattern() {
		 return Pattern.compile("\\W");
	}

	// Contrived example to show that the framework also takes care of calling 
	// the user defined disposer at the end of the "thing" lifecycle. This lifecycle 
	// depends in our case on the context the "thing" itself is injected into.
	void removePattern(@Disposes @InputSanitizer Pattern pattern) {
		pattern = null;
	}
	

}
