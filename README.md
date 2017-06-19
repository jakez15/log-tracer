# log-tracer
## Purpose
Provide a lightweight and easy to use performance metrics logger with the ability to target specific spring profiles, project packages, and methods.

## Usage
1. Add dependency
2. Update the main application class to include the `@EnableLogTracer` annotation. The `@EnableLogTracer` annotations takes a comma seperated list of profiles that the log tracer will be enabled for:
```java
@EnableLogTracer(profiles={local,development,test})
```
3. Add the required `logtracer.package` property with comma seperated list of packages to wrap with the log tracer:
```
logtracer.package=io.pivotal.proxy,io.pivotal.text.controller
```
4. In addition to wrapping enitre packages with the log tracer you can annotate specific method's with `@LogTracer`:
```java
@LogTracer
public void logMe(){...}
```

## Example of Logged Output
```
2017-06-17 22:24:56.479 TRACE 29914 --- [nio-8080-exec-2] o.s.a.i.CustomizableTraceInterceptor     : >>> Entering method 'fakeAnnotateMethod()' of class [io.pivotal.annotation.AnnotationController]
2017-06-17 22:24:56.484 TRACE 29914 --- [nio-8080-exec-2] o.s.a.i.CustomizableTraceInterceptor     : <<< Exiting  method 'fakeAnnotateMethod()' of class [io.pivotal.annotation.AnnotationController] took 5ms.
```


