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
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LogTracerConfigProperties.class)
public class LogTracerAutoConfiguration {

	@Autowired
	private LogTracerConfigProperties logTracerConfigProperties;

	@Bean
	CustomizableTraceInterceptor customizableTraceInterceptor() {
		CustomizableTraceInterceptor cti = new CustomizableTraceInterceptor();
		cti.setEnterMessage(">>> Entering method '" + PLACEHOLDER_METHOD_NAME + "(" + PLACEHOLDER_ARGUMENTS
				+ ")' of class [" + PLACEHOLDER_TARGET_CLASS_NAME + "]");

		cti.setExitMessage("<<< Exiting  method '" + PLACEHOLDER_METHOD_NAME + "(" + PLACEHOLDER_ARGUMENTS
				+ ")' of class [" + PLACEHOLDER_TARGET_CLASS_NAME + "] took " + PLACEHOLDER_INVOCATION_TIME + "ms.");

		return cti;
	}

	@Bean
	Advisor logTraceAopAdvisor(CustomizableTraceInterceptor cti) {
		String[] pointCutArr = logTracerConfigProperties.getPackages();
		String pointCutPackages = "";
		for (int i = 0; i < pointCutArr.length; i++) {
			if (i == 0) {
				pointCutPackages = "execution(* ".concat(pointCutArr[i]).concat("..*.*(..))");
			} else {
				pointCutPackages += "|| execution(* ".concat(pointCutArr[i]).concat("..*.*(..))");
			}
		}
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(pointCutPackages);
		return new DefaultPointcutAdvisor(pointcut, cti);
	}

	@Bean
	Advisor logTraceAopAdvisorAnnotation(CustomizableTraceInterceptor cti) {
		final String annotationPointCut = "@annotation(io.pivotal.log.tracer.LogTracer)";
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(annotationPointCut);
		return new DefaultPointcutAdvisor(pointcut, cti);
	}

}
