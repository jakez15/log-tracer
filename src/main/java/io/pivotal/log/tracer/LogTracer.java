package io.pivotal.log.tracer;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.context.annotation.Profile;

/**
 * Convenience annotation to add a {@link CustomizableTraceInterceptor} to a specific method. 
 * 
 *  Dependent on {@link EnableLogTracer} enabled for an active spring {@link Profile}
 * 
 * @author jzingler
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogTracer {

}
