package io.pivotal.log.tracer;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * Convenience annotation to enable auto-configuration of {@link CustomizableTraceInterceptor} based on the user provided spring {@link Profile}.  
 * 
 * @author jzingler
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LogTracerImportSelectorTest.class)
public @interface EnableLogTracer {

	String[] profiles() default "";

}