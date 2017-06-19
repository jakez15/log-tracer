package io.pivotal.log.tracer;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "logtracer")
@Validated
public class LogTracerConfigProperties {
	@NotEmpty
	private String[] packages;

	public String[] getPackages() {
		return packages;
	}

	public void setPackage(String[] packages) {
		this.packages = packages;
	}
}