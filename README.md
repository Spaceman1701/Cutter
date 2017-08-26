# Cutter
Cutter is an open source, lightweight, and fast java library for basic aspect oriented programming. As the name suggests,
the primary feature of Cutter is the ability to add [Pointcuts](https://en.wikipedia.org/wiki/Pointcut) to java. Unlike libraries
like AspectJ, Cutter does't require a special compiler or heavy runtime dependencies. Cutter is written in pure java with
minimal "magic" code. 

#Features
##Simplicity
Cutter currently supports Pointcuts targeting instance methods using annotations. No special syntax is required.
#Performance
Since cutter creates Pointcuts complete at compile time and does not depend on reflection, it has a minimal performance impact.
##Security
Cutter's Pointcuts are also memory safe, which means that the ```before``` method of the point cut executes before the intercepted method's
stack frame is even allocated. 

Another advantage for Cutter's security is it's simplicity: Cutter doesn't depend on any complex runtime (or even compile time)
logic. This reduces the potential security risks substantially. Furthermore, since cutter isn't dependant on reflection, the
Java security manager can be activated without interfering with Pointcuts.  

#Example

To use cutter, you first have to create an concrete implementation of the ```PointCut``` abstract class. Then you have to
annotate your methods with ```@Cut```

A Pointcut is a plain old java object:
````java
class MyPointCut extends PointCut<Object> {
    
    public MyPointCut(JoinPoint joinPoint, Parameter[] parameters) {
        super(joinPoint, parameters);
    }
    
    /**
    * the intercepted method will execute iff before() returns true 
    */
    @Override
    public boolean before() {
        return parameters[0].getCurrentValue().equals("admin");
    }
    
    /**
    * after() captures the intercepted method's returnValue 
    */
    @Override
    public Object after(Object returnValue) {
        return returnValue;
    }
    
    
   /**
   * onSkip() is called when the intercepted method is skipped.
   * tgus allows a default behavior to be defined.
   */
    @Override
    public Object onSkip() {
        throw new IllegalArgumentException("User must be admin!");    
    }
}
````

Using this point cut is simple:

```java
public class foobar {
    @Cut(MyPointCut.class)
    public Object foo(String user) {
        System.out.println("super secure info");
    }
}
```