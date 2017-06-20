package io.pivotal.log.tracer;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * {@link DeferredImportSelector} to handle {@link EnableAutoConfiguration auto-configuration} of {@link LogTracerAutoConfiguration}.
 * 
 * @author jzingler
 */
public class LogTracerImportSelector implements ImportSelector, EnvironmentAware {

	private Environment environment;

	@Override
	public String[] selectImports(AnnotationMetadata metadata) {
		String[] logTracerAutoConfigurationImports = new String[0];

		AnnotationAttributes attributes = AnnotationAttributes
				.fromMap(metadata.getAnnotationAttributes("io.pivotal.log.tracer.EnableLogTracer", true));

		String[] annotationProfiles = attributes.getStringArray("profiles");
		boolean activeProfilesIndicator = this.environment.acceptsProfiles(annotationProfiles);

		if (activeProfilesIndicator) {
			logTracerAutoConfigurationImports = new String[] { "io.pivotal.log.tracer.LogTracerAutoConfiguration" };
		}

		return logTracerAutoConfigurationImports;
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

}
