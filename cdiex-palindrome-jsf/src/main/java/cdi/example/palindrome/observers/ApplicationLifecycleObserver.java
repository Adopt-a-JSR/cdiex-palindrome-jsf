package cdi.example.palindrome.observers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;


// CDI 1.1 - Support firing general purpose lifecycle events in Java EE environments.
// See: https://issues.jboss.org/browse/CDI-86
public class ApplicationLifecycleObserver {

	@Inject
	private Logger logger;
	
	public void afterApplicationInit(@Observes @Initialized(ApplicationScoped.class) Object  o) { 
		logger.info("Application Initialized [{}]", o);
	}

	public void afterApplicationDestroyed(@Observes @Destroyed(ApplicationScoped.class) Object  o) { 
		logger.info("Application Destroyed [{}]", o);
	}

//	public void afterConversationInit(@Observes @Initialized(ConversationScoped.class) Object  o) { 
//		logger.info("Conversation Initialized [{}]", o);
//	}
//
//	public void afterConversationDestroyed(@Observes @Destroyed(ConversationScoped.class) Object  o) { 
//		logger.info("Conversation Destroyed [{}]", o);
//	}
//
//	public void afterRequestInit(@Observes @Initialized(RequestScoped.class) Object  o) { 
//		logger.info("Request Initialized [{}]", o);
//	}
//
//	public void afterRequestDestroyed(@Observes @Destroyed(RequestScoped.class) Object  o) { 
//		logger.info("Request Destroyed [{}]", o);
//	}

}
