package io.pivotal.log.tracer;

import static org.springframework.aop.interceptor.CustomizableTraceInterceptor.PLACEHOLDER_ARGUMENTS;
import static org.springframework.aop.interceptor.CustomizableTraceInterceptor.PLACEHOLDER_INVOCATION_TIME;
import static org.springframework.aop.interceptor.CustomizableTraceInterceptor.PLACEHOLDER_METHOD_NAME;
import static org.springframework.aop.interceptor.CustomizableTraceInterceptor.PLACEHOLDER_TARGET_CLASS_NAME;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableAutoConfiguration Auto-configuration} of {@link CustomizableTraceInterceptor} for the user-defined packages and methods annotated with {@link LogTracer}
 * 
 * @author jzingler
 */
@Configuration
@EnableConfigurationProperties(LogTracerConfigProperties.class)
public class LogTracerAutoConfiguration {

	protected static final String ENTER_METHOD_LOG_MESSAGE = ">>> Entering method '" + PLACEHOLDER_METHOD_NAME + "(" + PLACEHOLDER_ARGUMENTS
			+ ")' of class [" + PLACEHOLDER_TARGET_CLASS_NAME + "]";
	
	protected static final String EXIT_METHOD_LOG_MESSAGE = "<<< Exiting  method '" + PLACEHOLDER_METHOD_NAME + "(" + PLACEHOLDER_ARGUMENTS
			+ ")' of class [" + PLACEHOLDER_TARGET_CLASS_NAME + "] took " + PLACEHOLDER_INVOCATION_TIME + "ms";
	
	private final LogTracerConfigProperties logTracerConfigProperties;
	
	@Autowired
	public LogTracerAutoConfiguration(LogTracerConfigProperties logTracerConfigProperties){
		this.logTracerConfigProperties = logTracerConfigProperties;
	}
	
	@Bean
	CustomizableTraceInterceptor customizableTraceInterceptor() {
		CustomizableTraceInterceptor cti = new CustomizableTraceInterceptor();
		cti.setEnterMessage(ENTER_METHOD_LOG_MESSAGE);
		cti.setExitMessage(EXIT_METHOD_LOG_MESSAGE);

		return cti;
	}

	/**
	 * Create expression pointcut based on user-provided packages
	 * 
	 * @param cti
	 * @return DefaultPointcutAdvisor
	 */
	@Bean
	Advisor logTraceAopAdvisor(CustomizableTraceInterceptor cti) {
		final String[] pointCutArr = logTracerConfigProperties.getPackages();
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(createAopAdvisorPointcutExpression(pointCutArr));
		return new DefaultPointcutAdvisor(pointcut, cti);
	}

	/**
	 * Create a pointcut expression based on user-provided packages
	 * 
	 * @param pointCutArr
	 * @return String containing pointcut expression
	 */
	String createAopAdvisorPointcutExpression(String[] pointCutArr) {
		String pointCutPackagesExpr = "";
		for (int i = 0; i < pointCutArr.length; i++) {
			if (i == 0) {
				pointCutPackagesExpr = "execution(* ".concat(pointCutArr[i]).concat("..*.*(..))");
			} else {
				pointCutPackagesExpr += " || execution(* ".concat(pointCutArr[i]).concat("..*.*(..))");
			}
		}
		return pointCutPackagesExpr;
	}

	/**
	 * Create expression pointcut based on methods annotated with {@link LogTracer}
	 * 
	 * @param cti
	 * @return DefaultPointcutAdvisor
	 */
	@Bean
	Advisor logTraceAopAdvisorAnnotation(CustomizableTraceInterceptor cti) {
		final String annotationPointCut = "@annotation(io.pivotal.log.tracer.LogTracer)";
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(annotationPointCut);
		return new DefaultPointcutAdvisor(pointcut, cti);
	}

}
