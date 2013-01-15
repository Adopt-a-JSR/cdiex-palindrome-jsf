package cdi.example.palindrome;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.weld.context.bound.BoundConversationContext;
import org.jboss.weld.context.bound.MutableBoundRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import cdi.example.datastore.DataStore;
import cdi.example.palindrome.beans.PalindromeServiceSynchronous;
import cdi.example.palindrome.bindings.PalindromeServiceSync;
import cdi.example.palindrome.decorators.PalindromeDecorator;
import cdi.example.palindrome.interceptors.AuditInterceptor;
import cdi.example.palindrome.observers.ApplicationLifecycleObserver;
import cdi.example.palindrome.producers.InputSanitizerProducer;
import cdi.example.utils.producers.LoggerProducer;


@RunWith(Arquillian.class)
public class PalindromeRequestTest {

	
	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)
				.addPackage(Palindrome.class.getPackage())
				.addPackage(PalindromeServiceSynchronous.class.getPackage())
				.addPackage(PalindromeServiceSync.class.getPackage())
				.addPackage(PalindromeDecorator.class.getPackage())
				.addPackage(AuditInterceptor.class.getPackage())
				.addPackage(ApplicationLifecycleObserver.class.getPackage())
				.addPackage(InputSanitizerProducer.class.getPackage())
				.addPackage(DataStore.class.getPackage())
				.addPackage(LoggerProducer.class.getPackage())
				.addAsManifestResource(
						new StringAsset("<beans> <alternatives><class priority='100'>cdi.example.datastore.InMemoryDataStore</class></alternatives> </beans>"), 
						"beans.xml");
	}
	
	@Inject
	private PalindromeRequest palindromeRequest;
	
		
	@Test
	public void testCheckPalindromeSingleTrueValue() {
		palindromeRequest.setWord("fdf");
		palindromeRequest.checkPalindrome();
		Assert.assertEquals(Boolean.TRUE, palindromeRequest.getPalindrome());
	}

	@Test
	public void testCheckPalindromeMultipleValues() {
		palindromeRequest.setWord("Aha");
		palindromeRequest.checkPalindrome();
		Assert.assertEquals(Boolean.TRUE, palindromeRequest.getPalindrome());
		//
		palindromeRequest.setWord("ptpd");
		palindromeRequest.checkPalindrome();
		Assert.assertEquals(Boolean.FALSE, palindromeRequest.getPalindrome());
	}

}
