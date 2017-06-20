package io.pivotal.log.tracer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.aop.interceptor.CustomizableTraceInterceptor.PLACEHOLDER_ARGUMENTS;
import static org.springframework.aop.interceptor.CustomizableTraceInterceptor.PLACEHOLDER_INVOCATION_TIME;
import static org.springframework.aop.interceptor.CustomizableTraceInterceptor.PLACEHOLDER_METHOD_NAME;
import static org.springframework.aop.interceptor.CustomizableTraceInterceptor.PLACEHOLDER_TARGET_CLASS_NAME;

import org.junit.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;

/**
 * @author jzingler
 */
public class LogTracerAutoConfigurationTest {

	@Test
	public void whenCreateLogTracerAutoConfigurationThenNotNull() {
		LogTracerAutoConfiguration ltac = new LogTracerAutoConfiguration(createLogTracerConfigProperties());
		assertNotNull(ltac);
	}
	
	@Test
	public void whenCreateLogTracerAutoConfigurationThenCustomizableTraceInterceptorNotNull() {
		LogTracerAutoConfiguration ltac = new LogTracerAutoConfiguration(createLogTracerConfigProperties());
		assertNotNull(ltac.customizableTraceInterceptor());
	}
	
	@Test
	public void whenCreateLogTracerAutoConfigurationThenAopAdvisorInstanceOfCustomizableTraceInterceptor() {
		LogTracerAutoConfiguration ltac = new LogTracerAutoConfiguration(createLogTracerConfigProperties());
		CustomizableTraceInterceptor cti = ltac.customizableTraceInterceptor();
		Advisor advisor = ltac.logTraceAopAdvisor(cti);
		
		assertNotNull(advisor);
		assertTrue(advisor.getAdvice() instanceof CustomizableTraceInterceptor);
	}
	
	@Test
	public void whenCreateLogTracerAutoConfigurationThenAopAdvisorAnnotationInstanceOfCustomizableTraceInterceptor() {
		LogTracerAutoConfiguration ltac = new LogTracerAutoConfiguration(createLogTracerConfigProperties());
		CustomizableTraceInterceptor cti = ltac.customizableTraceInterceptor();
		Advisor advisor = ltac.logTraceAopAdvisorAnnotation(cti);
		
		assertNotNull(advisor);
		assertTrue(advisor.getAdvice() instanceof CustomizableTraceInterceptor);
	}
	
	@Test
	public void testEnterExitMessage() {
		assertEquals(">>> Entering method '" + PLACEHOLDER_METHOD_NAME + "(" + PLACEHOLDER_ARGUMENTS
			+ ")' of class [" + PLACEHOLDER_TARGET_CLASS_NAME + "]", LogTracerAutoConfiguration.ENTER_METHOD_LOG_MESSAGE);
		
		assertEquals("<<< Exiting  method '" + PLACEHOLDER_METHOD_NAME + "(" + PLACEHOLDER_ARGUMENTS
				+ ")' of class [" + PLACEHOLDER_TARGET_CLASS_NAME + "] took " + PLACEHOLDER_INVOCATION_TIME + "ms", LogTracerAutoConfiguration.EXIT_METHOD_LOG_MESSAGE);
	}
	
	@Test
	public void whenCreatePkgPointcutExprThenNotNullOrEmpty() {
		LogTracerConfigProperties ltp = createLogTracerConfigProperties();
		LogTracerAutoConfiguration ltac = new LogTracerAutoConfiguration(createLogTracerConfigProperties());
		String pointcut = ltac.createAopAdvisorPointcutExpression(ltp.getPackages());
		
		assertNotNull(pointcut);
		assertNotEquals("", pointcut);
	}
	
	@Test
	public void whenCreatePkgPointcutExprWithNullThenNotNullAndEmpty() {
		LogTracerAutoConfiguration ltac = new LogTracerAutoConfiguration(createLogTracerConfigProperties());
		String pointcut = ltac.createAopAdvisorPointcutExpression(new String[0]);
		
		assertNotNull(pointcut);
		assertEquals("", pointcut);
	}
	
	@Test
	public void whenCreatePkgPointcutExprThenPointcutEqInputPackage() {
		LogTracerConfigProperties ltp = createLogTracerConfigProperties();
		LogTracerAutoConfiguration ltac = new LogTracerAutoConfiguration(ltp);
		String pointcut = ltac.createAopAdvisorPointcutExpression(ltp.getPackages());
		
		assertEquals("execution(* io.pivotal.log.tracer..*.*(..))", pointcut);
	}
	
	@Test
	public void whenCreatePkgPointcutExprWithMultipleThenPointcutEqInputPackages() {
		LogTracerConfigProperties mltp = createMultipleLogTracerConfigProperties();
		LogTracerAutoConfiguration ltac = new LogTracerAutoConfiguration(mltp);
		String pointcut = ltac.createAopAdvisorPointcutExpression(mltp.getPackages());
		
		assertEquals("execution(* io.pivotal.log.tracer..*.*(..))"
				+ " || execution(* io.test.multiple.two..*.*(..))"
				+ " || execution(* io.test.multiple.three..*.*(..))", pointcut);
	}

	LogTracerConfigProperties createLogTracerConfigProperties() {
		LogTracerConfigProperties ltcp = new LogTracerConfigProperties();
		ltcp.setPackage(new String[] { "io.pivotal.log.tracer" });
		return ltcp;
	}
	
	LogTracerConfigProperties createMultipleLogTracerConfigProperties() {
		LogTracerConfigProperties ltcp = new LogTracerConfigProperties();
		ltcp.setPackage(new String[] { "io.pivotal.log.tracer", "io.test.multiple.two", "io.test.multiple.three" });
		return ltcp;
	}

}
