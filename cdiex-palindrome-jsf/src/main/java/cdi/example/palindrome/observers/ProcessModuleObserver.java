package cdi.example.palindrome.observers;

import java.util.Arrays;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.ProcessModule;
import javax.inject.Inject;

import org.slf4j.Logger;


// CDI 1.1 - Ability to process modules and manipulate the content of the beans.xml file via ProcessModule.
// See: https://issues.jboss.org/browse/CDI-97
// TODO This does not work. Is the event fired during container init so only visible by an extension that observes 
// container init events (e.g. AfterDeploymentValidation)?
public class ProcessModuleObserver {

	@Inject
	private Logger logger;
	
	public void observeProcessModule(@Observes ProcessModule  pm) { 
		logger.info("ProcessModule Event observed");
		logger.info("PM Alternatives: {}", Arrays.toString(pm.getAlternatives().toArray()));		
	}

}
