package cdi.example.palindrome.aop;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

import cdi.example.palindrome.bindings.Auditable;


@Interceptor
@Auditable
public class AuditInterceptor {

	@Inject 
	private Logger logger;
	
	@AroundInvoke 
	public Object auditRequest(InvocationContext ctx) throws Exception {  
		Object[] params = ctx.getParameters();
		if ( params != null && params.length >= 1 ) {
			if ( params[0].toString().startsWith("a") ) {
				logger.warn("AuditInterceptor - Service called for string: [{}]", params[0].toString());
			}
		}
		Object ret = ctx.proceed();
        return ret;
	}

}
