# log-tracer
## Purpose

## Usage
1. Add dependency
1. Update the main application class to include:
```java
@EnableLogTracer(profiles={local,development,test})
```
1. Add comma seperated list of packages to wrap with log tracer:
```
logtracer.package=io.pivotal.proxy,io.pivotal.text.controller
```
1. To annotate a specific method:
```java
@LogTracer
public void logMe(){...}
```

