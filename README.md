# Cutter
Cutter is an open source, lightweight, and fast java library for basic aspect oriented programming. As the name suggests,
the primary feature of Cutter is the ability to add [Pointcuts](https://en.wikipedia.org/wiki/Pointcut) to java. Unlike libraries
like AspectJ, Cutter does't require a special compiler or heavy runtime dependencies. Cutter is written in pure java with
minimal "magic" code. 

Cutter is available for Java 8.
# Features
## Simplicity
Cutter currently supports Pointcuts targeting instance methods using annotations. No special syntax or config files are required.

Cutter also doesn't have any complex runtime. Not even a special classloader or a Java agent.
## Performance
Since cutter creates Pointcuts completely at compile time and does not depend on reflection, it has a minimal performance impact.
## Security
Cutter's Pointcuts are also memory safe, which means that the ```before``` method of the point cut executes before the intercepted method's
stack frame is even allocated. 

Another advantage for Cutter's security is it's simplicity: Cutter doesn't depend on any complex runtime (or even compile time)
logic. This reduces the potential security risks substantially. Furthermore, since cutter isn't dependant on reflection, the
Java security manager can be activated without interfering with Pointcuts. 

## Interoperability
Since cutter uses facade methods to do method invocation interception, Pointcuts have no special runtime requirements.
This means that code that uses Cutter is standard java code that is fully compatible with any other Java library and with any JVM.

# Example

To use cutter, you first have to create an concrete implementation of the ```PointCut``` abstract class. Then you have to
annotate your methods with ```@Cut```

A Pointcut is a plain old java object:
````java
class MyPointCut extends AbstractPointCut<Object> {
    
    public MyPointCut(JoinPoint joinPoint, Parameter[] parameters) {
        super(joinPoint, parameters);
    }
    
    /**
    * the intercepted method will execute iff before() returns true 
    */
    @Override
    public boolean before() {
        return getParamerValue(0).equals("admin");
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
# Limitations/Know Bugs
* Cuts in anonymous classes are not supported
    * Currently putting a Cut in an anonymous class does not raise a compiler error - it simply does nothing
    * This is due to a limitation in the java compiler and may not be feasible to fix
* Cutter is JDK dependent. While it appears to work on all Oracle JDKs (including OpenJDK), there is no guarantee
that it will work on any given JDK. Cutter uses a "hack" similar to [project lombok](https://projectlombok.org/) to manipulate the AST at compile-time.
* Recursive methods will trigger the Pointcut every time - this behavior is probably acceptable in most cases, but may be configurable in the future.
    * A possible workaround is to use reflection
* Inheritance (specifically 'super') calls can lead to confusing behavior
    * Java does not support method annotation inheritence, so Cuts should be placed on concrete classes when possible
    * When making a call to 'super,' Cuts placed on the supermethod will be triggered.
    * Furthermore, abstract method's cannot be marked with Cut because it would cause invalid code to be generated. This may be fixed in a future version
