package test.annotations;//Creating annotation

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface MyAnnotation {
    int value();
}

//Applying annotation  
class Hello {
    @MyAnnotation(value = 10)
    public void sayHello() {
        System.out.println("hello annotation");
    }
}

//Accessing annotation  
class TestCustomAnnotation1 {
    public static void main(String args[]) throws Exception {
        Hello hello=new Hello();
        Method method = hello.getClass().getMethod("sayHello");
        final MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
        final int value = annotation.value();
        System.out.println(value);
        System.out.println(Hello.class.getMethod("sayHello").getAnnotation(MyAnnotation.class).value());


    }
}