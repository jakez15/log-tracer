package io.pivotal.log.tracer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

public class LogTracerImportSelector implements DeferredImportSelector, EnvironmentAware {

	private Environment environment;

	@Override
	public String[] selectImports(AnnotationMetadata metadata) {
		String[] imports = new String[0];
		
		AnnotationAttributes attributes = AnnotationAttributes
				.fromMap(metadata.getAnnotationAttributes("io.pivotal.log.tracer.EnableLogTracer", true));

		String[] annotationProfiles = attributes.getStringArray("profiles");
		String[] activeProfiles = this.environment.getActiveProfiles();
		
		for (int i = 0; i < activeProfiles.length; i++) {
			for (int j = 0; j < annotationProfiles.length; j++) {
				if (activeProfiles[i].equals(annotationProfiles[j])) {
					List<String> importsList = new ArrayList<>(Arrays.asList(imports));
					importsList.add("io.pivotal.log.tracer.LogTracerAutoConfiguration");
					return importsList.toArray(new String[0]);
				}
			}
		}

		return imports;
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

}
