# log-tracer
## Purpose
Provide an easy to use metrics logger with the ability to target specific spring profiles, project packages, and specific methods.

## Usage
1. Add dependency
2. Update the main application class to include:
```java
@EnableLogTracer(profiles={local,development,test})
```
3. Add property with comma seperated list of packages to wrap with log tracer:
```
logtracer.package=io.pivotal.proxy,io.pivotal.text.controller
```
4. To annotate a specific method:
```java
@LogTracer
public void logMe(){...}
```

## Example of Logged Output
```
2017-06-17 22:24:56.479 TRACE 29914 --- [nio-8080-exec-2] o.s.a.i.CustomizableTraceInterceptor     : >>> Entering method 'fakeAnnotateMethod()' of class [io.pivotal.annotation.AnnotationController]
2017-06-17 22:24:56.484 TRACE 29914 --- [nio-8080-exec-2] o.s.a.i.CustomizableTraceInterceptor     : <<< Exiting  method 'fakeAnnotateMethod()' of class [io.pivotal.annotation.AnnotationController] took 5ms.
```


