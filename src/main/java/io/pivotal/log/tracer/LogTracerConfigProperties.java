package io.pivotal.log.tracer;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for {@link EnableLogTracer} for user-defined packages 
 *  
 * @author jzingler
 */
@ConfigurationProperties(prefix = "logtracer")
public class LogTracerConfigProperties {
	
	private String[] packages;

	public String[] getPackages() {
		return packages;
	}

	public void setPackage(String[] packages) {
		this.packages = packages;
	}
}