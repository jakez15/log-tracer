package io.pivotal.log.tracer;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "logtracer")
@Validated
public class LogTracerConfigProperties {

	@NotBlank
	private String logTracerPackage;

	public String getLogTracerPackage() {
		return logTracerPackage;
	}

	public void setLogTracerPackage(String logTracerPackage) {
		this.logTracerPackage = logTracerPackage;
	}
}