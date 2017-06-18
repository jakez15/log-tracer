package io.pivotal.log.tracer;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "logtracer")
@Validated
public class LogTracerConfigProperties {
	@NotNull
	private String[] packages;

	public String[] getPackages() {
		return packages;
	}

	public void setBasePackage(String[] packages) {
		this.packages = packages;
	}
}